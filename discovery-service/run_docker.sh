#!/bin/bash
docker run \
  --name=discovery.cedesistemas.local \
  --hostname discovery.cedesistemas.local \
  --net cedesistemas_network \
  -p 8761:8761 \
  -d registry.cedesistemas.internal:5000/commerce/discovery-service:dev

