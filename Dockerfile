FROM maven:3.9.12-eclipse-temurin-21-alpine AS BUILD

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/target/fintrack-0.0.1-SNAPSHOT.jar fintrack.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fintrack.jar"]