FROM openjdk:8-alpine
MAINTAINER yggdrasil
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/ControlPresupuestario-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ControlPresupuestario-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ControlPresupuestario-0.0.1-SNAPSHOT.jar"]