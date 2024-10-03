#!/bin/bash

KAFKA_CONTAINER_NAME="hscm-kafka"
KAFKA_URL="PLAINTEXT://kafka:9094"
TOPIC="sample-data"

MESSAGE='{"chanel": "SAMPLE_DATA", "recipient": "PROFILE_SERVICE"}'

echo "${MESSAGE}" | docker exec -i ${KAFKA_CONTAINER_NAME} kafka-console-producer.sh --broker-list ${KAFKA_URL} --topic ${TOPIC} > /dev/null

echo "NotificationEvent message sent from Docker container '${KAFKA_CONTAINER_NAME}' to Kafka topic '${TOPIC}'."