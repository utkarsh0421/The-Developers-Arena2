# Multi-stage Dockerfile for erp-api
FROM maven:3.9.3-eclipse-temurin-17 as builder
WORKDIR /workspace
COPY pom.xml mvnw ./
COPY .mvn .mvn/
COPY src ./src/
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=local
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
