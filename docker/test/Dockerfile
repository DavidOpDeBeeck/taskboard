FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app

ENTRYPOINT ["/bin/bash"]

CMD gradle task flywayMigrate -Penv=test && \
	gradle task repositoryTests -Penv=test
