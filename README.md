# RideTogether

RideTogether is a full-stack group bike-trip planning application.

It helps a group create trips, add members, split expenses exactly, calculate settlements, and make shared decisions through polls.

## Features

- Create trips with destination and travel dates
- Persist trip data using an H2 database
- Add trip members
- Add expenses in rupees with paise-based backend calculations
- Calculate minimal settlement payments
- Reload saved trips after a browser refresh
- Create group decision polls
- Allow each member to change their vote without double-counting
- Display all features through a React frontend

## Tech stack

| Layer | Technology |
| --- | --- |
| Frontend | React, JavaScript, Vite, CSS |
| Backend | Java 17, Spring Boot |
| Database | H2, Spring Data JPA, Hibernate |
| Testing | JUnit 5, MockMvc |
| Build tools | Maven, npm |

## Architecture

```text
React frontend
    -> fetch /api/...
Vite development proxy
    ->
Spring Boot REST controllers
    ->
TripService business logic
    ->
JPA / Hibernate
    ->
H2 database
```

## Core design decisions

### Exact money calculations

The backend stores money in paise using `long`.

```text
Rs. 15000.75 -> 1,500,075 paise
```

This avoids floating-point rounding errors when calculating balances and settlements.

### Separation of responsibilities

```text
Controller -> HTTP requests and JSON responses
Service    -> application workflow and database access
Trip       -> business rules and calculations
DTOs       -> API request/response shapes
```

### Voting rule

Each member can have only one vote per poll. If a member chooses another option later, the old vote is removed and the new vote is recorded.

## Run locally

### Prerequisites

- Java 17
- Node.js and npm

### Start the backend

```powershell
.\mvnw.cmd spring-boot:run
```

The API starts at:

```text
http://localhost:8080
```

### Start the frontend

```powershell
cd frontend
npm install
npm run dev
```

Open:

```text
http://localhost:5173
```

## Run tests

```powershell
.\mvnw.cmd test
```

## Build the frontend

```powershell
cd frontend
npm run build
```

## Important API endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| POST | `/api/trips` | Create a trip |
| GET | `/api/trips` | List saved trips |
| POST | `/api/trips/{tripId}/members` | Add a member |
| GET | `/api/trips/{tripId}/members` | View members |
| POST | `/api/trips/{tripId}/expenses` | Add an expense |
| GET | `/api/trips/{tripId}/expenses` | View expenses |
| GET | `/api/trips/{tripId}/settlements` | Calculate settlements |
| POST | `/api/trips/{tripId}/polls` | Create a group poll |
| GET | `/api/trips/{tripId}/polls` | View polls and vote counts |
| POST | `/api/trips/{tripId}/polls/{pollId}/votes` | Vote on a poll |

## What I learned

- Core Java OOP, encapsulation, composition, and validation
- Exact currency calculations using integers instead of `double`
- Spring Boot REST APIs and dependency injection
- DTOs for API request and response data
- JPA entities, relationships, lazy loading, and transactions
- H2 database persistence
- Unit, controller, and integration testing
- React state, props, forms, components, and API calls
- Connecting a React frontend to a Spring Boot backend
