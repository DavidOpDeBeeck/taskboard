FROM ubuntu

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    tar \
    git \
    maven

RUN mkdir -p /opt/jdk && cd /opt && \
  wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u77-b03/jdk-8u77-linux-x64.tar.gz && \
  tar -zxf jdk-8u77-linux-x64.tar.gz -C /opt/jdk && \
  update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_77/bin/java 100 && \
  update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_77/bin/javac 100 && \
  rm -f jdk-8u77-linux-x64.tar.gz

ENV JAVA_HOME=/opt/jdk/jdk1.8.0_77
ENV PATH=$PATH:$JAVA_HOME/bin

RUN mkdir -p /opt/gradle && cd /opt/gradle && \
  wget https://services.gradle.org/distributions/gradle-2.3-bin.zip && \
  unzip gradle-2.3-bin.zip && \
  rm -f gradle-2.3-bin.zip

ENV GRADLE_HOME=/opt/gradle/gradle-2.3
ENV PATH=$PATH:$GRADLE_HOME/bin

RUN cd /opt && \
    git clone https://github.com/DavidOpDeBeeck/taskboard-domain.git && \
    cd taskboard-domain && \
    gradle task install

COPY . /opt/rest-api

WORKDIR /opt/rest-api

CMD gradle task test
