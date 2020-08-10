cd configuration-service
run_build.sh
cd..
gradlew :configuration-service:docker --info --build-cache
cd configuration-service
run_docker.sh
