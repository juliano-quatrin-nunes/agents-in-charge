FROM maven:3.9.9-eclipse-temurin-23

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download Maven dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Create a smaller image for the runtime environment
FROM eclipse-temurin:23-jre

# Set the same working directory
WORKDIR /app

# Copy the built artifact from the build stage - specifically the jar-with-dependencies
COPY --from=0 /app/target/*-jar-with-dependencies.jar ./app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]