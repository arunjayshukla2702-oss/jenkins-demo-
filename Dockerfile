FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/demo-app-9.9.jar app.jar
CMD ["java", "-jar", "app.jar"]
