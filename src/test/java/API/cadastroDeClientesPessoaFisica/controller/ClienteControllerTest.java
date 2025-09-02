package API.cadastroDeClientesPessoaFisica.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import API.cadastroDeClientesPessoaFisica.client.dto.ClienteRequestDTO;
import API.cadastroDeClientesPessoaFisica.client.dto.ClienteResponseDTO;
import API.cadastroDeClientesPessoaFisica.client.model.Cliente;
import API.cadastroDeClientesPessoaFisica.client.repo.ClienteRepository;
import API.cadastroDeClientesPessoaFisica.client.service.ClienteService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Teste");
        cliente.setEmail("teste@email.com");
        cliente.setCpf("12345678900");
        cliente.setDataNascimento(LocalDate.of(2000, 1, 1));
        cliente.setSenha("senhaCriptografada");

        requestDTO = new ClienteRequestDTO();
        requestDTO.setNome("Teste");
        requestDTO.setEmail("teste@email.com");
        requestDTO.setCpf("12345678900");
        requestDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        requestDTO.setSenha("senha123");
    }

    @Test
    void deveCriarClienteComSucesso() {
        when(clienteRepository.existsByCpf(requestDTO.getCpf())).thenReturn(false);
        when(clienteRepository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(requestDTO.getSenha())).thenReturn("senhaCriptografada");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO response = clienteService.criar(requestDTO);

        assertNotNull(response);
        assertEquals(cliente.getNome(), response.getNome());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void naoDeveCriarClienteQuandoCpfJaExiste() {
        when(clienteRepository.existsByCpf(requestDTO.getCpf())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> clienteService.criar(requestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void naoDeveCriarClienteQuandoEmailJaExiste() {
        when(clienteRepository.existsByCpf(requestDTO.getCpf())).thenReturn(false);
        when(clienteRepository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> clienteService.criar(requestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografadaNova");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponseDTO response = clienteService.atualizar(1L, requestDTO);

        assertNotNull(response);
        assertEquals(cliente.getNome(), response.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void naoDeveAtualizarClienteInexistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clienteService.atualizar(1L, requestDTO));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void deveExcluirClienteExistente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        clienteService.excluir(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void naoDeveExcluirClienteInexistente() {
        when(clienteRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> clienteService.excluir(1L));
        verify(clienteRepository, never()).deleteById(anyLong());
    }

    @Test
    void deveBuscarClientePorId() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteResponseDTO response = clienteService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(cliente.getNome(), response.getNome());
    }

    @Test
    void naoDeveBuscarClientePorIdInexistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clienteService.buscarPorId(1L));
    }

    @Test
    void deveListarTodosClientes() {
        Page<Cliente> page = new PageImpl<>(Arrays.asList(cliente));
        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ClienteResponseDTO> result = clienteService.listarTodos(Pageable.unpaged());

        assertEquals(1, result.getContent().size());
    }

    @Test
    void deveBuscarPorCpf() {
        when(clienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(cliente));

        Optional<ClienteResponseDTO> result = clienteService.buscarPorCpf("12345678900");

        assertTrue(result.isPresent());
    }

    @Test
    void deveBuscarPorEmail() {
        when(clienteRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(cliente));

        Optional<ClienteResponseDTO> result = clienteService.buscarPorEmail("teste@email.com");

        assertTrue(result.isPresent());
    }
}
