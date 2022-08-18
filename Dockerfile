FROM openjdk:8-alpine
LABEL org.opencontainers.image.authors="clauber_camilo@live.com"

ADD target/notes-api-0.0.1-SNAPSHOT-standalone.jar /notes-api/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/notes-api/app.jar"]
