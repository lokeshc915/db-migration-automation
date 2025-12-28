# DB Migration Automation

## Overview

Automates database schema and data migrations using Spring Boot APIs.

## How to Run

``` bash
mvn clean install
mvn spring-boot:run
```

## APIs

run as java application with main method
DBMigrationAppStartupRunner is directly calling the service and get the meta data of the db

-   POST /dbmig
-   GET /health
