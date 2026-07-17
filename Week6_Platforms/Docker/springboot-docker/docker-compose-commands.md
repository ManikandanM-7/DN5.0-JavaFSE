# Docker Compose Commands

## how to run this

```bash
# first build the spring boot jar
cd ../../Week4_REST_Microservices/SpringREST
mvn clean package -DskipTests

# copy jar here or update Dockerfile path
# then from this folder:

# build and start all services
docker-compose up --build

# run in background
docker-compose up -d --build

# see running services
docker-compose ps

# view logs
docker-compose logs
docker-compose logs spring-app
docker-compose logs -f mysql-db

# stop all
docker-compose down

# stop and remove volumes too
docker-compose down -v
```

## microservices with docker compose
# can also run the microservices setup

```yaml
# add eureka, account, loan, gateway as separate services
# each gets its own container
# they talk to each other using service names
```

## what i learned
- docker-compose makes multi-container apps easy
- services can reference each other by name in the network
- depends_on ensures services start in the right order
- healthcheck makes sure db is ready before app starts
- volumes persist data even when container restarts
