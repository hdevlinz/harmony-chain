# Harmony Supply Chain System | Microservice Architecture

![SCMS](https://raw.githubusercontent.com/hiepthanhtran/scms/main/images/scms.png)

### SCMS is a pet project aim to practice building a typical microservice application in Java with Spring Boot, Next.js, and Flutter.

![api-gateway-ci](https://github.com/hiepthanhtran/scms/actions/workflows/api-gateway-ci.yaml/badge.svg)
![cart-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/cart-service-ci.yaml/badge.svg)
![file-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/file-service-ci.yaml/badge.svg)
![identity-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/identity-service-ci.yaml/badge.svg)
![inventory-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/inventory-service-ci.yaml/badge.svg)
![notification-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/notification-service-ci.yaml/badge.svg)
![order-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/order-service-ci.yaml/badge.svg)
![product-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/product-service-ci.yaml/badge.svg)
![profile-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/profile-service-ci.yaml/badge.svg)
![rating-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/rating-service-ci.yaml/badge.svg)
![shipping-service-ci](https://github.com/hiepthanhtran/scms/actions/workflows/shipping-service-ci.yaml/badge.svg)

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

![Development Architecture](https://raw.githubusercontent.com/hiepthanhtran/harmony-supply-chain/main/images/architecture.png)

## Getting started with Docker Compose

1. Get the latest source code
2. Open terminal of your choice, go to `scms` directory, run `./start-scms.sh`, wait for all the containers up and running

> *_Warning:_* To run all the containers, you need a minimum of 16GB of RAM.

```bash
./start-scms.sh
```

> *_Note:_* If you use Linux, you may need to run `chmod +x ./start-scms.sh` to make the script executable. Run `./stop-scms.sh` to stop all the containers.

3. All the containers up and running then we start to get sample data for the services. Open terminal of your choice, go to `scms` directory,
   run `./start-data.sh`

```bash
./start-data.sh
```

> *_Note:_* If you use Linux, you may need to run `chmod +x ./start-data.sh` to make the script executable.