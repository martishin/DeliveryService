# Stage 1: Build
FROM gradle:7.6.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

# Stage 2: Run
FROM openjdk:17-jdk
COPY --from=build /home/gradle/src/build/libs/delivery-simulation-1.0.0.jar /delivery-simulation-1.0.0.jar
ENTRYPOINT ["java","-jar","/delivery-simulation-1.0.0.jar"]
