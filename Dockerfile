# ---- Build Stage ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and POM first for dependency caching
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:resolve -q

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests -q

# ---- Runtime Stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE ${PORT:-3000}

ENTRYPOINT ["java", "-jar", "app.jar"]
