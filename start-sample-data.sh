#!/bin/bash

# Kiểm tra Kafka container đã sẵn sàng chưa (nếu cần)
KAFKA_CONTAINER_NAME="hscm-kafka"
SERVICE_CONTAINER_NAME="hscm-profile"  # Container của service nhận thông điệp

# Đợi Kafka container sẵn sàng
echo "Checking if Kafka container '${KAFKA_CONTAINER_NAME}' is running..."
docker inspect -f '{{.State.Running}}' ${KAFKA_CONTAINER_NAME} 2>/dev/null | grep "true" > /dev/null

if [ $? -eq 0 ]; then
  echo "Kafka container '${KAFKA_CONTAINER_NAME}' is running."
else
  echo "Kafka container '${KAFKA_CONTAINER_NAME}' is not running. Exiting..."
  exit 1
fi

# Kiểm tra service container đã chạy chưa
echo "Checking if service container '${SERVICE_CONTAINER_NAME}' is running..."
docker inspect -f '{{.State.Running}}' ${SERVICE_CONTAINER_NAME} 2>/dev/null | grep "true" > /dev/null

if [ $? -eq 0 ]; then
  echo "Service container '${SERVICE_CONTAINER_NAME}' is running."
else
  echo "Service container '${SERVICE_CONTAINER_NAME}' is not running. Exiting..."
  exit 1
fi

# Gửi thông điệp Kafka
KAFKA_BROKER="localhost:9094"  # Địa chỉ Kafka broker, bạn cần thay đổi nếu khác
TOPIC="sample-data"

# Tạo thông điệp JSON để gửi
MESSAGE='{"chanel": "SAMPLE_DATA", "recipient": "PROFILE_SERVICE"}'

# Gửi thông điệp đến topic với Kafka producer
echo "Sending message to Kafka topic '${TOPIC}'..."
echo "${MESSAGE}" | docker exec -i ${KAFKA_CONTAINER_NAME} bash -c "kafka-console-producer.sh --broker-list ${KAFKA_BROKER} --topic ${TOPIC}"

echo "Message sent to Kafka topic '${TOPIC}' successfully."