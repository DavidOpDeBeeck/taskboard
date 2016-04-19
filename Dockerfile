FROM qlik/gradle

COPY . /app

EXPOSE 8080

ENTRYPOINT ["gradle", "task", "repositoryTests", "-Penv=test"]
