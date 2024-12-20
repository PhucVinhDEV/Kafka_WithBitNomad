# Chọn image base với OpenJDK và Maven để build ứng dụng
FROM maven:latest AS builder
WORKDIR /app

# Copy pom.xml riêng để tận dụng cache của Docker
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copy source code
COPY src /app/src/

# Build project
RUN mvn clean package

# Sử dụng image base OpenJDK cho giai đoạn chạy ứng dụng
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ giai đoạn builder vào container
COPY --from=builder /app/target/NotificationService-0.0.1-SNAPSHOT.jar /app/notification-service.jar

# Expose port 8081
EXPOSE 8081

# Lệnh chạy ứng dụng khi container khởi động
CMD ["java", "-jar", "/app/notification-service.jar"]
