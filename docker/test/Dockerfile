FROM qlik/gradle

COPY . /app

EXPOSE 8080

WORKDIR /app

CMD task flywayMigrate -Penv=test && \
	task repositoryTests -Penv=test
