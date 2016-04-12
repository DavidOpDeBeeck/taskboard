FROM ubuntu

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    tar

RUN mkdir -p /opt/jdk && cd $_ && \
  wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u77-b03/jdk-8u77-linux-x64.tar.gz && \
  tar -zxf jdk-8u77-linux-x64.tar.gz && \
  update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_77/bin/java 100 && \
  update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_77/bin/javac 100 && \
  rm -f jdk-8u77-linux-x64.tar.gz

RUN mkdir -p /opt/gradle && cd $_ && \
  wget https://services.gradle.org/distributions/gradle-2.3-bin.zip && \
  unzip gradle-2.3-bin.zip && \
  rm -f gradle-2.3-bin.zip

COPY . /opt/rest-api

ENV GRADLE_HOME=/opt/gradle/gradle-2.3
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /opt/rest-api

CMD gradle task test
