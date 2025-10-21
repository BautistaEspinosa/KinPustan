package com.kinpustan.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenApi() {
    final String securitySchemeName = "bearerAuth";
    return new OpenAPI()
        .info(new Info()
            .title("Mi API")
            .version("1.0")
            .description("Documentación de la API para la tienda de abarrotes KinPustan"))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(new io.swagger.v3.oas.models.Components()
            .addSecuritySchemes(securitySchemeName,
                new io.swagger.v3.oas.models.security.SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
        );
  }
}