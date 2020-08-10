cd discovery-service
run_build.sh
cd..
gradlew :discovery-service:docker --info --build-cache
cd discovery-service
run_docker.sh
