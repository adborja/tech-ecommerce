#!/bin/bash
mkdir -p target/dependency
unzip build/libs/*.jar -d target/dependency/
docker build -t registry.cedesistemas.internal:5000/commerce/social-service:dev .