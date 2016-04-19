FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app

RUN gradle task build -i
ENTRYPOINT [  "java",
              "-Djava.security.egd=file:/dev/./urandom",
              "-jar",
              "/app/taskboard/build/libs/taskboard-1.0.jar" ]
