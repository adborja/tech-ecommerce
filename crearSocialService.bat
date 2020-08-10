cd social-service
run_build.sh
cd..
gradlew :social-service:docker --info --build-cache
cd social-service
run_docker.sh
