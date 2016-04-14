FROM qlik/gradle

RUN git clone https://github.com/DavidOpDeBeeck/taskboard-domain.git && \
    cd taskboard-domain && \
    gradle task install

COPY . /app/taskboard-rest-api

EXPOSE 8080

WORKDIR /app/taskboard-rest-api
ENTRYPOINT ["gradle" ,"task", "test", "-i"]
