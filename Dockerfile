FROM openjdk:8-jdk-alpine

COPY target/phonevalidator-0.0.1.jar /phonevalidator-0.0.1.jar
COPY sample.db /sample.db

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/phonevalidator-0.0.1.jar"]