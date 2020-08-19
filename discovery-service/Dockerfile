FROM openjdk:11-jdk-slim
VOLUME /tmp

RUN mkdir -p /root/.ssh/

ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","co.edu.cedesistemas.commerce.discovery.DiscoveryServiceApp"]

EXPOSE 8761