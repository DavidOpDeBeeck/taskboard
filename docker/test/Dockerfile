FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app

ENTRYPOINT ["gradle" ,"task", "repositoryTests", "-Denv=test"]
