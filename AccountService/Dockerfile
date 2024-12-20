# Build stage
FROM maven:latest AS builder
WORKDIR /app

# Copy pom.xml riêng để tận dụng cache của Docker
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copy source code
COPY src /app/src/

# Build project
RUN mvn clean package

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy JAR từ build stage
COPY --from=builder /app/target/AccountService-0.0.1-SNAPSHOT.jar /app/AccountService-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/AccountService-0.0.1-SNAPSHOT.jar"]