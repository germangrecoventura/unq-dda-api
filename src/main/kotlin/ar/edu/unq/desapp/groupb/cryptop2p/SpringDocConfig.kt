package ar.edu.unq.desapp.groupb.cryptop2p

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition
@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class SpringDocConfig {
    @Bean
    fun baseOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("Documentation API CryptoP2PApplication").version("2.0")
                .description("Developed by: Germán Greco Ventura and Pablo Spizzamiglio")
        )
    }
}