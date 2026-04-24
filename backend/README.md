# Backend - BarberShopQueueState

Backend Java Spring Boot para gestión de turnos de barbería aplicando el patrón State.

## Tecnologías

- Java
- Spring Boot
- REST API
- In-memory storage
- State Design Pattern

## Ejecución

Desde esta carpeta (`backend/`):

```bash
mvn spring-boot:run
```

## Puerto

- `server.port=8080`

## Endpoints

- `GET /api/health`
- `POST /api/appointments`
- `GET /api/appointments`
- `GET /api/appointments/{id}`
- `PUT /api/appointments/{id}/waiting`
- `PUT /api/appointments/{id}/start`
- `PUT /api/appointments/{id}/complete`
- `PUT /api/appointments/{id}/cancel`

## State Pattern

Cada cita delega sus transiciones al objeto de estado actual.

Flujo válido principal:

`Registered -> Waiting -> InService -> Completed`

Flujos alternativos:

`Registered -> Cancelled`
`Waiting -> Cancelled`