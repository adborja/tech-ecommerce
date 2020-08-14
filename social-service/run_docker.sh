#!/bin/bash
docker run \
  --name=social.cedesistemas.local \
  --hostname social.cedesistemas.local \
  --net cedesistemas_network \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery.cedesistemas.local:8761/eureka" \
  --add-host "neo4j.cedesistemas.local:192.168.0.131" \
  -p 8082:8082 \
  -d registry.cedesistemas.internal:5000/commerce/social-service:dev