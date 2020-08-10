cd gateway-service
run_build.sh
cd..
gradlew :gateway-service:docker --info --build-cache
cd gateway-service
run_docker.sh
