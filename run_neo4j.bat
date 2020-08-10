docker run --restart=unless-stopped -v /C/cedesistemas/data/neo4j:/data -v /C/cedesistemas/data/neo4j/logs:/var/lib/neo4j/logs -v /C/cedesistemas/data/neo4j/data:/var/lib/neo4j/data -v /C/cedesistemas/data/neo4j/conf:/var/lib/neo4j/conf -v /C/cedesistemas/data/neo4j/plugins:/var/lib/neo4j/plugins --hostname neo4j.cedesistemas.local --net cedesistemas_network --name neo4j.cedesistemas.local --memory=512m -e "TZ=America/Bogota" -e "NEO4J_AUTH=none" -p 7474:7474 -p 7687:7687 -d neo4j:4.0.3



