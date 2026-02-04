package com.demo.streamflix.security

import com.demo.streamflix.service.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsServiceImpl
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)

            if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)) {
                val username = jwtService.getUsernameFromToken(jwt)
                
                val userDetails = userDetailsService.loadUserByUsername(username)
                val authorities = jwtService.getAuthoritiesFromToken(jwt)
                
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, authorities
                )
                
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                
                SecurityContextHolder.getContext().authentication = authentication
                
                log.debug("Usuário autenticado: $username")
            }
        } catch (ex: Exception) {
            log.error("Não foi possível definir autenticação do usuário", ex)
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else {
            // Também verificar no parâmetro da query string (para WebSocket, etc.)
            request.getParameter("token")
        }
    }
}