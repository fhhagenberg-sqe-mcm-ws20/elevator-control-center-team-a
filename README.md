# Elevator Control Center of Team A
By Simon Angerbauer, Markus Seiberl and Anika Seibezeder

| [![CI Actions Status](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-a/workflows/CI/badge.svg)](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-a/actions) | [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-a&metric=alert_status)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-a) | [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-a&metric=coverage)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-a) | [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-a&metric=ncloc)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-a) |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

## Prerequisites
- [x] Java 13 SDK (e.g. Oracle or OpenJDK)
- [x] Maven 3 (If you use an IDE like Eclipse or IntelliJ, Maven is already included)
	- see http://maven.apache.org/install.html
- [x] An IDE or code editor of your choice

> Confirm your installation with `mvn -v` in a new shell. The result should be similar to:

```
$ mvn -v
Apache Maven 3.6.2 (40f52333136460af0dc0d7232c0dc0bcf0d9e117; 2019-08-27T17:06:16+02:00)
Maven home: C:\Users\winterer\.tools\apache-maven-3.6.2
Java version: 13, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-13
Default locale: en_GB, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

## Instructions
> You can download a pre-built `.jar` file [here](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-a/releases/latest/download/elevator-control-center-team-a-1.0.jar). Run the file with `java -jar elevator-control-center-team-a-1.0.jar`.  

### Run Application with Maven
1. Import this git repository into your favourite IDE
2. Run it with `mvn clean javafx:run`

### Build .jar File with Maven
1. Import this git repository into your favourite IDE
2. Build the application with `mvn clean package`
3. The resulting archive (`elevator-control-center-team-a-1.0.jar` file) is in the `target` directory
