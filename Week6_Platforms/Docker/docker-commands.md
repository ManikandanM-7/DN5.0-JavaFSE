# Docker HOL - Containerization

## basic docker commands

```bash
# check docker is installed
docker --version
docker info

# pull an image from docker hub
docker pull hello-world
docker pull nginx
docker pull openjdk:17

# list downloaded images
docker images

# run a container
docker run hello-world

# run nginx in background on port 8080
docker run -d -p 8080:80 --name my-nginx nginx

# list running containers
docker ps

# list all containers including stopped ones
docker ps -a

# stop a container
docker stop my-nginx

# start it again
docker start my-nginx

# remove container
docker rm my-nginx

# remove image
docker rmi nginx

# view logs
docker logs my-nginx
docker logs -f my-nginx  # follow logs

# exec into running container
docker exec -it my-nginx bash
```

## docker images

```bash
# build an image from Dockerfile
docker build -t my-spring-app:1.0 .

# tag an image
docker tag my-spring-app:1.0 my-spring-app:latest

# push to docker hub
docker login
docker push manikandan/my-spring-app:1.0
```

## docker volumes
```bash
# create volume
docker volume create mydata

# mount volume when running container
docker run -v mydata:/app/data my-spring-app

# list volumes
docker volume ls

# remove volume
docker volume rm mydata
```

## docker networking
```bash
# list networks
docker network ls

# create custom network
docker network create my-network

# run container on custom network
docker run --network my-network --name app my-spring-app

# containers on same network can reach each other by name
```
