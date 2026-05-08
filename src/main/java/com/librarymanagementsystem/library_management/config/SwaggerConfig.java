package com.librarymanagementsystem.library_management.config;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI libraryOpenAPI() {
    	final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Library Management System API")
                        .description("REST API for Library Management System")
                        .version("1.0.0"))
                
                .addSecurityItem(new SecurityRequirement()
                		.addList(securitySchemeName))
                .components(new Components()
                		.addSecuritySchemes(securitySchemeName,
                				new SecurityScheme()
                				.name(securitySchemeName)
                				.type(SecurityScheme.Type.HTTP)
                				.scheme("bearer")
                				.bearerFormat("JWT")));
    }
}