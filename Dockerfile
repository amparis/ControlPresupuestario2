FROM openjdk:8-alpine
MAINTAINER edissonfullstackdeveloper.com
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} contabilidad-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/contabilidad-0.0.1-SNAPSHOT.jar"]