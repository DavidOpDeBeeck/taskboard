
// NETWORKS

NETWORK_TEST = 'test-network';
NETWORK_ACC = 'acc-network';

// IMAGES

TASKBOARD = [
  img         : null,
  container   : null,
  name        : 'taskboard-image',
  dockerfile  : 'ops/taskboard/back-end/Dockerfile'
]

WEB = [
  img         : null,
  container   : null,
  name        : 'web-image',
  dockerfile  : 'ops/taskboard/front-end/web/Dockerfile'
]

MYSQL_TEST = [
  img         : null,
  container   : null,
  name        : 'mysql-test-image',
  dockerfile  : 'ops/taskboard/db/test/Dockerfile'
]

MYSQL_ACC = [
  img         : null,
  container   : null,
  name        : 'mysql-acc-image',
  dockerfile  : 'ops/taskboard/db/acceptance/Dockerfile'
]

/**
* Workflow
*/

stage 'COMMIT STAGE'
var dbUrl = null;
node('docker') {
  // spin up DB
  dbUrl =
}
node('java') {
    git 'https://github.com/DavidOpDeBeeck/taskboard.git'
    // build gradle -> build + unit tests
    // collect results
    // archive workspace
  }

stage 'TEST STAGE'
  node('docker') {
    makeImages()
    cleanTestEnvironment()
    testEnvironment()
  }

  node('java') {
    // unarchive workspace
    // run tests against TEST ENV
    // collect results
    // archive workspace
  }



    stage 'Test Environment'



    stage 'Acceptance Environment'

    cleanAcceptanceEnvironment()
    acceptanceEnvironment()

    stage 'Web Deploy'

    webDeploy()
}

def makeImages() {
  TASKBOARD.img   = build(TASKBOARD.name, TASKBOARD.dockerfile)
  WEB.img         = build(WEB.name, WEB.dockerfile)
  MYSQL_ACC.img   = build(MYSQL_ACC.name, MYSQL_ACC.dockerfile)
  MYSQL_TEST.img  = build(MYSQL_TEST.name, MYSQL_TEST.dockerfile)
}

/**
* TEST ENVIRONMENT
*/

def cleanTestEnvironment(){
  try { disconnect(TASKBOARD.container.id, NETWORK_TEST) } catch (err) {}
  try { disconnect(MYSQL_TEST.container.id, NETWORK_TEST) } catch (err) {}
  try { stop(TASKBOARD.container.id) } catch (err) {}
  try { stop(MYSQL_TEST.container.id) } catch (err) {}
  try { removeNetwork(NETWORK_TEST) } catch (err) {}
}

def testEnvironment() {
  try {
    createNetwork(NETWORK_TEST)

    def currentDir = pwd()
    def resultVolume = "${currentDir}/taskboard/rest-api/taskboard-domain/build/test-results:/app/taskboard-domain/build/test-results"

    TASKBOARD.container = TASKBOARD.img.run("-i -v ${resultVolume}")
    MYSQL_TEST.container = MYSQL_TEST.img.run("-i --name=mysql")

    connect(TASKBOARD.container.id, NETWORK_TEST)
    connect(MYSQL_TEST.container.id, NETWORK_TEST)

    exec(TASKBOARD.container.id, "gradle flywayMigrate -Denv=test")
    exec(TASKBOARD.container.id, "gradle repositoryTests -Denv=test")

    step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-domain/build/test-results/TEST-*.xml"])
  } catch (err) {
    throw err
  } finally {
      cleanTestEnvironment()
  }
}

/**
* ACCEPTANCE ENVIRONMENT
*/

def cleanAcceptanceEnvironment() {
  try { disconnect(TASKBOARD.container.id, NETWORK_ACC) } catch (err) {}
  try { disconnect(MYSQL_ACC.container.id, NETWORK_ACC) } catch (err) {}
  try { stop(TASKBOARD.container.id) } catch (err) {}
  try { stop(MYSQL_ACC.container.id) } catch (err) {}
  try { removeNetwork(NETWORK_ACC) } catch (err) {}
}

def acceptanceEnvironment() {
  try {
    createNetwork(NETWORK_ACC)

    def currentDir = pwd()
    def resultVolume = "${currentDir}/taskboard/rest-api/taskboard-rest-api/build/test-results:/app/taskboard-rest-api/build/test-results"

    TASKBOARD.container = TASKBOARD.img.run("-i -v ${resultVolume}")
    MYSQL_ACC.container = MYSQL_ACC.img.run("-i --name=mysql")

    connect(TASKBOARD.container.id, NETWORK_ACC)
    connect(MYSQL_ACC.container.id, NETWORK_ACC)

    exec(TASKBOARD.container.id, "gradle flywayMigrate -Denv=acceptance")
    exec(TASKBOARD.container.id, "gradle acceptanceTests -Denv=acceptance")

    step([$class: 'JUnitResultArchiver', testResults: "**/taskboard-rest-api/build/test-results/TEST-*.xml"])
  } catch (err) {
    throw err
  } finally {
      cleanAcceptanceEnvironment()
  }
}

/**
* WEB DEPLOY
*/

def webDeploy() {
  WEB.container = WEB.img.run("-i -p 8000:8000")
}


/**
* Docker functions
*/

def build(name, dockerfile) {
  sh "cp ${dockerfile} Dockerfile"
  def image = docker.build(name)
  sh 'rm -f Dockerfile'
  return image
}

def exec(container, cmd) {
  sh "docker exec -i ${container} ${cmd}"
}

def connect(container, network) {
	sh "docker network connect ${network} ${container}"
}

def disconnect(container, network) {
	sh "docker network disconnect ${network} ${container}"
}

def stop(container) {
  sh "docker rm -f ${container}"
}

def createNetwork(network) {
	sh "docker network create -d bridge ${network}"
}

def removeNetwork(network) {
	sh "docker network rm ${network} || echo 'Network ${network} does not exist!'"
}
