# Use OpenJDK 17 slim base image
FROM eclipse-temurin:17-jre-jammy

#Maintainer info
LABEL maintainer="robot-worlds-team@wethinkcode.co.za"

#Set working dir
WORKDIR /srv

# Copy compiled JAR into an image
COPY target/robot-world-0.0.2-jar-with-dependencies.jar /srv/robot-worlds-server.jar

#Expose server port
EXPOSE 5050

#Run the server
CMD ["java", "-jar", "/srv/robot-worlds-server.jar"]
