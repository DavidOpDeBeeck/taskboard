//------------------------------
// GLOBALS
//------------------------------

def stageFailed = false;

//------------------------------
// DATABASE CONFIGS
//------------------------------

def testDatabase = [
    name      : env.BUILD_TAG + "-test-database",
    port      : '55000',
    user      : 'db-test',
    password  : 'g6qf98xy'
]

def accDatabase = [
    name      : env.BUILD_TAG + "-acc-database",
    port      : '60000',
    user      : 'db-acc',
    password  : 'asiyat37'
]

def web = [
    name      : env.BUILD_TAG + "-web",
    port      : '8000'
]

def restApi = [
    name      : env.BUILD_TAG + "-rest-api",
    port      : '9000'
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
  def node = getNodeContainer()
  runContainer( 'mysql',
                'mysql',
                "-d -p $testDatabase.port:3306 -e MYSQL_DATABASE=taskboard -e MYSQL_USER=$testDatabase.user -e MYSQL_PASSWORD=$testDatabase.password -e MYSQL_ALLOW_EMPTY_PASSWORD=true" )
  createNetwork('test-network')
  connect('test-network', node)
  connect('test-network', 'mysql')
  sh "ping mysql"
}

node ('gradle')
{
  try
  {
    unstash 'taskboard'
    sh "gradle flywayMigrate -Denv=test"
    sh "gradle repositoryTests -Denv=test"
    step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-domain/build/test-results/TEST-*.xml"])
  } catch (err)
  {
    stageFailed = true
  }
}

node ('docker')
{
  removeContainer( testDatabase.name )
  if (stageFailed)
    error 'Stage failed'
}

//------------------------------
// ACCEPTANCE ENVIRONMENT STAGE
//------------------------------

stage "REST API TESTS"

node ('docker')
{
  runContainer( accDatabase.name,
                'mysql',
                "-it -p $accDatabase.port:3306 -e MYSQL_DATABASE=taskboard -e MYSQL_USER=$accDatabase.user -e MYSQL_PASSWORD=$accDatabase.password -e MYSQL_ALLOW_EMPTY_PASSWORD=true" )
}

node ('gradle')
{
    try
    {
      unstash 'taskboard'
      sh "gradle flywayMigrate -Denv=acc"
      sh "gradle acceptanceTests -Denv=acc"
      step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-rest-api/build/test-results/TEST-*.xml"])
    } catch (err)
    {
      stageFailed = true
    }
}

node ('docker')
{
    if (stageFailed) {
      removeContainer( accDatabase.name )
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
  buildImage( web.name )
  runContainer( web.name, web.name, "-p $web.port:8000" )
}

node ('docker') {
  unstash 'config'
  unstash 'rest-api'
  buildImage( restApi.name )
  runContainer( restApi.name, restApi.name, "-p $restApi.port:8080" )
}

node ('webdriver && gradle')
{
  try
  {
    unstash 'taskboard'
    sh "gradle webTests -Denv=acc"
  } catch (err)
  {
    stageFailed = true
  }
  step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-web-test/build/test-results/TEST-*.xml"])
}

node ('docker')
{
  removeContainer( accDatabase.name )
  if (stageFailed)
    error 'Stage failed'
}

//------------------------------
// NODE FUNCTIONS
//------------------------------

def getNodeContainer()
{
  return env.NODE_NAME.split("-")[1]
}

//------------------------------
// DOCKER FUNCTIONS
//------------------------------

def buildImage( name )
{
  sh "docker build -t $name ."
}

def runContainer( name , image , options )
{
  sh "docker run $options --name=$name $image"
}

def removeContainer( name )
{
  sh "docker rm -f $name"
}

def createNetwork( name )
{
  sh "docker network create -d bridge $name"
}

def removeNetwork( name )
{
	sh "docker network rm $name || echo 'Network $name does not exist!'"
}

def connect( network , container )
{
  sh "docker network connect $network $container"
}

def disconnect( network , container )
{
  sh "docker network disconnect $network $container"
}
