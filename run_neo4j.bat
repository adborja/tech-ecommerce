docker run --restart=unless-stopped --hostname neo4j.cedesistemas.local --name neo4j.cedesistemas.local --memory=512m -e "TZ=America/Bogota" -e "NEO4J_AUTH=none" -p 7474:7474 -p 7687:7687 -d -d neo4j:4.0.3



