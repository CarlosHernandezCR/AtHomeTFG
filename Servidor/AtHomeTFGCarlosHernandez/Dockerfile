FROM maven:3.9-sapmachine-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests




FROM openjdk:21-jdk-oracle

COPY --from=build /app/target/athometfg_docker.jar /athometfg_docker.jar
ENTRYPOINT ["java","-jar","/athometfg_docker.jar"]