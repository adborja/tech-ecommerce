#!/bin/bash
mkdir -p target/dependency
unzip build/libs/gateway-service-0.0.1-SNAPSHOT.jar -d target/dependency/
docker build -t registry.cedesistemas.internal:5000/gateway/GatewayApp:dev .
