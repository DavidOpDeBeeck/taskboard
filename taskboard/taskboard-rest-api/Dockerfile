FROM java:8-jdk

RUN mkdir app

COPY acc.properties /app/application.properties
COPY build\libs\taskboard-rest-api-1.0.jar /app/rest-api.jar

EXPOSE 8080

WORKDIR app

ENTRYPOINT [  "java",
              "-Djava.security.egd=file:/dev/./urandom",
              "-jar",
              "rest-api.jar" ]
