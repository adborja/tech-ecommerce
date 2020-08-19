#!/bin/bash
docker run \
  --name=config.cedesistemas.local \
  --hostname config.cedesistemas.local \
  --net cedesistemas_network \
  -v c:/Users/stelo/.ssh/:/root/.ssh/ \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery.cedesistemas.local:8761/eureka" \
  -e "SPRING_PROFILES_ACTIVE=dev" \
  -p 8760:8760 \
  -d registry.cedesistemas.internal:5000/commerce/configuration-service:dev


