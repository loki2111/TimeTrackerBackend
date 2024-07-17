#Base Image
FROM openjdk:17
#Copying Data or renaming files.
ADD target/timetracker-0.0.1-SNAPSHOT.jar  springboot-mysql-docker.jar
#Entrypoint to run jar file
ENTRYPOINT ["java","-jar","springboot-mysql-docker.jar"]
