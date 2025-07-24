# === Build Stage ===
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# === Run Stage ===
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy final JAR from builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
