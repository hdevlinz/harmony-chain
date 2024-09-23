# Identity service

This microservice is responsible for:

* Onboarding users
* Roles and permissions
* Authentication

## Tech stack

* Build tool: maven >= 3.9.5
* Java: 21
* Framework: Spring boot 3.3.x
* DBMS: MySQL

## Prerequisites

* Java SDK 21
* A MySQL server

## Start application

```bash
mvn spring-boot:run
```

## Build application

```bash
mvn clean package
```

## Docker guideline

### Build docker image

```bash
docker build -t <account>/harmony-identity-service:0.0.1 .
```

### Push docker image to Docker Hub

```bash
docker image push <account>/harmony-identity-service:0.0.1
```

### Create network:

```bash
docker network create harmony-network
```

### Start MySQL in harmony-network

```bash
docker run --network harmony-network --name harmony-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.39-debian
```

### Run your application in harmony-network

```bash
docker run --name <account>/harmony-identity-service:0.0.1 --network harmony-network -p 8080:8080 -e DBMS_URL=jdbc:mysql://harmony-mysql:3306/identity_service <account>/harmony-identity-service:0.0.1
```
