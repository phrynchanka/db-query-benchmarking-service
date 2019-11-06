#!/usr/bin/env bash

#Build application.
echo "Building app"
docker-compose down
docker volume rm db-query-benchmarking-service_postgres-remote-1-data
docker volume rm db-query-benchmarking-service_postgres-remote-2-data
docker volume rm db-query-benchmarking-service_postgres-querydb-data
docker volume rm db-query-benchmarking-service_mysql-remote-1-data
mvn clean package -DskipTests

#Run services
echo "Starting services"
docker-compose up --build -d

echo "Waiting for app staring"
until curl localhost:8081/actuator/health; do
  echo "App is unavailable - sleeping"
  sleep 5
done

#Post queries for processing
echo "Post query processing"

echo "[
            {
            "metrics": ["EXECUTION_TIME"],
            "queryName": "select_all_posts",
            "sql": "SELECT * from perf",
            "version" : "v1" ,
            "databases" : ["mysql-remote-1","postgres-remote-1","postgres-remote-2"]
            },
            {
            "metrics": ["EXECUTION_TIME"],
            "queryName": "select_all_posts",
            "sql": "SELECT author2 from perf",
            "version" : "v2" ,
            "databases" : ["mysql-remote-1","postgres-remote-1","postgres-remote-2"]
            },
            {
            "metrics": ["EXECUTION_TIME"],
            "queryName": "select_all_posts",
            "sql": "SELECT content from perf",
            "version" : "v3" ,
            "databases" : ["mysql-remote-1","postgres-remote-2"]
            }
        ]"

curl -X POST \
    http://localhost:8081/measure-performance \
    -H 'Content-Type: application/json' \
    -d '[
            {
            "metrics": ["EXECUTION_TIME"],
            "queryName": "select_all_posts",
            "sql": "SELECT * from perf",
            "version" : "v1" ,
            "databases" : ["mysql-remote-1","postgres-remote-1","postgres-remote-2"]
            },
            {
            "metrics": ["EXECUTION_TIME"],
            "queryName": "select_all_posts",
            "sql": "SELECT author2 from perf",
            "version" : "v2" ,
            "databases" : ["mysql-remote-1","postgres-remote-1","postgres-remote-2"]
            },
            {
            "metrics": ["EXECUTION_TIME"],
            "queryName": "select_all_posts",
            "sql": "SELECT content from perf",
            "version" : "v3" ,
            "databases" : ["mysql-remote-1","postgres-remote-2"]
            }
        ]'

#Wait for processing requests
echo "Waiting 15s for processing"
sleep 15
echo "Request report"

#Request report by query name
curl -X GET http://localhost:8081/report/select_all_posts | json_pp

echo "Destroying app"
docker-compose down
docker volume rm db-query-benchmarking-service_postgres-remote-1-data
docker volume rm db-query-benchmarking-service_postgres-remote-2-data
docker volume rm db-query-benchmarking-service_postgres-querydb-data
docker volume rm db-query-benchmarking-service_mysql-remote-1-data
