# Use Maven image to build the app
FROM maven:3.8.6-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy pom and download dependencies (for cache efficiency)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Now use a smaller image to run the app
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy built jar from previous image
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
