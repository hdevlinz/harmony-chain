# Harmony Supply Chain System | Microservice Architecture

![SCMS](https://raw.githubusercontent.com/hiepthanhtran/microservices-scms/main/images/scms.png)

### SCMS is a pet project aim to practice building a typical microservice application in Java with Spring Boot, Next.js, and Flutter.

![api-gateway-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/gateway-ci.yaml/badge.svg)
![cart-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/cart-ci.yaml/badge.svg)
![file-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/file-ci.yaml/badge.svg)
![identity-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/identity-ci.yaml/badge.svg)
![inventory-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/inventory-ci.yaml/badge.svg)
![notification-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/notification-ci.yaml/badge.svg)
![order-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/order-ci.yaml/badge.svg)
![product-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/product-ci.yaml/badge.svg)
![profile-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/profile-ci.yaml/badge.svg)
![rating-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/rating-ci.yaml/badge.svg)
![shipping-service-ci](https://github.com/hiepthanhtran/microservices-scms/actions/workflows/shipping-ci.yaml/badge.svg)

## Tentative technologies and frameworks

- [x] Java 21
- [x] Spring boot 3.3.4
- [x] Next.js
- [ ] Flutter (Soon)
- [x] Kafka
- [ ] Keycloak (Soon)
- [ ] Elasticsearch (Soon)
- [ ] SonarCloud (Soon)
- [ ] OpenTelemetry (Soon)
- [ ] Grafana, Loki, Prometheus, Tempo (Soon)

## Development Architecture

![Development Architecture](https://raw.githubusercontent.com/hiepthanhtran/microservices-scms/main/images/architecture.png)

## Getting started with Docker Compose

1. Get the latest source code
2. Open terminal of your choice, go to project directory, run `./scms-up.sh`, wait for all the containers up and running

> *_Warning:_* To run all the containers, you need a minimum of 16GB of RAM.

```bash
./scms-up.sh
```

> *_Note:_* If you use Linux, you may need to run `chmod +x ./scms-up.sh` to make the script executable. Run `./scms-down.sh` to stop all the containers.

3. All the containers up and running then we start to get sample data for the services. Open terminal of your choice, go to project directory,
   run `./start-data.sh`, wait for all the services to get the sample data.

```bash
./start-data.sh
```

> *_Note:_* If you use Linux, you may need to run `chmod +x ./start-data.sh` to make the script executable.