# BarberShopQueueState

University workshop project with a separated **backend** and **frontend** architecture to manage barbershop appointments.

## 1. Project Description

This project implements an appointment system where each appointment changes state according to business rules controlled by the State Pattern.

## 2. Backend Technologies

- Java 17+
- Spring Boot
- REST API
- In-memory storage (Map)
- Global error handling
- CORS configuration

## 3. Frontend Technologies

- HTML
- CSS
- JavaScript (vanilla, no frameworks)

## 4. How the State Pattern Is Used

Each `Appointment` keeps a current state (`AppointmentState`) and delegates transitions to that state object.

Implemented states:

- Registered
- Waiting
- InService
- Completed
- Cancelled

Invalid transitions throw controlled backend errors.

## 5. Folder Structure

```text
BarberShopQueueState/
  backend/
    pom.xml
    README.md
    src/
      main/
        java/
        resources/
          application.properties
  frontend/
    index.html
    styles.css
    script.js
```

## 6. How to Run the Backend

1. Open a terminal in `backend/`.
2. Run:

```bash
mvn spring-boot:run
```

The backend runs at `http://localhost:8080`.

## 7. How to Open the Frontend

Recommended options:

1. Open `frontend/index.html` with Live Server (port 5500), or
2. Serve the `frontend/` folder with any static server.

The frontend consumes this API:

`http://localhost:8080/api/appointments`

## 8. Normal Test Flow

1. Create appointment (`POST /api/appointments`)
2. Move to waiting (`PUT /api/appointments/{id}/waiting`)
3. Start service (`PUT /api/appointments/{id}/start`)
4. Complete appointment (`PUT /api/appointments/{id}/complete`)

## 9. Invalid Flow Examples

- Registered cannot go directly to InService.
- Waiting cannot go directly to Completed.
- InService cannot be cancelled.
- Completed and Cancelled cannot change state.