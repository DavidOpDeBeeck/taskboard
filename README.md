# Taskboard Application

## Configuration

When executing a test or a database migration you will have to specify a configuration that will be used.  
A few configuration files are made available under `taskboard/conf/`, you can add your own configuration files or edit the existing ones.

The configuration file can be specified using the `-Denv` argument.  
E.g. `gradle flywayMigrate -Denv=local` when `/taskboard/conf/local.properties` exists).

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

## Domain

The domain project contains the core and repositories classes of the taskboard. You can test the repositories using the `repositoryTests` task of the root gradle project.

```bash
gradle repositoryTests -Denv=local
```

It is advised to initialise the database before running this task.

```bash
gradle flywayMigrate -Denv=local
```

## REST API

The REST API project contains the web controllers (endpoints) of the taskboard. You can test the REST API using the `acceptanceTests` task of the root gradle project.

```bash
gradle acceptanceTests -Denv=local
```

It is advised to initialise the database before running this task.

```bash
gradle flywayMigrate -Denv=local
```

## Web

The web project contains the angular front-end of the taskboard. To initialise this project for the first time you will need to execute a few commands in de `/taskboard/taskboard-web/` directory.

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


## Web Test

The web test project contains the selenium tests of the front-end of the taskboard. You can test the front-end using the `webtests` task of the root gradle project.

```bash
gradle webtests -Denv=local
```


## Jenkins Pipeline

### Jenkins Pipeline Script

You can find the jenkins pipeline script in `jenkins/Jenkinsfile`.  

### Jenkins Pipeline Model


![Jenkins Workflow #1](https://raw.githubusercontent.com/DavidOpDeBeeck/taskboard/master/models/jenkins-workflow-1.png "Jenkins Workflow #1")

![Jenkins Workflow #2](https://raw.githubusercontent.com/DavidOpDeBeeck/taskboard/master/models/jenkins-workflow-2.png "Jenkins Workflow #2")
