package com.demo.streamflix.admin

import emotion.react.css
import react.FC
import react.Props
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div
import web.cssom.*
import web.dom.document
import kotlinx.browser.window
import react.router.*
import react.useState
import react.useEffect
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.demo.streamflix.admin.components.Layout.*
import com.demo.streamflix.admin.pages.*
import com.demo.streamflix.admin.services.AuthService
import com.demo.streamflix.admin.services.AuthApiService

val ProtectedLayout = FC<Props> { props ->
    val location = useLocation()
    val navigate = useNavigate()
    val [sidebarOpen, setSidebarOpen] = useState(false)
    
    // Verificar autenticação
    useEffect {
        val authApiService = AuthApiService()
        val scope = MainScope()
        
        scope.launch {
            val isValid = authApiService.validateToken()
            if (!isValid && location.pathname != "/login") {
                navigate("/login")
            }
        }
        
        cleanup {
            authApiService.close()
        }
    }
    
    // Se não estiver autenticado, redirecionar para login
    if (!AuthService.isAuthenticated() && location.pathname != "/login") {
        navigate("/login")
        return@FC null
    }
    
    // Se estiver na página de login, mostrar apenas a página
    if (location.pathname == "/login") {
        return@FC LoginPage.create()
    }
    
    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            minHeight = 100.vh
        }
        
        Header {
            onMenuClick = { setSidebarOpen(!sidebarOpen) }
            onLogout = {
                AuthService.logout()
                navigate("/login")
            }
            userName = AuthService.getUser()?.let { 
                // Parse user from localStorage
                kotlinx.serialization.json.Json.decodeFromString<UserResponse>(it).fullName
            }
        }
        
        div {
            css {
                display = Display.flex
                flex = 1.number
                marginTop = 64.px
            }
            
            Sidebar {
                isOpen = sidebarOpen
                onClose = { setSidebarOpen(false) }
            }
            
            div {
                css {
                    flex = 1.number
                    marginLeft = if (sidebarOpen) 250.px else 0.px
                    transition = "margin-left 0.3s ease"
                    overflow = "auto".unsafeCast<Overflow>()
                    
                    media("(max-width: 768px)") {
                        marginLeft = 0.px
                    }
                }
                
                // Conteúdo principal baseado na rota
                Routes {
                    Route {
                        path = "/admin"
                        element = DashboardPage.create().unsafeCast<react.ReactNode>()
                    }
                    Route {
                        path = "/admin/users"
                        element = UsersPage.create().unsafeCast<react.ReactNode>()
                    }
                    Route {
                        path = "/admin/channels"
                        element = ChannelsPage.create().unsafeCast<react.ReactNode>()
                    }
                    Route {
                        path = "/admin/categories"
                        element = CategoriesPage.create().unsafeCast<react.ReactNode>()
                    }
                    Route {
                        path = "/admin/subscriptions"
                        element = SubscriptionsPage.create().unsafeCast<react.ReactNode>()
                    }
                }
            }
        }
        
        Footer {}
    }
}

fun main() {
    val container = document.getElementById("root") ?: return
    
    createRoot(container).render {
        BrowserRouter {
            Routes {
                Route {
                    path = "/*"
                    element = ProtectedLayout.create().unsafeCast<react.ReactNode>()
                }
            }
        }
    }
}