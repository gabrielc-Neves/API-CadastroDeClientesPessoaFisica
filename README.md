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
│   │   │           ├── CadastroDeClientesPessoaFisicaApplication.java   # Classe principal
│   │   │           ├── client/
│   │   │           │   ├── controller/      # Controllers REST (ClienteController, AuthController)
│   │   │           │   ├── dto/             # DTOs e mapeadores (ClienteRequestDTO, ClienteResponseDTO, ClienteMapper)
│   │   │           │   ├── model/           # Entidade Cliente
│   │   │           │   ├── repo/            # Repositório JPA (ClienteRepository)
│   │   │           │   └── service/         # Regras de negócio (ClienteService)
│   │   │           ├── config/
│   │   │           │   └── SwaggerConfig.java # Configuração Swagger/OpenAPI
│   │   │           ├── security/
│   │   │           │   ├── config/          # Configuração de segurança (SecurityConfig)
│   │   │           │   ├── jwt/             # JWT Service e Filter
│   │   │           │   └── user/            # UserDetails e UserDetailsService customizados
│   │   └── resources/
│   │       ├── application.properties       # Configurações do Spring Boot e banco de dados
│   │       ├── static/                      # Arquivos estáticos (se necessário)
│   │       └── templates/                   # Templates (se necessário)
│   └── test/
│       └── java/
│           └── API/
│               └── cadastroDeClientesPessoaFisica/
│                   ├── CadastroDeClientesPessoaFisicaApplicationTests.java
│                   ├── controller/          # Testes integrados dos controllers
│                   ├── service/             # Testes unitários do ClienteService
│                   └── comportamento/       # Testes de comportamento (fluxo completo)
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

A cobertura de testes está organizada em três tipos principais:

- **Testes Unitários**  
  Localizados em `src/test/java/API/cadastroDeClientesPessoaFisica/service/ClienteServiceTest.java`.  
  Testam as regras de negócio da classe `ClienteService` isoladamente, utilizando mocks para dependências.

- **Testes Integrados**  
  Localizados em `src/test/java/API/cadastroDeClientesPessoaFisica/controller/ClienteControllerIT.java`.  
  Validam o funcionamento dos endpoints dos controllers, simulando requisições HTTP reais com o contexto Spring Boot.

- **Testes de Comportamento**  
  Localizados em `src/test/java/API/cadastroDeClientesPessoaFisica/comportamento/FluxoCadastroAutenticacaoIT.java`.  
  Simulam o fluxo completo do usuário, como cadastro e autenticação, garantindo que os principais cenários funcionem conforme esperado.

Além disso, há o teste de contexto da aplicação em  
`src/test/java/API/cadastroDeClientesPessoaFisica/CadastroDeClientesPessoaFisicaApplicationTests.java`.

Para rodar todos os testes, utilize:
```
./mvnw test
```
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

# Processo Criativo e Decisões de Desenvolvimento

## Introdução

Durante o desenvolvimento deste projeto de cadastro de clientes pessoa física, procurei seguir boas práticas do ecossistema Java/Spring, sempre buscando aprender e aplicar conceitos novos. Neste projeto priorizei clareza, organização e pesquisa constante para superar os desafios.

---

## 1. Levantamento dos Requisitos

Comecei entendendo o objetivo principal: criar uma API REST para cadastro, edição, listagem e exclusão de clientes pessoa física, com autenticação JWT e documentação Swagger.  

---

## 2. Estruturação do Projeto

Optei por separar o projeto em camadas (controller, service, repository, dto, model) para facilitar manutenção e testes.  
Usei o Spring Initializr para gerar a estrutura básica e adicionei dependências como Spring Web, Spring Data JPA, H2, Spring Security e Swagger.

---

## 3. Modelagem dos Dados

Criei a entidade `Cliente` com os campos essenciais (nome, email, cpf, data de nascimento, senha).  
Implementei DTOs para entrada e saída, garantindo que os dados sensíveis (como senha) fossem tratados corretamente.

---

## 4. Implementação dos Endpoints

Implementei os endpoints REST seguindo o padrão CRUD.  
Utilizei Bean Validation para garantir que os dados enviados fossem válidos, pesquisando exemplos de uso das anotações como `@NotBlank`, `@Email`, `@Pattern` e `@NotNull`.

---

## 5. Autenticação JWT
  
Tive dificuldades ao implementar o JWT no swagger mas com algumas tentativas, erro e feedback consegui configurá-lo.

---

## 6. Documentação Swagger

Adicionei o Swagger para facilitar testes e documentação dos endpoints.  
Fui ajustando as anotações para descrever os retornos e possíveis erros, aprendendo sobre o padrão OpenAPI.

---

## 7. Testes

Implementei testes unitários e integrados para garantir o funcionamento dos serviços e controllers.  
No início, tive dificuldades com o Mockito, especialmente para mockar métodos estáticos, mas consegui resolver pesquisando exemplos e ajustando o escopo dos mocks.

---

## 8. Resolução de Problemas

Durante o desenvolvimento, enfrentei problemas como erro 403 (Forbidden) e 400 (Bad Request).  
Aprendi a analisar os logs, revisar a configuração de segurança e ajustar os DTOs para garantir compatibilidade com o JSON enviado.

---

## 9. Aprendizados

- A importância de separar responsabilidades em camadas.
- Como documentar APIs com Swagger.

---

## 10. Considerações Finais

Procurei ser organizado, documentar o código e registrar as decisões tomadas.  
Busquei aprender com cada desafio e entregar uma solução funcional, segura e bem documentada.
