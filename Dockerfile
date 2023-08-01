FROM openjdk:17-jdk-alpine
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/api-movimientos-0.0.1-SNAPSHOT.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","api-movimientos-0.0.1-SNAPSHOT.jar"]


