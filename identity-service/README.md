# User service

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

## Install Docker on ubuntu

# Add Docker's official GPG key:

```bash
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
```

# Add the repository to Apt sources:

```bash
echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
$(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo docker run hello-world
```

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
