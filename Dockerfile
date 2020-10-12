FROM openjdk:8-jre-alpine

COPY /radio-service-api/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]