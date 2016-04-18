# Taskboard RESTfull API

## Flyway

```Gradle
gradle flywayMigrate -Penv=(local|test|acc)
```

## Taskboard Domain

### Test Environment 

##### Manual 

```sh
cd taskboard-domain
```
```Gradle
gradle task test -Penv=test
```

##### Automatic 

```Docker
docker-compose -f docker/domain/test/docker-compose.yml -d
```

## Taskboard Endpoints

### Test Environment 

##### Manual 

```sh
cd taskboard-api
```
```Gradle
gradle task test -Penv=test
```

##### Automatic 

```Docker
docker-compose -f docker/api/test/docker-compose.yml -d
```

## Taskboard REST API

### Acceptance Environment 

##### Manual 

```sh
cd .
```
```Gradle
gradle task build -Penv=acc
```

##### Automatic 

```Docker
docker-compose -f docker/complete/test/docker-compose.yml -d
