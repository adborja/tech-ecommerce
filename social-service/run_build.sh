#!/bin/bash
mkdir -p target/dependency
unzip build/libs/social-service-0.0.1-SNAPSHOT.jar -d target/dependency
docker build -t registry.cedesistemas.internat:5000/commerce/social-service:dev .