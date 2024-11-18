# Etapa 1: Compilar el proyecto
FROM maven:3.8.7-openjdk-8 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Construir la imagen final
FROM openjdk:8-alpine
WORKDIR /app
COPY --from=build /app/target/ControlPresupuestario-0.0.1-SNAPSHOT.jar ControlPresupuestario-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/ControlPresupuestario-0.0.1-SNAPSHOT.jar"]