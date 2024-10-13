#!/bin/bash

WORKFLOW_TEMPLATE=$(cat .github/workflow-template.yaml)
SERVICES=(cart file identity inventory notification order product profile rating shipment) # Add your services here

for SERVICE_NAME in "${SERVICES[@]}"; do
    echo "Generating workflow for ${SERVICE_NAME} service"

    WORKFLOW="${WORKFLOW_TEMPLATE//\{\{SERVICE_NAME\}\}/${SERVICE_NAME}}"
    echo "${WORKFLOW}" > ".github/workflows/${SERVICE_NAME}-ci.yaml"
done

WORKFLOW_TEMPLATE2=$(cat .github/workflow-template2.yaml)
SERVICES2=(api-gateway) # Add your services here

for SERVICE_NAME2 in "${SERVICES2[@]}"; do
    echo "Generating workflow for ${SERVICE_NAME2} service"

    WORKFLOW2="${WORKFLOW_TEMPLATE2//\{\{SERVICE_NAME\}\}/${SERVICE_NAME2}}"
    echo "${WORKFLOW2}" > ".github/workflows/${SERVICE_NAME2}-ci.yaml"
done