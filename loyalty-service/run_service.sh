#!/bin/bash
docker run --restart=unless-stopped \
        --log-opt max-size=10m \
        --log-opt max-file=5 \
        --hostname loyalty.cedesistemas.local \
        --name loyalty.cedesistemas.local \
        --net cedesistemas_network \
        --memory=512m \
        -e "SPRING_PROFILES_ACTIVE=dev" \
        -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
        -e "TZ=America/Bogota" \
        -p 8085:8085 \
        -d registry.cedesistemas.internal:5000/commerce/loyalty-service:dev
