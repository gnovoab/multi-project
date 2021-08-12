package com.gnovoa.stock.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *  Configuration class for Swagger / Open Api
 */
@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(@Value("\${spring.application.version}") appVersion: String?): OpenAPI? {

        return OpenAPI()
            .components(Components())
            .info(
                Info()
                    .title("Stock Service")
                    .description("This is the Stock Open API Documentation.")
                    .version(appVersion)
                    .contact(Contact().name("SoftwareTastic").email("enquires@SoftwareTastic.com").url("https://www.SoftwareTastic.com/"))
                    .termsOfService("http://swagger.io/terms/")
                    .license(License().name("Apache 2.0").url("http://springdoc.org"))
            )
    }
}
