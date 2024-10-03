#FROM eclipse-temurin:21-jre-alpine
FROM amazoncorretto:21.0.4
RUN apk add --no-cache bash
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
COPY target/*.jar app.jar
ENTRYPOINT [ "./wait-for-it.sh", "product-service:8084", "--timeout=300", "--", "java", "-jar", "/app.jar" ]
