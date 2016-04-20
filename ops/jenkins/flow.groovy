
// NETWORKS

NETWORK_TEST = 'test-network';
NETWORK_ACC = 'acc-network';

// IMAGES

TASKBOARD = [
  img         : null,
  container   : null,
  name        : 'mysql-test-image',
  dockerfile  : 'ops/taskboard/back-end/Dockerfile'
]

MYSQL_TEST = [
  img         : null,
  container   : null,
  name        : 'taskboard-test-image',
  dockerfile  : 'ops/taskboard/db/test/Dockerfile'
]

MYSQL_ACC = [
  img         : null,
  container   : null,
  name        : 'taskboard-acc-image',
  dockerfile  : 'ops/taskboard/db/acceptance/Dockerfile'
]

/**
* Workflow
*/

node {

    stage 'Git Clone'

    git 'https://github.com/DavidOpDeBeeck/taskboard.git'

    stage 'Making Docker Images'

    TASKBOARD.img = build(TASKBOARD.name, TASKBOARD.dockerfile)
    MYSQL_ACC.img = build(MYSQL_ACC.name, MYSQL_ACC.dockerfile)
    MYSQL_TEST.img = build(MYSQL_TEST.name, MYSQL_TEST.dockerfile)

    stage 'Test Environment'

    cleanTestEnvironment()
    testEnvironment()

    stage 'Acceptance Environment'

    cleanAcceptanceEnvironment()
    acceptanceEnvironment()

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
	try { sh "docker network rm ${network}" } catch(err) {}
}
