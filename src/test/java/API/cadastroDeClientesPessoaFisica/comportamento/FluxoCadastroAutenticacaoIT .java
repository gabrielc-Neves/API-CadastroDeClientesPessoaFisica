package API.cadastroDeClientesPessoaFisica.comportamento;


import API.cadastroDeClientesPessoaFisica.client.dto.ClienteRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FluxoCadastroAutenticacaoIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fluxoCompletoCadastroELogin() throws Exception {
        // Cadastro
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNome("Maria Teste");
        dto.setEmail("maria@email.com");
        dto.setCpf("98765432100");
        dto.setDataNascimento(LocalDate.of(1995, 5, 20));
        dto.setSenha("senhaSegura");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Login (ajuste conforme seu AuthController espera os parâmetros)
        mockMvc.perform(post("/api/auth/login")
                .param("username", "maria@email.com")
                .param("password", "senhaSegura"))
                .andExpect(status().isOk());
    }
}
