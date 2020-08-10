FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY build/libs/social-service-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8082