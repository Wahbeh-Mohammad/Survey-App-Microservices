FROM openjdk:8
WORKDIR ./app
ADD ./target/analytics_service.jar application.jar
EXPOSE 8003
ENTRYPOINT ["java","-jar","application.jar"]
