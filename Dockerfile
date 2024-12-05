# Use an official OpenJDK runtime as a parent image
#FROM openjdk:17-jdk-alpine
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /discount-service

# Copy the JAR file into the container
COPY target/discount-service-0.0.1-SNAPSHOT.jar /discount-service/discount-service-0.0.1-SNAPSHOT.jar

# Specify the command to run the JAR file
ENTRYPOINT ["java", "-jar", "/discount-service/discount-service-0.0.1-SNAPSHOT.jar"]