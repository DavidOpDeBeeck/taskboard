FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app/taskboard-domain

ENTRYPOINT ["gradle" ,"task", "test", "-Denv=test"]
