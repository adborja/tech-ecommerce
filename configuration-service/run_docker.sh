#!/bin/bash
docker run \
  --name=configuration-service.cedesistemas.local \
  --hostname configuration-service.cedesistemas.local \
  --net cedesistemas_network \
  -e "SPRING_PROFILES_ACTIVE=sandbox" \
  -p 8760:8760 \
  -d registry.cedesistemas.internal:5000/commerce/configuration-service:dev



