FROM openjdk:11-jdk-slim
ENV JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8
VOLUME /tmp
RUN mkdir -p /root/.ssh/
ADD build/libs/*.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

EXPOSE 8760