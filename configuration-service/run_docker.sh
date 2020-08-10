#!/bin/bash
docker run \
  --name=configuration.cedesistemas.local \
  --hostname configuration.cedesistemas.local \
  --net cedesistemas_network \
  -v /Users/gabrieljaimegaleano/.ssh/:/root/.ssh/ \
  -e "eureka.client.serviceUrl.defaultZone=http://discovery.cedesistemas.local:8761/eureka" \
  -p 8760:8760 \
  -d registry.cedesistemas.internal:5000/commerce/configuration-service:dev
