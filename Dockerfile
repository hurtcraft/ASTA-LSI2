FROM openjdk:26-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/asta.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/asta.jar","--spring.profiles.active=prod"]