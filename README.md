# Transaction Service

This project is a reactive API for managing financial transactions, built with Java 17, Spring Boot 3, and following Clean Architecture principles.

## Tech Stack

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)
![Project Reactor](https://img.shields.io/badge/Project%20Reactor-reactive-blueviolet)
![MySQL](https://img.shields.io/badge/MySQL-R2DBC-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.4-lightgrey)
![JUnit 5](https://img.shields.io/badge/JUnit-5-green)

- **Language:** Java 17
- **Framework:** Spring Boot 3 (WebFlux)
- **Asynchronous Programming:** Project Reactor
- **Database:** MySQL with R2DBC
- **Build Tool:** Gradle
- **Testing:** JUnit 5

## Project Structure

The project follows the Clean Architecture principles, promoting a separation of concerns and creating a more maintainable and scalable application.

```
.
├── applications
│   └── app-service
├── domain
│   ├── model
│   └── usecase
└── infrastructure
    ├── driven-adapters
    │   ├── r2dbc-mysql
    │   └── rest-consumer
    └── entry-points
        └── reactive-web
```

- **`domain`**: Contains the business logic of the application.
  - **`model`**: Represents the business entities.
  - **`usecase`**: Implements the business rules and orchestrates the flow of data.
- **`infrastructure`**: Contains the external dependencies and implementations of the interfaces defined in the domain.
  - **`driven-adapters`**: Implements the outbound communication with external systems, such as databases or other APIs.
  - **`entry-points`**: Contains the inbound communication with the application, such as REST controllers.
- **`applications`**: Contains the main application class and the configuration of the application.

## API Reference

| Method | Endpoint                  | Description                | Payload Example                          |
|--------|---------------------------|----------------------------|------------------------------------------|
| `POST` | `/api/v1/transactions`    | Creates a new transaction. | `{"amount": 100.50}`                     |
| `GET`  | `/api/v1/transactions`    | Retrieves all transactions.| N/A                                      |

## Prerequisites

- JDK 17
- Docker (for running a local MySQL database)

## Setup and Execution

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    ```
2.  **Configure the database:**
    - The application is configured to connect to a MySQL database. You can use the following Docker command to start a local instance:
      ```bash
      docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=transactions -d mysql:8.0
      ```
    - The database connection properties can be found and modified in `applications/app-service/src/main/resources/application.yaml`.

3.  **Build the project:**
    ```bash
    ./gradlew build
    ```
4.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```

## Testing

To run the unit tests, execute the following command:

```bash
./gradlew test
```
