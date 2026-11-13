# ReimburseFlow API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Licença](https://img.shields.io/badge/Licença-TODO-lightgrey.svg)](#licença)

**ReimburseFlow** é uma API RESTful de gestão de reembolsos corporativos desenvolvida com Java e Spring Boot, projetada seguindo os princípios de Clean Architecture e Domain-Driven Design (DDD). A aplicação gerencia solicitações de reembolso, fluxos de aprovação em múltiplos níveis e fornece trilhas completas de auditoria de status.

---

## Sumário

- [Visão Geral](#visão-geral)
- [Domínio e Fluxo de Trabalho](#domínio-e-fluxo-de-trabalho)
- [Stack Tecnológica](#stack-tecnológica)
- [Pré-requisitos](#pré-requisitos)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Configuração e Execução](#configuração-e-execução)
- [Scripts e Comandos Disponíveis](#scripts-e-comandos-disponíveis)
- [Documentação da API e Endpoints](#documentação-da-api-e-endpoints)
- [Testes](#testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [TODOs e Roadmap](#todos-e-roadmap)
- [Licença](#licença)

---

## Visão Geral

O ReimburseFlow otimiza a gestão de despesas corporativas automatizando o envio de reembolsos, revisões gerenciais, pagamentos financeiros e o rastreamento de status.

### Principais Funcionalidades

- **Controle de Acesso Baseado em Papéis (RBAC):** Níveis de permissão distintos para Colaboradores (*Employees*), Gestores (*Managers*) e Analistas Financeiros (*Financial*).
- **Autenticação Assimétrica via JWT:** Segurança stateless utilizando pares de chaves pública/privada RSA.
- **Logs de Auditoria:** Toda alteração de status é registrada com data/hora, referência do usuário responsável e comentários opcionais.
- **Versionamento de Banco de Dados:** Gerenciamento automatizado do schema do banco de dados com migrações Flyway.
- **Testes de Integração:** Testes ponta a ponta utilizando Testcontainers com uma instância efêmera do PostgreSQL.

---

## Domínio e Fluxo de Trabalho

### Perfis de Usuário (Roles)

- `ROLE_EMPLOYEE`: Cria rascunhos de solicitações de reembolso e os submete para análise.
- `ROLE_MANAGER`: Submete suas próprias solicitações, visualiza todas as solicitações e aprova ou rejeita reembolsos pendentes.
- `ROLE_FINANCIAL`: Finaliza o fluxo e marca os reembolsos aprovados como pagos.

### Ciclo de Vida e Transições de Status

```
 [Draft] ──(submit)──> [Submitted] ──(approve)──> [Approved] ──(pay)──> [Paid]
                             │
                             └──(reject)──> [Rejected]
```

- **Categorias de Reembolso:** `FOOD` (Alimentação), `TRANSPORT` (Transporte), `LODGING` (Hospedagem), `OTHER` (Outros) *(além de outras categorias definidas no domínio)*.
- **Trilha de Auditoria:** Armazenada na tabela `tb_reimbursement_histories`, capturando `reimbursement_id`, `changed_by_user_id`, `previous_status`, `new_status`, `comment` e `changed_at`.

---

## Stack Tecnológica

- **Linguagem:** Java 21
- **Framework:** Spring Boot 4.1.2
    - Spring Web MVC
    - Spring Data JPA
    - Spring Security (OAuth2 Resource Server com JWT)
    - Spring Boot Actuator
    - Spring Boot Validation (Jakarta Validation)
    - Spring Boot DevTools (ambiente de desenvolvimento)
- **Banco de Dados e Migrações:** PostgreSQL 16, Flyway Database Migration
- **Documentação da API:** SpringDoc OpenAPI 3 / Swagger UI (`springdoc-openapi-starter-webmvc-ui` 3.2.0)
- **Configuração e Utilitários:** `dotenv-java` (carregamento de `.env`), Project Lombok
- **Testes:** JUnit 5, Spring Security Test, Testcontainers (PostgreSQL 16)
- **Ferramenta de Build e Gerenciador de Dependências:** Apache Maven (com Maven Wrapper `mvnw` incluído)

---

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas localmente:

- **Java Development Kit (JDK):** Versão 21 ou superior
- **Apache Maven:** Versão 3.9+ (opcional caso utilize o wrapper `./mvnw` incluído no projeto)
- **Docker e Docker Compose:** Necessários para subir o contêiner local do PostgreSQL e executar os testes de integração com Testcontainers

---

## Variáveis de Ambiente

A aplicação suporta o carregamento de variáveis diretamente do ambiente do sistema ou através de um arquivo `.env` localizado na raiz do projeto (via `dotenv-java`).

| Variável      | Descrição                            | Exemplo / Padrão |
| ------------- | ------------------------------------ | ---------------- |
| `DB_NAME`     | Nome do banco de dados PostgreSQL    | `reimburseflow`  |
| `DB_USERNAME` | Usuário do banco de dados PostgreSQL | `postgres`       |
| `DB_PASSWORD` | Senha do banco de dados PostgreSQL   | `postgres`       |

### Exemplo de Arquivo `.env`

Crie um arquivo `.env` no diretório raiz do projeto:

```env
DB_NAME=reimburseflow
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

### Chaves de Segurança da Aplicação

As chaves RSA utilizadas para assinatura e verificação dos tokens JWT estão localizadas em:

- **Chave Pública:** `src/main/resources/certs/app.pub`
- **Chave Privada:** `src/main/resources/certs/app.key`
- **Expiração do Token:** `86400` segundos (24 horas)

---

## Configuração e Execução

### 1. Clonar o Repositório

```bash
git clone https://github.com/pedromolon/reimburseflow.git
cd reimburseflow
```

### 2. Configurar as Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto com as credenciais do banco de dados:

```bash
echo "DB_NAME=reimburseflow" >> .env
echo "DB_USERNAME=postgres" >> .env
echo "DB_PASSWORD=postgres" >> .env
```

### 3. Iniciar o Serviço de Banco de Dados

Inicie o contêiner do PostgreSQL utilizando o Docker Compose:

```bash
docker compose up -d
```

*(Alternativamente, utilize `docker-compose up -d` dependendo da sua versão do Docker).*

### 4. Executar a Aplicação

Inicie a aplicação Spring Boot utilizando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

*(No Windows: `mvnw.cmd spring-boot:run`)*

O ponto de entrada da aplicação é `com.pedromolon.reimburseflow.ReimburseflowApplication` e o servidor escuta na porta `8080` por padrão.

As migrações de banco de dados serão executadas automaticamente na inicialização via Flyway (`db/migration/V1__create_initial_tables.sql` e `db/migration/V2__insert_test_user.sql`).

---

## Scripts e Comandos Disponíveis

| Tarefa                            | Comando                                   |
| --------------------------------- | ----------------------------------------- |
| **Executar aplicação localmente** | `./mvnw spring-boot:run`                  |
| **Executar todos os testes**      | `./mvnw test`                             |
| **Compilar e empacotar JAR**      | `./mvnw clean package`                    |
| **Build sem executar testes**     | `./mvnw clean package -DskipTests`        |
| **Iniciar contêiner do banco**    | `docker compose up -d`                    |
| **Parar contêiner do banco**      | `docker compose down`                     |
| **Visualizar logs do banco**      | `docker compose logs -f reimburseflow-db` |

---

## Documentação da API e Endpoints

### Documentação Interativa (Swagger UI)

Com a aplicação em execução, acesse a interface do Swagger UI e a especificação OpenAPI em:

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Schema OpenAPI (JSON):** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Fluxo de Autenticação no Swagger

1. Envie uma requisição `POST` para `/api/v1/auth/login` com credenciais válidas de usuário.
2. Copie a string `token` retornada na resposta JSON.
3. Clique no botão **Authorize** (canto superior direito do Swagger UI) e cole o token (o prefixo `Bearer` é adicionado automaticamente).

### Usuário Padrão (Seed)

A migração inicial (`V2__insert_test_user.sql`) insere automaticamente uma conta padrão de colaborador:

- **E-mail:** `employee@reimburseflow.com`
- **Perfil (Role):** `ROLE_EMPLOYEE`
- *Nota: O hash da senha é provisionado via BCrypt no script de migração.*

### Principais Endpoints

#### Autenticação (`/api/v1/auth`)

- `POST /api/v1/auth/login` - Autentica o usuário e retorna um token JWT assinado.

#### Reembolsos (`/api/v1/reimbursement`)

- `POST /api/v1/reimbursement` - Cria um novo rascunho de reembolso (`ROLE_EMPLOYEE`, `ROLE_MANAGER`).
- `GET /api/v1/reimbursement` - Lista todas as solicitações de reembolso (`ROLE_MANAGER`).
- `POST /api/v1/reimbursement/{id}/submit` - Submete um rascunho de reembolso para revisão (`ROLE_EMPLOYEE`, `ROLE_MANAGER`).
- `POST /api/v1/reimbursement/{id}/approve` - Aprova um reembolso submetido (`ROLE_MANAGER`).
- `POST /api/v1/reimbursement/{id}/reject` - Rejeita um reembolso submetido (`ROLE_MANAGER`).
- `POST /api/v1/reimbursement/{id}/pay` - Marca um reembolso aprovado como pago (`ROLE_FINANCIAL`).

---

## Testes

O projeto conta com suítes de testes unitários e de integração:

- **Testes Unitários:** Validam entidades de domínio (`ReimbursementTest`) e casos de uso da aplicação (`CreateReimbursementUseCaseTest`).
- **Testes de Integração:** Utilizam **Testcontainers** (`AbstractIntegrationTest`, `ReimbursementIntegrationTest`) para rodar testes contra um contêiner efêmero do PostgreSQL 16, validando repositórios, migrações, segurança e controllers.

Para executar a suíte de testes:

```bash
./mvnw test
```

> **Nota:** O daemon do Docker deve estar em execução para permitir que o Testcontainers inicialize o contêiner de teste do PostgreSQL.

---

## Estrutura do Projeto

O projeto está estruturado seguindo as camadas da Clean Architecture / Arquitetura Hexagonal:

```
reimburseflow/
├── docker-compose.yml              # Definição do serviço local do PostgreSQL
├── pom.xml                         # Dependências Maven e configurações de build
├── src/
│   ├── main/
│   │   ├── java/com/pedromolon/reimburseflow/
│   │   │   ├── ReimburseflowApplication.java   # Ponto de entrada Spring Boot
│   │   │   ├── domain/                         # Regras de negócio corporativas e entidades
│   │   │   │   ├── enums/                      # Enums (ReimbursementStatus, Category)
│   │   │   │   ├── gateway/                    # Interfaces de repositório / ports
│   │   │   │   ├── Money.java                  # Value Object para valores monetários
│   │   │   │   ├── Reimbursement.java          # Entidade principal de Reembolso
│   │   │   │   ├── ReimbursementHistory.java   # Entidade de histórico de auditoria
│   │   │   │   ├── Role.java                   # Entidade de Perfil (Role)
│   │   │   │   └── User.java                   # Entidade de Usuário
│   │   │   ├── usecase/                        # Casos de uso da aplicação / interactors
│   │   │   │   ├── dto/                        # DTOs específicos dos casos de uso
│   │   │   │   ├── CreateReimbursementUseCase.java
│   │   │   │   ├── SubmitReimbursementUseCase.java
│   │   │   │   ├── ApproveReimbursementUseCase.java
│   │   │   │   ├── RejectReimbursementUseCase.java
│   │   │   │   └── PayReimbursementeUseCase.java
│   │   │   ├── application/                    # Serviços de aplicação e mappers
│   │   │   │   ├── mapper/                     # Mappers de Domínio para DTO
│   │   │   │   └── service/                    # Serviços de negócio (AuthService, ReimbursementService)
│   │   │   └── infrastructure/                 # Frameworks, drivers e adaptadores
│   │   │       ├── configuration/              # Configurações do OpenAPI / Swagger
│   │   │       ├── entity/                     # Entidades de banco de dados JPA
│   │   │       ├── mapper/                     # Mappers de entidades
│   │   │       ├── persistence/                # Adaptadores de gateway JPA
│   │   │       ├── repository/                 # Repositórios Spring Data JPA
│   │   │       ├── security/                   # Config de segurança, provedor JWT, chaves RSA
│   │   │       └── web/                        # Controllers REST, DTOs, Exception handlers
│   │   └── resources/
│   │       ├── application.yaml                # Configuração principal da aplicação
│   │       ├── certs/                          # Pares de chaves pública e privada RSA (app.pub, app.key)
│   │       └── db/migration/                   # Scripts de migração de banco Flyway
│   └── test/
│       ├── java/com/pedromolon/reimburseflow/  # Suíte de testes (Unitários, Controllers, Integração)
│       └── resources/
│           └── application-test.yaml           # Configuração de perfil de testes
```

---

## TODOs e Roadmap

- [ ] **TODO:** Definir a licença oficial do projeto (atualmente não atribuída no `pom.xml`).
- [ ] **TODO:** Implementar endpoint de cadastro de usuários (`/api/v1/auth/register`) com atribuição de perfis.
- [ ] **TODO:** Adicionar funcionalidade de anexo/upload de comprovantes (ex.: S3 ou armazenamento local) para solicitações de reembolso.
- [ ] **TODO:** Implementar notificações automatizadas por e-mail para transições de status (submetido, aprovado, rejeitado, pago).
- [ ] **TODO:** Adicionar paginação e filtros avançados no endpoint `GET /api/v1/reimbursement`.
- [ ] **TODO:** Corrigir erro de digitação no nome da classe `PayReimbursementeUseCase` -> `PayReimbursementUseCase` e na anotação de perfil `ROLE_MENAGER` -> `ROLE_MANAGER`.

---

## Licença

TODO: Especificar a licença do projeto (ex.: MIT, Apache 2.0).