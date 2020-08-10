#!/bin/bash
docker run \
  --name=social-service.cedesistemas.local \
  --hostname social-service.cedesistemas.local \
  --net cedesistemas_network \
  -e "SPRING_PROFILES_ACTIVE=sandbox" \
  -p 8082:8082 \
  -d registry.cedesistemas.internal:5000/commerce/social/social-service:dev



