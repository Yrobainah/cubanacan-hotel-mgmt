# Cubanacan Hotel Management

Base para una API profesional de gestión hotelera construida con Java 21 y Spring Boot.

## Requisitos

- Java 21
- Docker, para PostgreSQL durante el desarrollo

## Backend

El backend se encuentra en `backend/api`.

```powershell
cd backend/api
.\mvnw.cmd clean verify
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

## Perfiles

- `dev`: PostgreSQL local; permite sobrescribir `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD`.
- `test`: H2 en memoria y Flyway desactivado para pruebas reproducibles.
- `prod`: exige `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD`; mantiene Flyway y validación JPA.

La estructura base separa controladores, servicios, dominio, repositorios y configuración. El dominio hotelero todavía no está implementado.

## Docker

```bash
docker compose up --build
docker compose down
docker compose logs -f backend
docker compose logs -f postgres
```
