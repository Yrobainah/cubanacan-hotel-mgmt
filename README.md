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

- `dev`: PostgreSQL local; permite sobrescribir `DB_URL`, `DB_USER` y `DB_PASS`.
- `test`: H2 en memoria y Flyway desactivado para pruebas reproducibles.
- `prod`: exige `DB_URL`, `DB_USER` y `DB_PASS`; mantiene Flyway y validación JPA.

La estructura base separa controladores, servicios, dominio, repositorios y configuración. El dominio hotelero todavía no está implementado.
