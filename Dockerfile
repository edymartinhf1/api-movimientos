FROM openjdk:11.0.16
WORKDIR /app
COPY ./target/api-movimientos-0.0.1-SNAPSHOT.jar .
EXPOSE 8087
ENTRYPOINT ["java","-jar","api-movimientos-0.0.1-SNAPSHOT.jar"]


