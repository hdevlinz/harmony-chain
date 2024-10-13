#FROM amazoncorretto:21.0.4

FROM eclipse-temurin:21-jre-alpine

RUN apk add --no-cache bash

COPY wait-for-it.sh wait-for-it.sh

RUN chmod +x wait-for-it.sh

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

LABEL org.opencontainers.image.source=https://github.com/hiepthanhtran/microservices-scms