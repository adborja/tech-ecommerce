#!/bin/bash
docker run --restart=unless-stopped \
        -v /Users/lina/Documents/David/cedesistemas/var/data/neo4j:/data \
        -v /Users/lina/Documents/David/cedesistemas/var/data/neo4j/logs:/var/lib/neo4j/logs \
        -v /Users/lina/Documents/David/cedesistemas/var/data/neo4j/data:/var/lib/neo4j/data \
        -v /Users/lina/Documents/David/cedesistemas/var/data/neo4j/conf:/var/lib/neo4j/conf \
        -v /Users/lina/Documents/David/cedesistemas/var/data/neo4j/plugins:/var/lib/neo4j/plugins \
        --hostname neo4j.cedesistemas.local \
        --name neo4j.cedesistemas.local \
        --memory=1024m \
        -e "TZ=America/Bogota" \
        -e "NEO4J_AUTH=none" \
        -p 7474:7474 \
        -p 7687:7687 \
        -d neo4j:4.0.3
