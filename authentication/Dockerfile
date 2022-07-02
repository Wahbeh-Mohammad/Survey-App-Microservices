FROM openjdk:8
WORKDIR /app
ADD ./target/authentication_service.jar application.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","application.jar"]