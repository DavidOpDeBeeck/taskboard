FROM python:3.5.1-slim

RUN mkdir /app

COPY . /app

EXPOSE 8000

WORKDIR /app

CMD [ "python", "-m", "http.server" ]