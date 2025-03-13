package br.com.gabrieudev.itau.config.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
        @Bean
        OpenAPI itauOpenAPI() {
                return new OpenAPI()
                                .info(new Info().title("Desafio Itaú")
                                                .description("Minha resolução do desafio técnico Back-End do Itaú")
                                                .version("v0.0.1")
                                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                                .servers(List.of(
                                                new Server().url("http://localhost:8080/api/v1")
                                                                .description("Servidor local")))
                                .components(new io.swagger.v3.oas.models.Components())
                                .externalDocs(new ExternalDocumentation()
                                                .description("Repositório do projeto")
                                                .url("https://github.com/gabrieudev/itau"));
        }
}
