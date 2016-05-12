FROM java:8-jdk

COPY application.properties /application.properties
COPY build/libs/taskboard-rest-api-1.0.jar /rest-api.jar

RUN sh -c 'touch /rest-api.jar'

EXPOSE 9000

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rest-api.jar"]
