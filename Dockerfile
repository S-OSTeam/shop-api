FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/deamhome-0.1.0.jar

COPY ${JAR_FILE} app.jar

VOLUME /tmp

EXPOSE 8080

ENTRYPOINT ["java", "-jar","/app.jar"]
