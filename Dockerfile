# ==========================================
# Stage 1: Build the Java application
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Compile the application (skipping tests for faster deployment)
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Setup Python & Run the app
# ==========================================
FROM eclipse-temurin:17-jdk-jammy

# Install Python 3 and pip in the Linux container
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy ML assets and install exact Python dependencies
COPY ml_requirements/ ./ml_requirements/
RUN pip3 install --no-cache-dir -r ml_requirements/ml_dependencies.txt

# Copy the compiled Java application FROM the build stage above
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx300m", "-Xss256k", "-XX:+UseContainerSupport", "-jar", "app.jar"]