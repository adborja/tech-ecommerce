#!/bin/bash
docker run \
  --name=gateway.cedesistemas.local \
  --hostname gateway.cedesistemas.local \
  --net cedesistemas_network \
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
  -p 8080:8080 \
  -d registry.cedesistemas.internal:5000/commerce/gateway-service:dev \

