FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app

CMD gradle task flywayMigrate -Penv=test && \
	gradle task repositoryTests -Penv=test
