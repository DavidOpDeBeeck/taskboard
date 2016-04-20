FROM jenkins

USER root

RUN apt-get update && apt-get install dos2unix -y

RUN curl -sSL https://get.docker.com/ | sh

COPY plugins.txt /usr/share/jenkins/ref/
RUN dos2unix /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt

# -v /var/run/docker.sock:/var/run/docker.sock -v /var/jenkins_home/jobs:/var/jenkins_home/jobs
