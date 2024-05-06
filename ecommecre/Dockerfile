FROM openjdk:17-jdk-openjdk
ARG JAR_FILE = target/*.jar
COPY ./target/goodboy0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java", "-jar" ,"app.jar" ]
