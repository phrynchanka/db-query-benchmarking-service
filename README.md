 ##Implementation notes

###Run application
```bash
mvn spring-boot:run
```

####Tested databases configuration

It is possible to add databases or modify existed. 

>You need to specify:
> jdbcUrl
> - password
> - username
> - driver-class-name: ${choose one fro db.drivers}. 

If you want to add new database vendor (f.e `oracle`), 
you need to add `jdbc-driver` to `pom.xml` and configure remote db in `application.yml`

```yaml
remotes:
    perf_postgresql:
      jdbcUrl: jdbc:postgresql://localhost:5432/perf_postgresql
      driver-class-name: ${db.drivers.postgresql}
      password: pwd
      username: postgres
      maximum-pool-size: 1
      poolName: perf_0-db-pool

    perf_mssql_1:
      jdbcUrl: jdbc:sqlserver://localhost:1433;DatabaseName=perf_mssql_1
      driver-class-name: ${db.drivers.mssql}
      password: Password123
      username: sa
      maximum-pool-size: 1
      poolName: perf_mssql_1-db-pool

    perf_mssql_2:
      jdbcUrl: jdbc:sqlserver://localhost:1433;DatabaseName=perf_mssql_2
      driver-class-name: ${db.drivers.mssql}
      password: Password123
      username: sa
      maximum-pool-size: 1
      poolName: perf_mssql_2-db-pool
```

####Query performance test request

```bash
curl -X POST \
  http://localhost:8081/measure-performance \
  -H 'Content-Type: application/json' \
  -d '[
	{
		"metrics": ["EXECUTION_TIME"],
		"queryName": "select_1",
		"sql": "SELECT 1",
		"version" : "v1" ,
		"databases" : ["perf_mssql_1","perf_mssql_2","perf_postgresql"]
	},
	{
		"metrics": ["EXECUTION_TIME"],
		"queryName": "select_1",
		"sql": "SELECT 2",
		"version" : "v2" ,
		"databases" : ["perf_mssql_1","perf_mssql_2","perf_postgresql"]
	}
]'
```

####Report by query name

```bash
curl -X GET http://localhost:8081/report/select_1
```

```json
{
    "queryName": "select_1",
    "statistics": [
        {
            "sql": "SELECT 1",
            "metrics": "EXECUTION_TIME",
            "result": "49",
            "version": "v1",
            "database": "perf_mssql_1",
            "status": "SUCCESS",
            "executed": "2019-11-04T13:01:13.402+0000"
        },
        {
            "sql": "SELECT 1",
            "metrics": "EXECUTION_TIME",
            "result": "46",
            "version": "v1",
            "database": "perf_mssql_2",
            "status": "SUCCESS",
            "executed": "2019-11-04T13:01:13.399+0000"
        },
        {
            "sql": "SELECT 1",
            "metrics": "EXECUTION_TIME",
            "result": "0",
            "version": "v1",
            "database": "perf_postgresql",
            "status": "SUCCESS",
            "executed": "2019-11-04T13:01:13.423+0000"
        },
        {
            "sql": "SELECT 2",
            "metrics": "EXECUTION_TIME",
            "result": "46",
            "version": "v2",
            "database": "perf_mssql_1",
            "status": "SUCCESS",
            "executed": "2019-11-04T13:01:13.399+0000"
        },
        {
            "sql": "SELECT 2",
            "metrics": "EXECUTION_TIME",
            "result": "7",
            "version": "v2",
            "database": "perf_mssql_2",
            "status": "SUCCESS",
            "executed": "2019-11-04T13:01:13.430+0000"
        },
        {
            "sql": "SELECT 2",
            "metrics": "EXECUTION_TIME",
            "result": "44",
            "version": "v2",
            "database": "perf_postgresql",
            "status": "SUCCESS",
            "executed": "2019-11-04T13:01:13.397+0000"
        }
    ]
}
```