#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /usr/app/src
COPY pom.xml /usr/app
RUN mvn -f /usr/app/pom.xml clean package -DskipTests
RUN ls

#
# Package stage
#
FROM openjdk:8u131-jdk-alpine
RUN mkdir /usr/app
COPY --from=build /usr/app/target/receipt-0.0.1-SNAPSHOT.jar /usr/app
RUN ls
WORKDIR /usr/app
CMD ["java", "-jar", "receipt-0.0.1-SNAPSHOT.jar"]

### Deprecated ###
# FROM openjdk:8u131-jdk-alpine
# RUN mkdir /usr/app
# COPY ./target/receipt-0.0.1-SNAPSHOT.jar /usr/app
# WORKDIR /usr/app
# CMD ["java", "-jar", "receipt-0.0.1-SNAPSHOT.jar"]
