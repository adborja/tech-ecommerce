#!/bin/bash
docker run \
  --name=social.cedesistemas.local \
  --hostname social.cedesistemas.local \
  --net cedesistemas_network \
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
  -p 8082:8082 \
  -d registry.cedesistemas.internal:5000/commerce/social-service:dev