# ReimburseFlow API

API RESTful desenvolvida em Java e Spring Boot para gerenciamento corporativo de solicitações de reembolso, fluxo de aprovação multinível e rastreabilidade completa de status.

---

## Arquitetura e Tecnologias

- **Linguagem & Framework:** Java 21, Spring Boot 4+
- **Segurança:** Spring Security com autenticação Stateless via **JWT (JSON Web Token)** e controle de acesso baseado em perfis (`ROLE_EMPLOYEE`, `ROLE_MANAGER`, `ROLE_FINANCIAL`).
- **Persistência & Migrações:** Spring Data JPA, PostgreSQL 16 e **Flyway** para versionamento de schema.
- **Documentação:** OpenAPI 3 / Swagger UI (`springdoc-openapi`).
- **Testes:** JUnit 5, MockMvc e **Testcontainers** (PostgreSQL isolado em container Docker para testes de integração ponta a ponta).

---

## Modelo de Domínio e Fluxo

1. **Colaborador (`ROLE_EMPLOYEE`):** Submete solicitações de reembolso com descrição, valor e categoria (`FOOD`, `TRANSPORT`, `LODGING`, etc.).
2. **Gestor (`ROLE_MANAGER`):** Analisa solicitações pendentes, podendo aprovar ou rejeitar com justificativa.
3. **Financeiro (`ROLE_FINANCIAL`):** Efetiva o pagamento das solicitações aprovadas.
4. **Auditoria:** Toda transição de status registra automaticamente um histórico (`tb_reimbursement_histories`) vinculando o autor da alteração, o status anterior, o novo status e o carimbo de data/hora.

---

## Como Executar Localmente

### Pré-requisitos
- Java 21+
- Maven 3.9+
- Docker e Docker Compose (para o banco PostgreSQL e execução dos testes de integração)

### 1. Configurar Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto (ou exporte as variáveis no sistema):

```env
DB_URL=jdbc:postgresql://localhost:5432/reimburseflow
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

### 2. Subir o Banco de Dados

Caso utilize Docker para o PostgreSQL local:
Bash
```env
docker run --name reimburseflow-db \
-e POSTGRES_DB=reimburseflow \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 -d postgres:16-alpine
```

### 3. Executar a Aplicação
```Bash
mvn spring-boot:run
```

As migrações do Flyway serão aplicadas automaticamente na inicialização.

---

## Documentação Interativa (Swagger)

Com a aplicação em execução, acesse:

* Swagger UI: http://localhost:8080/swagger-ui/index.html
* OpenAPI JSON: http://localhost:8080/v3/api-docs

Testando rotas protegidas no Swagger:

1. Execute uma requisição no endpoint POST /api/v1/auth/login com credenciais válidas.
2. Copie o token retornado no corpo da resposta.
3. Clique no botão Authorize no topo da página do Swagger e cole o token (não é necessário incluir o prefixo Bearer).

---

## Executando os Testes

A suíte conta com testes unitários e testes de integração completos utilizando Testcontainers, que sobem uma instância efêmera do PostgreSQL automaticamente:

```
mvn test
```
Nota: Certifique-se de que o daemon do Docker esteja ativo antes de rodar os testes de integração.

---