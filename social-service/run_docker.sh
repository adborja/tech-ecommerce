#!/bin/bash
docker run \
  --name=social.cedesistemas.local \
  --hostname social.cedesistemas.local \
  --net cedesistemas_network \
  -e "SPRING_PROFILES_ACTIVE=sandbox" \
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
  -p 8086:8086 \
  -d registry.cedesistemas.internal:5000/commerce/shipping-service:dev
