FROM openjdk:8-jdk-alpine
VOLUME /tmp

COPY target/migration.jar /opt/migration/
WORKDIR /opt/migration/

ENTRYPOINT java -jar migration.jar
