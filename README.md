# Transport Management System

Web application for managing the transport system with user authorization.

## Description

Transport Management System is a modern Spring Boot web application for managing a bus fleet, passengers, and ticket sales. The system includes user authorization, protected pages, and REST API.

## Features

### Public access (no authorization required):
- Main page with statistics
- View bus list
- View passenger list
- View tickets and available seats

### Requires authorization:
- Purchase tickets
- Add buses
- Add passengers
- Manage data

### Authorization:
- Register new users
- User login
- Password protection (BCrypt)
- User roles

## Technologies

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Security** - authorization and authentication
- **Spring Data JPA** - database access
- **PostgreSQL** - relational database
- **Thymeleaf** - HTML template engine
- **Maven** - build tool
- **HTML/CSS** - modern UI

## Quick Start

### Requirements:
- Java JDK 21 or higher
- PostgreSQL
- Maven (or use built-in Maven Wrapper)

### Database Setup:

Create a PostgreSQL database and configure `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Run:

1. **Clone the repository:**
```bash
git clone https://github.com/Ramazan-dev012/Transport-Management-System-WAD.git
cd Transport-Management-System-WAD
```

2. **Start the application:**
```bash
mvnw spring-boot:run
```

Or via IDE (IntelliJ IDEA):
- Open the project
- Run `TransportSystemApplication.java`

3. **Open in browser:**
```
http://localhost:8080/transport
```

## Project Structure

```
src/main/java/com/example/transportsystem/
├── model/
│   ├── BusEntity.java          # Bus entity
│   ├── PassengerEntity.java    # Passenger entity
│   ├── TicketEntity.java       # Ticket entity
│   └── User.java               # User entity
├── repository/
│   ├── BusRepository.java      # Bus queries (available/full buses)
│   ├── PassengerRepository.java
│   ├── TicketRepository.java
│   └── UserRepository.java
├── service/
│   ├── TransportManagementService.java  # Core business logic
│   ├── AuthService.java                 # Registration service
│   └── CustomUserDetailsService.java   # Spring Security integration
├── config/
│   └── SecurityConfig.java             # Spring Security configuration
├── controller/
│   ├── HomeController.java             # Root redirect
│   ├── AuthController.java             # Login / Register pages
│   ├── TransportController.java        # MVC controller (Thymeleaf pages)
│   └── TransportRestController.java    # REST API controller
└── TransportSystemApplication.java     # Spring Boot entry point

src/main/resources/
├── templates/
│   ├── auth/
│   │   ├── login.html          # Login page
│   │   └── register.html       # Registration page
│   ├── main.html               # Dashboard with statistics
│   ├── buses.html              # Bus list
│   ├── passengers.html         # Passenger list
│   ├── viewTickets.html        # View tickets and available buses
│   ├── buyTicket.html          # Buy ticket
│   ├── addBus.html             # Add bus form
│   └── addPassenger.html       # Add passenger form
├── static/
│   ├── css/style.css           # Styles
│   └── api-tester.html         # REST API tester
└── application.properties      # Application configuration
```

## Data Models

### BusEntity (buses table)
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| routeNumber | String | Route number |
| capacity | int | Total seat capacity |
| driverName | String | Driver name |
| currentPassengers | int | Current passenger count |

### PassengerEntity (passengers table)
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| name | String | Full name |
| phoneNumber | String | Phone number |
| destination | String | Destination |
| hasTicket | boolean | Ticket status |

### TicketEntity (tickets table)
| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| passenger | PassengerEntity | Passenger (FK) |
| bus | BusEntity | Bus (FK) |
| seatNumber | int | Seat number |
| purchasedAt | LocalDateTime | Purchase time |

## Available Pages

| URL | Description | Auth required |
|-----|-------------|---------------|
| `/transport` | Dashboard with statistics | No |
| `/transport/buses` | Bus list | No |
| `/transport/passengers` | Passenger list | No |
| `/transport/viewTickets` | View tickets and available buses | No |
| `/transport/buses/add` | Add bus form | Yes |
| `/transport/passengers/add` | Add passenger form | Yes |
| `/transport/buyTicket` | Buy ticket | Yes |
| `/auth/login` | Login page | No |
| `/auth/register` | Registration page | No |

## REST API

Base URL: `/api/transport`

### Buses
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/buses` | Get all buses |
| GET | `/buses/{id}` | Get bus by ID |
| GET | `/buses/available` | Get available buses (free seats) |
| GET | `/buses/full` | Get full buses |
| POST | `/buses` | Add bus |
| DELETE | `/buses/{id}` | Delete bus |

### Passengers
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/passengers` | Get all passengers |
| GET | `/passengers/{id}` | Get passenger by ID |
| GET | `/passengers/withTickets` | Get passengers with tickets |
| POST | `/passengers` | Add passenger |
| DELETE | `/passengers/{id}` | Delete passenger |

### Tickets
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tickets` | Get all tickets |
| POST | `/tickets` | Buy ticket |

### Statistics
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/statistics` | Get system statistics |

## Configuration

### Server port:
```properties
server.port=8080
```

## License

This project was created for educational purposes.

## Author

**Ramazan**
- GitHub: [@Ramazan-dev012](https://github.com/Ramazan-dev012)
