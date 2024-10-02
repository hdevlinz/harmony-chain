#FROM eclipse-temurin:21-jre-alpine
FROM amazoncorretto:21.0.4
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
