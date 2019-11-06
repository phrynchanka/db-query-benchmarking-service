FROM openjdk:8-jre-slim
WORKDIR /app
COPY target/db-query-benchmarking-service.jar db-query-benchmarking-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/db-query-benchmarking-service.jar"]
