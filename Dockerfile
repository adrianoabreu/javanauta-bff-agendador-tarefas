FROM maven:3.9.10-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTest

FROM alpine/java:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar /app/bff-agendador-tarefas.jar
EXPOSE 8083
CMD ["java","-jar","/app/bff-agendador-tarefas.jar"]