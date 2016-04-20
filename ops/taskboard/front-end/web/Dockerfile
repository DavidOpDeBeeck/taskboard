FROM python:3.5.1-slim

RUN mkdir /app

COPY taskboard/web /app

EXPOSE 8000

WORKDIR /app

ENTRYPOINT [ "python", "-m", "http.server" ]
