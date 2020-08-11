#!/bin/bash
mkdir -p target/dependency
unzip build/libs/configuration-service-0.0.1-SNAPSHOT.jar -d target/dependency/
docker build -t registry.cedesistemas.internal:5000/commerce/configuration-service:dev .
