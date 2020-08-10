FROM adoptopenjdk:11.0.6_10-jre-openj9-0.18.1
#VOLUME C:\temp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","co.edu.cedesistemas.commerce.ConfigurationServiceApp"]

EXPOSE 8760