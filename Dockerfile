FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
COPY checkstyle.xml .
RUN mvn -B -ntp clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN useradd -ms /bin/bash spring
USER spring
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
