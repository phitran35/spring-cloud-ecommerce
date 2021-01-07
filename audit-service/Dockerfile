#### Stage 1: Build the application
FROM amazoncorretto:11-alpine-jdk

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
COPY target/*.jar app.jar

#### Stage 2: A minimal docker image with command to run the app
#FROM openjdk:8-jre-alpine

ENTRYPOINT ["java","-jar", "app.jar"]
EXPOSE 8200