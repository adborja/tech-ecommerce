#!/bin/bash
docker run \
  --name=eureka.cedesistemas.local \
  --hostname eureka.cedesistemas.local \
  --net cedesistemas_network \
  -p 8761:8761 \
  -d registry.cedesistemas.internal:5000/commerce/discovery-service:dev