package API.cadastroDeClientesPessoaFisica.client.dto;

import java.time.LocalDate;

public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dataNascimento;
    private int idade;

    public ClienteResponseDTO(Long id, String nome, String email, String cpf, LocalDate dataNascimento, int idade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public int getIdade() { return idade; }
}
