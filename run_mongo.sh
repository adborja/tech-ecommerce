#!/bin/bash
docker run --restart=unless-stopped \
        -v /Users/lina/Documents/David/cedesistemas/var/data/mongodb:/data/db \
        --hostname mongodb.cedesistemas.local \
        --name mongodb.cedesistemas.local \
        --memory=512m \
        -e "TZ=America/Bogota" \
        -p 27017:27017 \
        -d mongo:4.2.1
