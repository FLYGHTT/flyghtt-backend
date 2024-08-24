FROM openjdk:21-jdk

COPY build/libs/flyghtt-backend-0.0.1-SNAPSHOT.jar  flyghtt-backend-0.0.1-SNAPSHOT.jar

ENV DB_SCHEMA=flyghtt_db

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/flyghtt-backend-0.0.1-SNAPSHOT.jar"]
