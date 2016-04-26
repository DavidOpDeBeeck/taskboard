//------------------------------
// GLOBALS
//------------------------------

def stageFailed = false;

//------------------------------
// DATABASE CONFIGS
//------------------------------

def testDatabase = [
    ip        : '192.168.99.100',
    port      : '50000',
    user      : 'db-test',
    password  : 'g6qf98xy',
    instance  :  null
]

def accDatabase = [
    ip        : '192.168.99.100',
    port      : '60000',
    user      : 'db-acc',
    password  : 'asiyat37',
    instance  :  null
]

def web = [
    port      : '9000',
    instance  : null
]

def restApi = [
    port      : '8181',
    instance  :  null
]

//------------------------------
// COMMIT STAGE
//------------------------------

stage "COMMIT STAGE"

node('gradle')
{
    git 'https://github.com/DavidOpDeBeeck/taskboard.git'
    dir ('taskboard'){
      stash includes: '**', name: 'taskboard'
    }
}

//------------------------------
// TEST ENVIRONMENT STAGE
//------------------------------

stage 'REPOSITORY TESTS'

node ('docker')
{
  testDatabase.instance = docker.image('mysql').run("-it -p $testDatabase.port:3306 -e MYSQL_DATABASE=taskboard -e MYSQL_USER=$testDatabase.user -e MYSQL_PASSWORD=$testDatabase.password -e MYSQL_ALLOW_EMPTY_PASSWORD=true")
}

node ('gradle')
{
  try {
    unstash 'taskboard'
    sh "gradle flywayMigrate -Denv=test"
    sh "gradle repositoryTests -Denv=test"
    step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-domain/build/test-results/TEST-*.xml"])
  } catch (err){
    stageFailed = true
  }
}

node ('docker')
{
    testDatabase.instance.stop()
    if (stageFailed){
      error 'Stage failed'
    }
}

//------------------------------
// ACCEPTANCE ENVIRONMENT STAGE
//------------------------------

stage "REST API TESTS"

node ('docker')
{
  accDatabase.instance = docker.image('mysql').run("-it -p $accDatabase.port:3306 -e MYSQL_DATABASE=taskboard -e MYSQL_USER=$accDatabase.user -e MYSQL_PASSWORD=$accDatabase.password -e MYSQL_ALLOW_EMPTY_PASSWORD=true")
}

node ('gradle')
{
    try {
      unstash 'taskboard'
      sh "gradle flywayMigrate -Denv=acc"
      sh "gradle acceptanceTests -Denv=acc"
      step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-rest-api/build/test-results/TEST-*.xml"])
    } catch (err){
      stageFailed = true
    }
}

node ('docker')
{
    if (stageFailed) {
      accDatabase.instance.stop()
      error 'Stage failed'
    }
}

stage "WEB TESTS"

node ('gradle')
{
  unstash 'taskboard'
  sh "gradle build -x test"
  archive '**/taskboard-rest-api/build/libs/*.jar'
  dir ('conf') {
    stash includes: 'acc.properties', name: 'config'
  }
  dir ('taskboard-rest-api') {
    stash includes: '**', name: 'rest-api'
  }
  dir ('taskboard-web') {
    stash includes: '**', name: 'web'
  }
}

node ('docker')
{
  unstash 'web'
  web.instance = docker.build(env.BUILD_TAG + "-web").run("-p $web.port:8000")
}

node ('docker') {
  unstash 'config'
  unstash 'rest-api'
  restApi.instance = docker.build(env.BUILD_TAG + "-rest-api").run("-p $restApi.port:8080")
}

node ('java')
{
  try {
    unstash 'taskboard'
    sh "gradle webTests -Denv=acc"
    step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-web-tests/build/test-results/TEST-*.xml"])
  } catch (err){
    stageFailed = true
  }
}

node ('docker')
{
    accDatabase.instance.stop()
    if (stageFailed){
      error 'Stage failed'
    }
}
