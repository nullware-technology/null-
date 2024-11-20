# Stage 1: Build
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory
WORKDIR /app

# Cache Maven dependencies (first, copy only the pom.xml)
COPY pom.xml .

# Download dependencies to avoid reprocessing during rebuilds
RUN mvn dependency:go-offline -Dmaven.repo.local=/tmp/repo

# Copy the source code into the container
COPY src ./src

# Compile the application and clean up unnecessary caches
RUN mvn clean package -DskipTests -Dmaven.repo.local=/tmp/repo && \
    rm -rf /tmp/repo && \
    rm -rf /root/.m2 && \
    find /app/target -type f -name "*.original" -delete

# Stage 2: Runtime
FROM gcr.io/distroless/java17-debian11:nonroot

# Set the working directory
WORKDIR /app

# Copy the JAR file generated in the build stage
COPY --from=build /app/target/*.jar ./app.jar

# Expose the port configured by the environment
EXPOSE ${CONTAINER_SERVER_PORT}

# Command to run the application
CMD ["app.jar"]
