#
# Build stage
#
FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:21-jdk-slim
COPY --from=build /target/piggame-0.0.1-SNAPSHOT.jar piggame_t1.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","piggame_t1.jar"]