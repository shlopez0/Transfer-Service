FROM eclipse-temurin:21-jdk

COPY applications/app-service/build/libs/transfer-service.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]