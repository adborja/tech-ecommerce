#!/bin/bash
docker run \
  --name=loyalty.cedesistemas.local \
  --hostname loyalty.cedesistemas.local \
  --net cedesistemas_network \
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
  -p 8085:8085 \
  -d registry.cedesistemas.internal:5000/commerce/loyalty-service:dev