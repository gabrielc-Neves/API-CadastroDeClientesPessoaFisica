package API.cadastroDeClientesPessoaFisica.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Cadastro de Clientes API",
        version = "1.0",
        description = "API para cadastro, edição, listagem e exclusão de clientes PF"
    )
)
public class OpenApiConfig {
}

