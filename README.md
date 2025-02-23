# HarmonyChain | Microservice Architecture

## HarmonyChain is a supply chain management system designed to streamline and optimize the flow of goods and information. It promotes seamless collaboration between suppliers, manufacturers, and distributors, ensuring efficiency, reliability, and flexibility in managing supply chain operations. 

![HarmonyChain](https://raw.githubusercontent.com/hiepthanhtran/HarmonyChain/main/images/scms.png)

![api-gateway-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/api-gateway-ci.yaml/badge.svg)
![cart-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/cart-ci.yaml/badge.svg)
![file-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/file-ci.yaml/badge.svg)
![identity-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/identity-ci.yaml/badge.svg)
![inventory-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/inventory-ci.yaml/badge.svg)
![notification-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/notification-ci.yaml/badge.svg)
![order-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/order-ci.yaml/badge.svg)
![product-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/product-ci.yaml/badge.svg)
![profile-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/profile-ci.yaml/badge.svg)
![rating-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/rating-ci.yaml/badge.svg)
![shipment-service-ci](https://github.com/hiepthanhtran/HarmonyChain/actions/workflows/shipment-ci.yaml/badge.svg)

### Tentative technologies and frameworks

- [x] Java 21
- [x] Spring boot 3.3.4
- [x] Next.js <!-- - [ ] Flutter (Soon) -->
- [x] Kafka
- [ ] Keycloak (Soon)
- [ ] Elasticsearch (Soon)
- [ ] SonarCloud (Soon)
- [ ] OpenTelemetry (Soon)
- [ ] Grafana, Loki, Prometheus, Tempo (Soon)

### Development Architecture

![Development Architecture](https://raw.githubusercontent.com/hiepthanhtran/HarmonyChain/main/images/architecture.png)

### Getting started with Docker Compose

1. Get the latest source code
2. Add the following records to your host file:

```
127.0.0.1 api.harmony.local
127.0.0.1 identity
127.0.0.1 storefront
127.0.0.1 backoffice
127.0.0.1 phpmyadmin.scms.local
```

3. Open terminal of your choice, go to project directory, run `./start-harmony.sh`, wait for all the containers up and running

```bash
./start-harmony.sh
```

> *_Warning:_* To run all the containers, you need a minimum of 16GB of RAM.

> *_Note:_* If you use Linux, you may need to run `chmod +x ./start-harmony.sh` to make the script executable. Run `./stop-harmony.sh` to stop all the
> containers.

4. All the containers up and running then we start to get sample data for the services. Open terminal of your choice, go to project directory,
   run `./start-data.sh`, wait for all the services to get the sample data.

```bash
./start-data.sh
```

> *_Note:_* If you use Linux, you may need to run `chmod +x ./start-data.sh` to make the script executable.

##### About docker-compose files

1. docker-compose.yml for all core services
2. docker-compose.services.yml exclude application services

[//]: # (2. docker-compose.services.yml for search service)

[//]: # (3. docker-compose.o11y.yml for observability services)

### Contributing

- Give us a star
- Reporting a bug
- Participate discussions
- Propose new features
- Submit pull requests. If you are new to GitHub, consider
  to [learn how to contribute to a project through forking](https://docs.github.com/en/get-started/quickstart/contributing-to-projects)

By contributing, you agree that your contributions will be licensed under MIT License.
