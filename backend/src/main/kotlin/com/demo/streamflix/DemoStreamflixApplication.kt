package com.demo.streamflix

import com.demo.streamflix.service.CategoryService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableScheduling
class DemoStreamflixApplication

fun main(args: Array<String>) {
    runApplication<DemoStreamflixApplication>(*args)
}

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
            Aplicacao: DEMO StreamFlix
            Data: ${java.time.LocalDateTime.now()}
            Modo: ${System.getenv("SPRING_PROFILES_ACTIVE") ?: "default"}
            ============================================
            
            Endpoints disponiveis:
            - API: http://localhost:8080/api
            - Swagger: http://localhost:8080/swagger-ui.html
            - Health: http://localhost:8080/api/health
            
            Inicializando categorias padrao...
            ============================================
            """.trimIndent())
            
            categoryService.initializeDefaultCategories()
            
            println("Categorias inicializadas com sucesso!")
            println("============================================")
        }
    }
}

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
                "Autenticacao JWT",
                "Gestao de usuarios",
                "Catalogo de canais TV peruana",
                "Busca de canais",
                "Validacao de membresia"
            ),
            "categories" to listOf("Nacional", "Actualidad", "Infantil", "Regional")
        )
    }
}
