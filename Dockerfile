FROM eclipse-temurin:17-jdk-jammy

# Install Python 3 and pip in the Linux container
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy ML assets and install exact Python dependencies
COPY ml_requirements/ ./ml_requirements/
RUN pip3 install --no-cache-dir -r ml_requirements/ml_dependencies.txt

# Copy the compiled Java application
COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]