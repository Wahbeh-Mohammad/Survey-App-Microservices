FROM openjdk:8
WORKDIR ./app
ADD ./target/mongodb_service.jar application.jar
EXPOSE 8002
ENTRYPOINT ["java","-jar","application.jar"]