#!/bin/bash
docker run \
  --name=config.cedesistemas.local \
  --hostname config.cedesistemas.local \
  --net cedesistemas_network \
  -v /Users/Beto/.ssh/:/root/.ssh/ \
  -e "SPRING_PROFILES_ACTIVE=dev" \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery.cedesistemas.local:8761/eureka" \
  -p 8760:8760 \
  -d registry.cedesistemas.internal:5000/commerce/configuration-service:dev


