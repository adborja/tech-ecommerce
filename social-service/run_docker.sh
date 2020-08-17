#!/bin/bash
docker run \
  --name=social.cedesistemas.local \
  --hostname social.cedesistemas.local \
  --net cedesistemas_network \
  -p 8082:8082 \
  -e "eureka.client.serviceUrl.defaultZone=http://eureka.cedesistemas.local:8761/eureka" \
  --add-host "neo4j.cedesistemas.local:192.168.1.57" \
  -d registry.cedesistemas.internal:5000/commerce/social-service:dev