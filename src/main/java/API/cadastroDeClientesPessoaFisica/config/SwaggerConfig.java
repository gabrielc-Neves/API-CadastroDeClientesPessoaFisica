package API.cadastroDeClientesPessoaFisica.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Cadastro de Clientes Pessoa Física")
                .description("API REST para cadastro, edição, listagem e exclusão de clientes pessoa física. Autenticação JWT, validação de dados e documentação Swagger.")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Gabriel Neves")
                    .email("gabrielcneves17@gmail.com")
                    .url("https://github.com/gabrielc-Neves"))

            )
            .addServersItem(new Server().url("http://localhost:8080").description("Servidor local"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Autenticação JWT via header Authorization: Bearer {token}")
                )
            )
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}

