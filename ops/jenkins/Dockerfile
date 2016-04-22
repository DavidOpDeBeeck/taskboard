FROM jenkins

# We need root

USER root

# We download and install docker

RUN curl -sSL https://get.docker.com/ | sh

# We copy the plugin file to the image

COPY plugins.txt /usr/share/jenkins/ref/

# We download the plugins

RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt
