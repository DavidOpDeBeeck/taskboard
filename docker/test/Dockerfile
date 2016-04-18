FROM qlik/gradle

COPY . /app

EXPOSE 8080

CMD task flywayMigrate -Penv=test
