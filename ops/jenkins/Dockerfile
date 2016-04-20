FROM jenkins

RUN apt-get update && apt-get install dos2unix -y

COPY plugins.txt /usr/share/jenkins/ref/
RUN dos2unix /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt
