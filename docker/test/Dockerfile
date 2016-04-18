FROM qlik/gradle

COPY . /app

EXPOSE 8080

ENTRYPOINT ["task", "flywayMigrate", "-Penv=test"]
