FROM openjdk:11-jdk-slim
VOLUME /tmp
RUN apt-get update && apt-get install -y curl
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","co.edu.cedesistemas.commerce.payment.PaymentApp"]

EXPOSE 8084