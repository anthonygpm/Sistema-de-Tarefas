# Sistema de Tarefas (Spring Boot)

API REST para gerenciar tarefas (criar, listar, atualizar, concluir e deletar).

## Stack

- Java 17
- Spring Boot 3
- PostgreSQL 15
- Maven
- Docker / Docker Compose (opcional, recomendado para subir o Postgres rápido)
- OpenAPI/Swagger (springdoc)

## Endpoints

Base path: `http://localhost:8080`

- `GET /tarefas` – lista tarefas
- `GET /tarefas/{id}` – busca por id
- `POST /tarefas` – cria tarefa
- `PUT /tarefas/{id}` – atualiza tarefa
- `PATCH /tarefas/{id}/completed` – marca como concluída
- `DELETE /tarefas/{id}` – remove

## Swagger / OpenAPI

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

> Obs.: se você mudou o path do springdoc nas properties, ajuste as URLs acima.

## Perfis (profiles) e configuração do banco

Este projeto usa PostgreSQL.

- **Profile padrão (sem profile):** conecta em `localhost:5432` (veja `src/main/resources/application.properties`).
- **Profile `docker`:** conecta no host `db` (container do Postgres no compose), veja `src/main/resources/application-docker.properties`.

### Configuração (padrão / local)

Em `src/main/resources/application.properties`:

- `spring.datasource.url=jdbc:postgresql://localhost:5432/tarefas`
- `spring.datasource.username=postgres`
- `spring.datasource.password=postgres`

### Configuração (docker)

Em `src/main/resources/application-docker.properties`:

- `spring.datasource.url=jdbc:postgresql://db:5432/tarefas`
- `spring.datasource.username=postgres`
- `spring.datasource.password=postgres`

## Como rodar (IntelliJ)

Checklist:

1. Garanta que existe um PostgreSQL rodando em `localhost:5432` com:
   - database: `tarefas`
   - user: `postgres`
   - password: `postgres`
2. Garanta que a porta `8080` está livre.
3. Rode a classe `dev.anthony.tarefas.TarefasApplication`.

Se quiser forçar profile:

- **Profile padrão (local)**: não precisa setar nada.
- **Profile docker** (somente se você estiver rodando a API dentro do Docker Compose): defina `SPRING_PROFILES_ACTIVE=docker`.

## Como rodar com Docker Compose (API + Postgres)

O `docker-compose.yml` sobe:

- `db` (Postgres) na porta `5432:5432`
- `api` (Spring Boot) na porta `8080:8080` com `SPRING_PROFILES_ACTIVE=docker`

Passos:

1. Gere o JAR localmente:

   ```bash
   ./mvnw -DskipTests package
   ```

2. Suba os containers:

   ```bash
   docker compose up --build
   ```

Depois acesse:

- API: `http://localhost:8080/tarefas`
- Swagger: `http://localhost:8080/swagger-ui/index.html`

## Troubleshooting

### 1) Erro: `Connection to localhost:5432 refused`

Isso significa que **não tem PostgreSQL rodando** em `localhost:5432` (ou a porta não está acessível).

Opções:

- Subir o Postgres via Docker:
  ```bash
  docker compose up db
  ```
  E rodar a aplicação no IntelliJ usando o profile padrão (que aponta para `localhost`).

- Se você estiver rodando a aplicação **dentro do Docker**, use o profile `docker` (ele aponta para `db:5432`).

### 2) Erro: `Port 8080 was already in use`

A porta `8080` já está ocupada por outro processo/serviço (não é o `EXPOSE` do Dockerfile que “ocupa” a porta; `EXPOSE` só documenta a porta).

Como identificar no Windows:

```powershell
netstat -ano | findstr :8080
```

Com o `PID` em mãos:

```powershell
tasklist /FI "PID eq 6140"
```

E para finalizar (se for seguro):

```powershell
taskkill /PID 6140 /F
```

> Também pode ser um container mapeando `8080:8080`. Verifique:

```bash
docker ps --format "table {{.Names}}\t{{.Ports}}"
```

Se preferir não matar o processo, mude a porta da aplicação:

- Via IntelliJ (VM options): `-Dserver.port=8081`
- Ou em `application.properties`: `server.port=8081`

### 3) Conflito com Docker Compose (`8080:8080`)

Se você deixou o `docker compose up` rodando, ele vai publicar a API na 8080, e ao tentar rodar no IntelliJ dará conflito.

Soluções:

- Pare os containers: `docker compose down`
- Ou rode a aplicação local em outra porta (ex.: `8081`).

## Build e testes

```bash
./mvnw test
```

```bash
./mvnw package
```
