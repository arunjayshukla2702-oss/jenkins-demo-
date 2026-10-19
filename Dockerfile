FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/demo-app-1.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
