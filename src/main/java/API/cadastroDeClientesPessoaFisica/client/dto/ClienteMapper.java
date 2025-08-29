package API.cadastroDeClientesPessoaFisica.client.dto;


import API.cadastroDeClientesPessoaFisica.client.domain.Cliente;

public class ClienteMapper {

    public static ClienteResponseDTO toResponse(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf(),
                cliente.getDataNascimento(),
                cliente.getIdade()
        );
    }

    public static Cliente toEntity(ClienteRequestDTO dto) {
        return new Cliente(
                dto.getNome(),
                dto.getEmail(),
                dto.getCpf(),
                dto.getDataNascimento()
        );
    }
}
