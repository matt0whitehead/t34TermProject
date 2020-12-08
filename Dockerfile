# BUILDER IMAGE
FROM maven:3.6.3-ibmjava-8-alpine AS builder
COPY ./ ./
RUN mvn package

# WEBSERVER IMAGE
FROM openjdk:8-jdk-alpine
# copy just executable
COPY --from=builder ./target/t34TermProject-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]