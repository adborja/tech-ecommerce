#!/bin/bash
docker run --name=config.cedesistemas.local --hostname config.cedesistemas.local --net cedesistemas_network -v /C/users/laura/.ssh:/root/.ssh/ -p 8760:8760 -d registry.cedesistemas.internal:5000/commerce/configuration-service:dev