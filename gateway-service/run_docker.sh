#!/bin/bash
docker run \
  --name=gateway.cedesistemas.local \
  --hostname gateway.cedesistemas.local \
  --net cedesistemas_network \
  -e "SPRING_PROFILES_ACTIVE=sandbox" \
  -p 8080:8080 \
  -d registry.cedesistemas.internal:5000/commerce/gateway-service:dev



