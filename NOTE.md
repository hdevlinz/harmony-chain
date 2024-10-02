[//]: # (1 cart -> 2 profile -> 3 identity -> 4 product -> 5 rating -> 6 inventory -> 7 order -> 8 shipping)

## Docker Guideline

### Add Docker's official GPG key:

```bash
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
```

### Add the repository to Apt sources:

```bash
echo \
"deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
$(. /etc/os-release && echo "$VERSION_CODENAME") stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo docker run hello-world
```

### Create network:

```bash
docker network create hscm-network
```

## Install Keycloak from Quay.io

```bash
docker pull quay.io/keycloak/keycloak:25.0.0
```

```bash
docker run -d --name keycloak-25.0.0 -p 8180:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.0 start-dev
```

## Install Kafka from Docker Hub

```bash
docker pull bitnami/kafka:3.7.0
```

```bash
docker run -d --name kafka-3.7.0 -p 9094:9094 bitnami/kafka:3.7.0
```

## Install MySQL from Docker Hub

```bash
docker pull mysql:8.0.39-debian
```

```bash
docker run --name mysql-8.0.39-debian -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.39-debian
```

## Install Mongodb from Docker Hub

```bash
docker pull bitnami/mongodb:7.0.11
```

```bash
docker run -d --name mongodb-7.0.11 -p 27017:27017 -e MONGODB_ROOT_USER=root -e MONGODB_ROOT_PASSWORD=root bitnami/mongodb:7.0.11
```

## Install Neo4j from Docker Hub

```bash
docker pull neo4j:latest
```

```bash
docker run --name neo4j-latest --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/admin@123' neo4j:latest
```
