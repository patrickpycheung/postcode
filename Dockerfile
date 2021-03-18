# <Build stage>

# Prepare the base container image for building the jar file from the source (Linux OS + Maven 3.6.3)
FROM maven:3.6.3-jdk-11 AS build

# Specify the directory to hold the source in the container, and cd to there
WORKDIR /opt/source/suburb-and-postcode

# Copy local source to source folder in container
COPY src src

# Copy pom.xml to 
COPY pom.xml .

# Compile the jar file on the container
RUN mvn -f pom.xml clean package


# <Package stage>

# Prepare the base container image for running the application (Linux OS + Java 11 runtime)
FROM adoptopenjdk/openjdk11:alpine-jre

# Specify the directory to hold the jar in the container, and cd to there
WORKDIR /opt/app

#Copy the built jar to the application folder on the container
COPY --from=build /opt/source/suburb-and-postcode/target/suburb-and-postcode-1.0.0.jar app.jar

# Run application in container
ENTRYPOINT ["java","-jar","app.jar"]