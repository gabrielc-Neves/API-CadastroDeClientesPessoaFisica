package API.cadastroDeClientesPessoaFisica.client.service;


import API.cadastroDeClientesPessoaFisica.client.dto.ClienteRequestDTO;
import API.cadastroDeClientesPessoaFisica.client.dto.ClienteResponseDTO;
import API.cadastroDeClientesPessoaFisica.client.model.Cliente;
import API.cadastroDeClientesPessoaFisica.client.dto.ClienteMapper;
import API.cadastroDeClientesPessoaFisica.client.repo.ClienteRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Criar novo cliente
    public ClienteResponseDTO criar(ClienteRequestDTO dto) {
        // verificar duplicidade de CPF e Email
        if (clienteRepository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (clienteRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        
        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));
        Cliente salvo = clienteRepository.save(cliente);
        return ClienteMapper.toResponse(salvo);
    }

    // Atualizar cliente
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        // atualizar campos
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        cliente.setCpf(dto.getCpf());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setSenha(passwordEncoder.encode(dto.getSenha()));

        Cliente atualizado = clienteRepository.save(cliente);
        return ClienteMapper.toResponse(atualizado);
    }

    // Excluir cliente
    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    // Buscar cliente por ID
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));
        return ClienteMapper.toResponse(cliente);
    }

    // Listar todos (paginado)
    public Page<ClienteResponseDTO> listarTodos(int page, int size) {
        return clienteRepository.findAll(PageRequest.of(page, size))
                .map(ClienteMapper::toResponse);
    }

    // Buscar por CPF
    public Optional<ClienteResponseDTO> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).map(ClienteMapper::toResponse);
    }

    // Buscar por Email
    public Optional<ClienteResponseDTO> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email).map(ClienteMapper::toResponse);
    }
}

