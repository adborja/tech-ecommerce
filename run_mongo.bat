#!/bin/bash
docker run --restart=unless-stopped -v /C/cedesistemas/data/mongo:/data/db --hostname mongodb.cedesistemas.local --net cedesistemas_network --name mongodb.cedesistemas.local --memory=512m -e "TZ=America/Bogota" -p 27017:27017 -d mongo:4.2.1
