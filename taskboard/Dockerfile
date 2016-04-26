FROM qlik/gradle

COPY rest-api.jar /app

EXPOSE 8080

ENTRYPOINT [  "java",
              "-Djava.security.egd=file:/dev/./urandom",
              "-jar",
              "app/rest-api.jar" ]
