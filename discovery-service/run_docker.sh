#!/bin/bash
docker run \
  --name=discovery.cedesistemas.local \
  --hostname discovery.cedesistemas.local \
  --net cedesistemas_network \
  -p 8761:8761 \
  -e "eureka.client.register-with-eureka=false" \
  -e "eureka.client.fetch-registry=false" \
  -d registry.cedesistemas.internal:5000/commerce/discovery-service:dev
