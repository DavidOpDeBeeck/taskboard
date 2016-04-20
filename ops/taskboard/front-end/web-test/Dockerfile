FROM qlik/gradle

COPY . /app

WORKDIR /app

ENTRYPOINT ["gradle" ,"task", "test", "-i"]
