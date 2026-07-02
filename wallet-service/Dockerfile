# Stage 1: Build
FROM eclipse-temurin:26-jdk-jammy AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:26-jre-jammy
WORKDIR /app

# Create a non-root user and group for security
RUN addgroup --system appgroup && adduser --system --group appuser
USER appuser:appgroup

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
