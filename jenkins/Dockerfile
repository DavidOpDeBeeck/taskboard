FROM jenkins:2.0

# We need root

USER root

# We copy the plugin file to the image

COPY plugins.txt /usr/share/jenkins/ref/

# We download the plugins

RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt
