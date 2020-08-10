#!/bin/bash
docker run \
  --name=gateway.cedesistemas.local \
  --hostname gateway.cedesistemas.local \
  --net cedesistemas_network \
  -p 8080:8080 \
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
  -d registry.cedesistemas.internal:5000/commerce/gateway-service:dev


