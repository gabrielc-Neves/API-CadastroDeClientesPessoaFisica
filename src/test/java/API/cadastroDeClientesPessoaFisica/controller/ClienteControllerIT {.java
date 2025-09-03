package API.cadastroDeClientesPessoaFisica.controller;

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
class ClienteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarClienteComSucesso() throws Exception {
        ClienteRequestDTO dto = new ClienteRequestDTO();
        dto.setNome("João da Silva");
        dto.setEmail("joao@email.com");
        dto.setCpf("12345678901");
        dto.setDataNascimento(LocalDate.of(2000, 1, 1));
        dto.setSenha("senha123");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.cpf").value("12345678901"));
    }
}