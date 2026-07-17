# using openjdk 17 as base image
FROM openjdk:17-jdk-slim

# set working directory inside container
WORKDIR /app

# copy the jar file built by maven
# run mvn clean package first to generate the jar
COPY target/*.jar app.jar

# expose port 8080
EXPOSE 8080

# command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
