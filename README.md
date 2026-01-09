# Servicio de Transacciones

Este proyecto es una API reactiva para la gestión de transacciones financieras, construida con Java 17, Spring Boot 3, y siguiendo los principios de Arquitectura Limpia.

## Pila Tecnológica

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)
![Project Reactor](https://img.shields.io/badge/Project%20Reactor-reactive-blueviolet)
![H2 Database](https://img.shields.io/badge/h2-R2DBC-orange)
![Gradle](https://img.shields.io/badge/Gradle-8.4-lightgrey)
![JUnit 5](https://img.shields.io/badge/JUnit-5-green)

- **Lenguaje:** Java 17
- **Framework:** Spring Boot 3 (WebFlux)
- **Programación Asíncrona:** Project Reactor
- **Base de Datos:** H2 con R2DBC
- **Herramienta de Construcción:** Gradle
- **Pruebas:** JUnit 5

## Estructura del Proyecto

El proyecto sigue los principios de Arquitectura Limpia, promoviendo una separación de responsabilidades y creando una aplicación más mantenible y escalable.

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

- **`domain`**: Contiene la lógica de negocio de la aplicación.
  - **`model`**: Representa las entidades de negocio.
  - **`usecase`**: Implementa las reglas de negocio y orquesta el flujo de datos.
- **`infrastructure`**: Contiene las dependencias externas e implementaciones de las interfaces definidas en el dominio.
  - **`driven-adapters`**: Implementa la comunicación saliente con sistemas externos, como bases de datos u otras APIs.
  - **`entry-points`**: Contiene la comunicación entrante con la aplicación, como controladores REST.
- **`applications`**: Contiene la clase principal de la aplicación y la configuración de la misma.

## Referencia de la API

| Método | Endpoint                  | Descripción                      | Ejemplo de Payload               |
|--------|---------------------------|----------------------------------|------------------------------------|
| `POST` | `/api/v1/transactions`    | Crea una nueva transacción.      | `{"amount": 100.50}`               |
| `GET`  | `/api/v1/transactions`    | Recupera todas las transacciones.| N/A                                |

## Requisitos Previos

- JDK 17

## Configuración y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone <repository-url>
    ```
2.  **Configurar la base de datos:**
    - La aplicación está configurada para conectarse a una base de datos H2.
    - Las propiedades de conexión de la base de datos se pueden encontrar y modificar en `applications/app-service/src/main/resources/application.yaml`.

3.  **Construir el proyecto:**
    ```bash
    ./gradlew build
    ```
4.  **Ejecutar la aplicación:**
    ```bash
    ./gradlew bootRun
    ```

## Pruebas

Para ejecutar las pruebas unitarias, ejecute el siguiente comando:

```bash
./gradlew test
```