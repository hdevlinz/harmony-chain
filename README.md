# Harmony Supply Chain | Microservice Architecture

![SCMS](https://raw.githubusercontent.com/hiepthanhtran/harmony-supply-chain/main/images/scms.png)

## Tentative technologies and frameworks

- Java 21
- Spring boot 3.3.4
- Next.js
- Flutter (Soon)
- Kafka
- Keycloak (Soon)
- Elasticsearch (Soon)
- SonarCloud (Soon)
- OpenTelemetry (Soon)
- Grafana, Loki, Prometheus, Tempo (Soon)

## Development Architecture

![Development Architecture](https://raw.githubusercontent.com/hiepthanhtran/harmony-supply-chain/main/images/architecture.png)

## Getting started with Docker Compose

1. Get the latest source code
2. Open terminal of your choice, go to `hscm` directory, run `docker compose up`, wait for all the containers up and running

```bash
docker compose up -d
```

> *_Warning:_* To run all the containers, you need a minimum of 16GB of RAM. Otherwise, you can only run the core services with this
command `docker compose -f docker-compose.yml up`
