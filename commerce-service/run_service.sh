#!/bin/bash
docker run --restart=unless-stopped \
        --log-opt max-size=10m \
        --log-opt max-file=5 \
        --hostname commerce.cedesistemas.local \
        --name commerce.cedesistemas.local \
        --net cedesistemas_network \
        --memory=512m \
        -e "SPRING_PROFILES_ACTIVE=sandbox" \
        -e "TZ=America/Bogota" \
        -p 8081:8081 \
        -d registry.cedesistemas.internal:5000/commerce/commerce-service:dev
