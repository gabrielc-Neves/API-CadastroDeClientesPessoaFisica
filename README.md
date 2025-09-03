# Cadastro de Clientes Pessoa Física

Este projeto é uma API REST desenvolvida com Spring Boot para cadastro, edição, listagem e exclusão de clientes pessoa física. Ele utiliza autenticação JWT, validação de dados, documentação Swagger/OpenAPI e persistência em banco de dados H2 (memória) ou PostgreSQL.

## Funcionalidades

- **Cadastro de cliente:** Criação de novos clientes com validação de CPF, email e senha.
- **Atualização de cliente:** Edição dos dados de clientes existentes.
- **Exclusão de cliente:** Remoção de clientes pelo ID.
- **Listagem paginada:** Consulta de clientes com paginação.
- **Busca por CPF e Email:** Endpoints para buscar clientes por CPF ou email.
- **Autenticação JWT:** Login via endpoint `/api/auth/login` que retorna um token JWT.
- **Proteção de rotas:** Endpoints protegidos exigem autenticação via JWT.
- **Swagger UI:** Documentação interativa disponível em `/swagger-ui.html`.
- **Banco de dados:** Configuração padrão para H2 em memória, podendo ser alterada para PostgreSQL.

## Estrutura do Projeto

```
cadastroDeClientesPessoaFisica/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── API/
│   │   │       └── cadastroDeClientesPessoaFisica/
│   │   │           ├── client/
│   │   │           │   ├── controller/      # Controllers REST (ClienteController, AuthController)
│   │   │           │   ├── dto/             # DTOs e mapeadores (ClienteRequestDTO, ClienteResponseDTO, ClienteMapper)
│   │   │           │   ├── model/           # Entidade Cliente
│   │   │           │   ├── repo/            # Repositório JPA (ClienteRepository)
│   │   │           │   └── service/         # Regras de negócio (ClienteService)
│   │   │           ├── security/
│   │   │           │   ├── config/          # Configuração de segurança (SecurityConfig)
│   │   │           │   ├── jwt/             # JWT Service e Filter
│   │   │           │   └── user/            # UserDetails e UserDetailsService customizados
│   │   │           ├── config/              # Configuração Swagger/OpenAPI
│   │   │           └── CadastroDeClientesPessoaFisicaApplication.java # Classe principal
│   │   └── resources/
│   │       ├── application.properties       # Configurações do Spring Boot e banco de dados
│   │       ├── static/                      # Arquivos estáticos (se necessário)
│   │       └── templates/                   # Templates (se necessário)
│   └── test/
│       └── java/
│           └── API/
│               └── cadastroDeClientesPessoaFisica/
│                   ├── controller/          # Testes unitários do ClienteService
│                   └── CadastroDeClientesPessoaFisicaApplicationTests.java
├── pom.xml                                  # Dependências e configuração Maven
├── mvnw, mvnw.cmd, .mvn/                    # Maven Wrapper
├── .gitignore, .gitattributes               # Configurações Git
└── README.md                                # Este arquivo
```

## Endpoints Principais

- `POST /api/clientes` — Cria um novo cliente
- `PUT /api/clientes/{id}` — Atualiza um cliente existente
- `DELETE /api/clientes/{id}` — Exclui um cliente
- `GET /api/clientes/{id}` — Busca cliente por ID
- `GET /api/clientes` — Lista clientes paginados
- `GET /api/clientes/cpf/{cpf}` — Busca cliente por CPF
- `GET /api/clientes/email/{email}` — Busca cliente por email
- `POST /api/auth/login` — Autentica usuário e retorna JWT

## Autenticação
- Cadastre um novo cliente via `POST /api/clientes`, esse endpoint está liberado para que se possa cadastrar clientes e fazer o login.
- Para acessar endpoints protegidos, obtenha um token JWT via `/api/auth/login` e envie no header:
  ```
  Authorization: Bearer <seu_token_jwt>
  ```

## Banco de Dados

- **H2 (memória):** Configuração padrão em `src/main/resources/application.properties`.
- **PostgreSQL:** Basta alterar as propriedades do datasource para apontar para seu banco.

## Testes

- Testes unitários do serviço de cliente em [`src/test/java/API/cadastroDeClientesPessoaFisica/controller/ClienteControllerTest.java`](src/test/java/API/cadastroDeClientesPessoaFisica/controller/ClienteControllerTest.java).
- Teste de contexto Spring Boot em [`src/test/java/API/cadastroDeClientesPessoaFisica/CadastroDeClientesPessoaFisicaApplicationTests.java`](src/test/java/API/cadastroDeClientesPessoaFisica/CadastroDeClientesPessoaFisicaApplicationTests.java).

## Documentação Swagger

- Acesse `/swagger-ui.html` para explorar e testar os endpoints da API.

## Como executar

1. **Pré-requisitos:** Java 17+, Maven.
2. **Build:**  
   ```
   ./mvnw clean install
   ```
3. **Run:**  
   ```
   ./mvnw spring-boot:run
   ```
4. **Acesse:**  
   - API: `http://localhost:8080/api/clientes`
   - Swagger: `http://localhost:8080/swagger-ui/index.html`
   - H2 Console: `http://localhost:8080/h2-console`

## Observações

- Senhas são armazenadas criptografadas (BCrypt).
- O projeto segue boas práticas de separação de camadas (Controller, Service, Repository, DTO).
- Validações de dados são feitas via anotações Bean Validation.
- JWT é usado para autenticação e autorização.
- O Swagger facilita o teste dos endpoints.

---