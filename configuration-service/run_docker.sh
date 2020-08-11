#!/bin/bash
docker run \
  --name=discovery.cedesistemas.local \
  --hostname discovery.cedesistemas.local \
  --net cedesistemas_network \
  -e "SPRING_PROFILES_ACTIVE=dev" \
  -v C:/Users/Laura\.ssh:/root/.ssh/ \
  -p 8760:8760 \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery.cedesistemas.local:8761/eureka" \
  -d registry.cedesistemas.internal:5000/commerce/configuration-service:dev