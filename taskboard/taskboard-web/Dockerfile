FROM node:5.10.1-wheezy

# Python

RUN apt-get update && \
    apt-get install -y python2.7-dev

# Bower and Grunt

RUN npm install -g \
    bower \
    grunt-cli

# Copy files to app directory

RUN mkdir /app
COPY . /app
WORKDIR /app

# Install npm, bower dependencies

RUN npm install && \
    bower update --allow-root

# Execute grunt task

ARG API_HOST=localhost
ARG API_PORT=808O

RUN grunt --host=$API_HOST --port=$API_PORT

EXPOSE 8000

ENTRYPOINT [ "python", "-m", "SimpleHTTPServer" ]
