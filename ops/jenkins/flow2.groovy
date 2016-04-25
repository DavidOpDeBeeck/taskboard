//------------------------------
// GLOBALS
//------------------------------

def stageFailed = false;

//------------------------------
// DATABASE
//------------------------------

def dbPort = 3306
def dbUrl = "jdbc:mysql://192.168.99.100:$dbPort/taskboard";
def mysqlImage = docker.image("mysql"); // TODO is local now, needs test and acc different images
def mysqlContainer;

//------------------------------
// COMMIT STAGE
//------------------------------

stage "COMMIT STAGE"

node('gradle')
{
    git 'https://github.com/DavidOpDeBeeck/taskboard.git'
    dir ('taskboard'){
      stash includes: 'rest-api/**', name: 'rest-api'
      stash includes: 'web/**', name: 'web'
      stash includes: 'web-test/**', name: 'web-test'
    }
}

//------------------------------
// TEST ENVIRONMENT STAGE
//------------------------------

stage 'REPOSITORY TESTS'

node ('docker')
{
  mysqlContainer = mysqlImage.run("-it -p $dbPort:3306")
}

node ('gradle')
{
  try {
    unstash 'rest-api'
    dir ('rest-api') {
      sh "gradle flywayMigrate -Denv=test -Dflyway.url=$dbUrl"
      sh "gradle repositoryTests -Denv=test -Ddatasource.url=$dbUrl"
    }
    step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-domain/build/test-results/TEST-*.xml"])
  } catch (err){
    stageFailed = true
  }
}

node ('docker')
{
    mysqlContainer.stop()
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
  mysqlContainer = mysqlImage.run("-it -p $dbPort:3306")
}

node ('gradle')
{
    try {
      unstash 'project'
      sh "cd taskboard/rest-api && gradle flywayMigrate -Denv=test -Dflyway.url=$dbUrl"
      sh "cd taskboard/rest-api && gradle acceptanceTests -Denv=test -Ddatasource.url=$dbUrl"
      step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-rest-api/build/test-results/TEST-*.xml"])
    } catch (err){
      stageFailed = true
    }
}

node ('docker')
{
    mysqlContainer.stop()
    if (stageFailed){
      error 'Stage failed'
    }
}

//------------------------------
// DEPLOY STAGE
//------------------------------

stage "DEPLOY"

node ('docker')
{
  mysqlContainer = mysqlImage.run("-it -p $dbPort:3306")
}

node ('gradle')
{
  unstash 'rest-api'
  sh "gradle build -Denv=test -Ddatasource.url=$dbUrl"
  archive '**/taskboard-rest-api/build/libs/*.jar'
  stash includes: '**/test.properties', name: 'API-CONFIG'
  stash includes: '**/taskboard-rest-api/build/libs/*.jar', name: 'API'
}

node ('docker && java')
{
  unstash 'API'
  unstash 'API-CONFIG'
  sh 'ls -l'
  //sh 'mv test.properties application.properties'
  //sh 'java -jar -Ddatasource.url=jdbc:mysql://192.168.99.100:2376:3306/taskboard taskboard-rest-api-1.0.jar'
}

node ('docker')
{
    mysqlContainer.stop()
    if (stageFailed){
      error 'Stage failed'
    }
}

// breng web op python docker
// breng rest api op java docker
// breng web tests op java docker
