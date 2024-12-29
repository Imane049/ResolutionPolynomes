FROM openjdk:17-jdk-slim
VOLUME /tmp
EXPOSE 8086
ARG JAR_FILE=target/BairstowResolution-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
