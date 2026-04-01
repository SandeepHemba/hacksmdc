# Use Java 17
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Expose port (Render will override with PORT env)
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/*.jar"]
