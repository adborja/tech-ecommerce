#!/bin/bash
docker run --restart=unless-stopped -v /C/cedesistemas/data/rabbitmq:/home/client --hostname rabbitmq.cedesistemas.local --net cedesistemas_network --name rabbitmq.cedesistemas.local --memory=256m -e "TZ=America/Bogota" -p 5672:5672 -p 15672:15672 -d rabbitmq:3.7-management
