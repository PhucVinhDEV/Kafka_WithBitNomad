# Chọn image base cho Maven và OpenJDK để build ứng dụng
FROM maven:latest AS builder
WORKDIR /app

# Copy pom.xml riêng để tận dụng cache của Docker
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copy source code
COPY src /app/src/

# Build project
RUN mvn clean package

# Chọn image base cho OpenJDK để chạy ứng dụng
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc trong container
WORKDIR /app

# Copy file JAR đã build từ giai đoạn builder vào container
COPY --from=builder /app/target/statictisService-0.0.1-SNAPSHOT.jar /app/statictisService-0.0.1-SNAPSHOT.jar

# Expose port 8090
EXPOSE 8090

# Lệnh chạy ứng dụng khi container khởi động
CMD ["java", "-jar", "/app/statictisService-0.0.1-SNAPSHOT.jar"]
