FROM node:5.10.1-wheezy

RUN apt-get update && apt-get install -y python2.7-dev
RUN npm install -g bower

RUN mkdir /app

COPY . /app

RUN cd /app && bower update --allow-root

EXPOSE 8000

WORKDIR /app

ENTRYPOINT [ "python", "-m", "SimpleHTTPServer" ]
