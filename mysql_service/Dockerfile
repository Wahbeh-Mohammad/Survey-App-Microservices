FROM openjdk:8
WORKDIR ./app
ADD ./target/mysql_service.jar application.jar
EXPOSE 8001
ENTRYPOINT ["java","-jar","application.jar"]