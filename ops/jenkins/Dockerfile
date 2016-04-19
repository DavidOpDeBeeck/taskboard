FROM jenkins

USER root

RUN apt-get update && apt-get install dos2unix -y

RUN curl -sSL https://get.docker.com/ | sh
RUN curl -L https://github.com/docker/compose/releases/download/1.7.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
RUN chmod +x /usr/local/bin/docker-compose

COPY plugins.txt /plugins.txt
RUN dos2unix /plugins.txt
RUN /usr/local/bin/plugins.sh /plugins.txt
