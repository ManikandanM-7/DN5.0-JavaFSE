# Week 6 - Platforms (Git, Docker, Agile)

## what's in here

### Git HOLs (all 5 mandatory)
- HOL1 - git basics: init, add, commit, log, reset
- HOL2 - branching: create, switch, merge, resolve conflicts
- HOL3 - remote repos: push, pull, clone, fetch
- HOL4 - collaboration: fork, PR workflow, stash
- HOL5 - gitflow: feature/release/hotfix branch strategy

### Docker
- docker-commands.md - all basic docker commands with examples
- springboot-docker/ - Dockerfile + docker-compose for spring boot + mysql
- docker-compose-demo/ - docker-compose for all 4 microservices together

### Agile
- agile-scrum-notes.md - agile manifesto, scrum roles, ceremonies, story points, INVEST
- sample-user-stories.md - user stories in Given-When-Then format with sprint plan

## how to run docker stuff

```bash
# build spring boot app first
cd ../Week4_REST_Microservices/SpringREST
mvn clean package -DskipTests

# then run with docker compose
cd ../../Week6_Platforms/Docker/springboot-docker
docker-compose up --build
```
