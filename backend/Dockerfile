FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY target/backend.jar /opt/backend/
WORKDIR /opt/backend/

ENTRYPOINT java -jar backend.jar
