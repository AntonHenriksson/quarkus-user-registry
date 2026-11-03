# quarkus-user-registry

A user registry REST API built with **Quarkus** — written as a learning project to
get hands-on with Quarkus after working in Spring Boot.

It covers the parts of a user service you actually need: registration, login with
JWT, profile updates, password changes and role-protected admin endpoints, with
Bean Validation on the DTOs and integration tests on the resources.

## Tech

| Layer      | Technology                                              |
|------------|---------------------------------------------------------|
| Framework  | Quarkus 3.32 (Java 17, Maven)                           |
| API        | Jakarta REST (quarkus-rest) + Jackson                   |
| Persistence| Hibernate ORM with Panache, MariaDB (quarkus-jdbc-mariadb) |
| Security   | SmallRye JWT (signed tokens), `@RolesAllowed`, `@Authenticated` |
| Validation | Hibernate Validator on request DTOs                     |
| API docs   | SmallRye OpenAPI (Swagger UI)                           |
| Tests      | JUnit 5 + RestAssured + `quarkus-test-security-jwt`     |

## Endpoints

| Method | Path                       | Access        |
|--------|----------------------------|---------------|
| POST   | `/api/auth/login`          | Public        |
| POST   | `/api/users/register`      | Public        |
| GET    | `/api/users/id/{id}`       | `ADMIN`       |
| GET    | `/api/users/email/{email}` | Authenticated |
| PATCH  | `/api/users/{id}`          | Authenticated |
| PATCH  | `/api/users/{id}/password` | Authenticated |
| DELETE | `/api/users/{id}`          | `ADMIN`       |

## Run locally

Quarkus needs a signing key pair for the JWTs. Generate one and drop the files in
`src/main/resources/` (they are not committed):

```bash
openssl genpkey -algorithm RSA -out privateKey.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in privateKey.pem -out publicKey.pem
```

Then start the dev server:

```bash
./mvnw quarkus:dev
```

- API: <http://localhost:8080/api/users>
- Swagger UI: <http://localhost:8080/q/swagger-ui>

Database settings live in `src/main/resources/application.properties`
(`quarkus.datasource.*`). The tests run against the Quarkus test profile and do
not need a running MariaDB.

## Tests

```bash
./mvnw test
```

## Structure

```
src/main/java
├── controller/   # AuthResource, AppUserResource
├── service/      # AppUserService, TokenService
├── domain/       # AppUser entity (Panache)
├── dto/          # request/response records + validation
├── mapper/       # entity <-> DTO
└── exception/    # GlobalExceptionHandler
```

## Notes

This is a learning project, not a production service. The next steps I would take
are refresh tokens, hashed passwords with a proper cost factor, and pagination on
the lookup endpoints.
