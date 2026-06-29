# Week 4 - Spring REST and Microservices

## spring rest

- Hello World REST endpoint
- Country CRUD - GET all, GET by code, POST, PUT, DELETE
- DTO mapping between entity and response
- @Valid with bean validation annotations
- @RestControllerAdvice for centralised exception handling
- Spring Boot Actuator endpoints
- Swagger UI for API docs

## jwt security

- JwtTokenProvider - generates and validates tokens
- JwtAuthenticationFilter - runs on every request
- SecurityConfig - stateless session, public vs protected routes
- login endpoint returns Bearer token

**test flow:**
```bash
# get token
POST /api/auth/login
{"username":"mani","password":"password123"}

# use token
GET /api/countries
Authorization: Bearer <token>
```

## microservices

four separate spring boot apps:

| service | port | what it does |
|---------|------|-------------|
| eureka-discovery-server | 8761 | service registry |
| account-service | 8081 | GET /accounts/{number} |
| loan-service | 8082 | GET /loans/{number} |
| api-gateway | 9090 | routes to account and loan |

**start order:**
```bash
# 1
cd eureka-discovery-server && mvn spring-boot:run
# 2
cd account-service && mvn spring-boot:run
cd loan-service && mvn spring-boot:run
# 3
cd api-gateway && mvn spring-boot:run
```

test via gateway: http://localhost:9090/accounts/123

## run tests
```bash
cd SpringREST
mvn clean test
```
