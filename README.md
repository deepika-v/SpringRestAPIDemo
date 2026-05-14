# SpingRestAPIDemo

This project demonstrates REST API design concepts, including Swagger/OpenAPI integration, API versioning, and comprehensive unit testing with 80% code coverage.

## Features

- **REST API Design Concepts**:
  - Proper HTTP methods (GET, POST, PUT, DELETE)
  - Resource naming conventions
  - Status codes and error handling
  - Request/response validation
  - HATEOAS (Hypermedia As The Engine Of Application State) in v2

- **API Versioning**:
  - URL-based versioning (`/v1/users`, `/v2/users`)
  - Backward compatibility between versions
  - Enhanced features in v2 (HATEOAS links)

- **Swagger/OpenAPI Integration**:
  - Interactive API documentation at `/swagger-ui.html`
  - OpenAPI 3.0 specification
  - Detailed operation descriptions and examples

- **Comprehensive Testing**:
  - Unit tests for services and controllers
  - Mocking with Mockito
  - Code coverage reporting with JaCoCo (target: 80%)

## Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use included Maven wrapper)

## Getting Started

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd spingrestapidemo
   ```

2. **Build the project**:
   ```bash
   mvn clean compile
   ```

3. **Run the tests**:
   ```bash
   mvn test
   ```

4. **Check code coverage**:
   ```bash
   mvn jacoco:report
   ```
   View the report at `target/site/jacoco/index.html`

5. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the Swagger UI at:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## API Endpoints

### Version 1 (`/v1/users`)

- `GET /v1/users` - Get all users
- `GET /v1/users/{id}` - Get user by ID
- `POST /v1/users` - Create a new user
- `PUT /v1/users/{id}` - Update an existing user
- `DELETE /v1/users/{id}` - Delete a user

### Version 2 (`/v2/users`)

Same endpoints as v1, but with HATEOAS links in responses for better discoverability.

### Example Requests

**Create a User**:
```bash
curl -X POST http://localhost:8080/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'
```

**Get All Users (v2 with HATEOAS)**:
```bash
curl http://localhost:8080/v2/users
```

Response includes `_links` for navigation.

## Project Structure

```
src/
├── main/java/com/example/demo/
│   ├── config/          # OpenAPI configuration
│   ├── controller/
│   │   ├── v1/          # Version 1 controllers
│   │   └── v2/          # Version 2 controllers with HATEOAS
│   ├── model/           # Data models (User)
│   ├── service/         # Business logic (UserService)
│   └── exception/       # Global exception handling
└── test/java/com/example/demo/
    ├── controller/      # Controller tests
    └── service/         # Service tests
```

## Testing

Run tests with:
```bash
mvn test
```

For coverage report:
```bash
mvn test jacoco:report
```

Target coverage: 80% overall (lines, branches, methods).

## Architecture Notes

- **Versioning Strategy**: URL-based versioning allows clients to pin to specific versions while new versions can evolve independently.
- **HATEOAS**: Version 2 demonstrates REST maturity by providing links for state transitions, enabling more discoverable APIs.
- **Error Handling**: Consistent error responses with appropriate HTTP status codes and detailed messages.
- **Validation**: Input validation using Bean Validation annotations with meaningful error messages.
- **Testing**: Unit tests focus on behavior, using mocks for dependencies to ensure fast, reliable tests.

## Extending the Project

- Add more entities (e.g., Products, Orders)
- Implement authentication/authorization
- Add database persistence (JPA/Hibernate)
- Implement pagination and filtering
- Add integration tests
- Deploy to cloud platforms

## Technologies Used

- Spring Boot 3.2.0
- Spring Web
- Spring Validation
- Spring HATEOAS
- SpringDoc OpenAPI
- JUnit 5
- Mockito
- AssertJ
- JaCoCo

## Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure code coverage remains above 80%
5. Submit a pull request
