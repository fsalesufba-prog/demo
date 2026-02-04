package com.demo.streamflix

import com.demo.streamflix.service.CategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DemoStreamflixApplication

fun main(args: Array<String>) {
    runApplication<DemoStreamflixApplication>(*args)
}

/**
 * Bean de inicialização para verificar status da aplicação
 */
@Configuration
class AppConfig {

    @Bean
    fun applicationStartupRunner(
        categoryService: CategoryService
    ): CommandLineRunner {
        return CommandLineRunner {
            println("""
            ============================================
              DEMO StreamFlix Backend Iniciado
            ============================================
            🚀  Aplicação: DEMO StreamFlix
            📅  Data: ${java.time.LocalDateTime.now()}
            🔐  Modo: ${System.getenv("SPRING_PROFILES_ACTIVE") ?: "default"}
            ============================================
            
            Endpoints disponíveis:
            - API: http://localhost:8080/api
            - Swagger: http://localhost:8080/swagger-ui.html
            - Health: http://localhost:8080/api/health
            
            Inicializando categorias padrão...
            ============================================
            """.trimIndent())
            
            // Inicializar categorias padrão
            categoryService.initializeDefaultCategories()
            
            println("Categorias inicializadas com sucesso!")
            println("============================================")
        }
    }
}

/**
 * Controller de health check simples
 */
@RestController
@RequestMapping("/api")
class HealthController {
    
    @GetMapping("/health")
    fun health(): Map<String, Any> {
        return mapOf(
            "status" to "UP",
            "service" to "demo-streamflix-backend",
            "version" to "0.0.1",
            "timestamp" to java.time.Instant.now().toString()
        )
    }
    
    @GetMapping("/info")
    fun info(): Map<String, Any> {
        return mapOf(
            "name" to "DEMO StreamFlix Backend",
            "description" to "Backend para aplicativo de streaming de TV peruana",
            "author" to "Equipe DEMO",
            "features" to listOf(
                "Autenticação JWT",
                "Gestão de usuários",
                "Catálogo de canais TV peruana",
                "Busca de canais",
                "Validação de membresía"
            ),
            "categories" to listOf("Nacional", "Actualidad", "Infantil", "Regional")
        )
    }
}