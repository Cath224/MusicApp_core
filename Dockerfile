FROM eclipse-temurin:22-jdk
VOLUME /tmp
ARG JAR_FILE
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]