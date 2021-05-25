FROM openjdk:12-jdk-alpine
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} MessagingAppRESTAPI.jar
ENTRYPOINT ["java","-jar","/MessagingAppRESTAPI.jar"]
