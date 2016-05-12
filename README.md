# Taskboard Application

## Table of contents

1. [Configuration](#configuration)
2. [Installation](#installation)
3. [Docker](#docker)
4. [Jenkins Pipeline](#jenkins-pipeline)


## Configuration

When executing a test or a database migration you will have to specify a configuration that will be used.  
A few configuration files are made available under `conf/`, you can add your own configuration files or edit the existing ones.

The configuration file can be specified using the `-Denv` argument.  
E.g. `gradle flywayMigrate -Denv=local` when `conf/local.properties` exists).

**You can always overwrite the properties in the configuration files using `-D{prop.name}={prop.value}`.**

#### Flyway

When executing a flyway migration the used configuration file needs to contain the following properties.

```properties
flyway.url=
flyway.user=
flyway.password=
flyway.locations=
flyway.baselineOnMigrate=
```

#### Domain and REST API

When executing a repository or REST API test the used configuration file needs to contain the following properties.

```properties
datasource.url=
datasource.user=
datasource.password=
```

#### REST API

When executing a REST API test the used configuration file needs to contain the following properties.

```properties
server.address=
server.port=
server.ssl.enabled=
```

#### Web Tests

When executing a Web test the used configuration file needs to contain the following properties.

```properties
webdriver.server.address=
webdriver.server.port=
webdriver.server.ssl.enabled=

webdriver.node.address=
webdriver.node.port=
```

## Installation

#### Flyway

You can edit or add scripts in the `db/migration` directory.

```bash
gradle flywayMigrate -Denv=local
```


#### Domain

The domain project contains the core and repositories classes of the taskboard. You can test the repositories using the `repositoryTests` task of the root gradle project.

```bash
gradle repositoryTests -Denv=local
```

It is advised to initialise the database before running this task.

```bash
gradle flywayMigrate -Denv=local
```

#### REST API

The REST API project contains the web controllers (endpoints) of the taskboard. You can test the REST API using the `acceptanceTests` task of the root gradle project.

```bash
gradle acceptanceTests -Denv=local
```

It is advised to initialise the database before running this task.

```bash
gradle flywayMigrate -Denv=local
```

When you want to run the REST API you need to execute the following command.

```bash
gradle bootRun -Denv=local
```

#### Web

The web project contains the angular front-end of the taskboard. To initialise this project for the first time you will need to execute a few commands in de `taskboard-web/` directory.

Install all the npm dependencies in package.json.

```bash
npm install
```

Install all the bower dependencies in bower.json.

```bash
bower update
```

Execute the default grunt task to generate the files that are included in the index.html.

```bash
grunt
```

**When you make changes in the `app` directory you will need to rerun the `grunt` task.**


#### Web Test

The web test project contains the selenium tests of the front-end of the taskboard. You can test the front-end using the `webtests` task of the root gradle project.

```bash
gradle webtests -Denv=local
```

## Docker

#### General

The jenkins pipeline script uses docker to create jenkins slaves. You can use the docker images below for your jenkins slaves.

- [dodb/jenkins-java-slave](https://hub.docker.com/r/dodb/jenkins-java-slave)
- [dodb/jenkins-java-gradle-slave](https://hub.docker.com/r/dodb/jenkins-java-gradle-slave/)
- [dodb/jenkins-java-docker-slave](https://hub.docker.com/r/dodb/jenkins-java-docker-slave/)
- [dodb/jenkins-java-gradle-docker-slave](https://hub.docker.com/r/dodb/jenkins-java-gradle-docker-slave)

#### REST API

The Dockerfile requires that a jar is present in `taskboard-rest-api/build/libs/`. The easiest way to achieve this is to build the REST API with gradle.

```bash
gradle build -x
```

You will also need to add the appropriate configuration in `taskboard-rest-api/application.properties`.

When this is done you can build the docker images for the rest api.

```bash
docker build -t rest-api .
```

#### Web

The Dockerfile has 2 optional build arguments `API_HOST` and `API_PORT`.

```bash
docker build -t web --build-arg API_HOST=${api.host} --build-arg API_PORT=${api.port} .
```
**When left empty `localhost:8080` is used.**

## Jenkins Pipeline

#### Jenkins Pipeline Script

You can find the jenkins pipeline script in `Jenkinsfile`.  

#### Jenkins Pipeline Model

<p align="center">
  <img src="https://raw.githubusercontent.com/DavidOpDeBeeck/taskboard/master/models/jenkins-workflow-1.png" alt="Jenkins Workflow #1"/>
</p>
