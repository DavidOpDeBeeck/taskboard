FROM java:8-jdk

RUN mkdir app

COPY acc.properties /app/application.properties
COPY build/libs/taskboard-rest-api-*.jar /app/rest-api.jar

EXPOSE 8080

WORKDIR app

ENTRYPOINT [ "java", "-jar", "rest-api.jar" ]
