FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app

RUN gradle task flywayMigrate -Penv=test

ENTRYPOINT ["gradle" ,"task", "repositoryTests", "-Penv=test"]
