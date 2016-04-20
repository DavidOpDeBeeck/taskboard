
// NETWORKS

NETWORK_TEST = 'test-network';
NETWORK_ACC = 'acc-network';

// CONTAINERS

CONT_TASKBOARD = 'taskboard-container';
CONT_MYSQL = 'mysql-container';

/**
* Workflow
*/

node {

    stage 'Git Clone'

    git 'https://github.com/DavidOpDeBeeck/taskboard.git'

    stage 'Test Environment'

    initTestEnvironment()
    execTestEnvironment()

}

def initTestEnvironment(){
  try {
    disconnect(taskboardContainer.id, NETWORK_TEST)
    disconnect(mysqlContainer.id, NETWORK_TEST)
    stop(taskboardContainer.id)
    stop(mysqlContainer.id)
    removeNetwork(NETWORK_TEST)
  } catch (err) {}
}

def execTestEnvironment() {
  def taskboardContainer, mysqlContainer;
  try {
    createNetwork(NETWORK_TEST)

    def currentDir = pwd()

    def taskboardImage = build(CONT_TASKBOARD, 'ops/taskboard/back-end/test/Dockerfile')
    def mysqlImage = build(CONT_MYSQL, 'ops/taskboard/db/test/Dockerfile')

    def resultVolume = "${currentDir}/taskboard/rest-api/taskboard-domain/build/test-results:/app/taskboard-domain/build/test-results"

    taskboardContainer = taskboardImage.run("-i -v ${resultVolume}")
    mysqlContainer = mysqlImage.run("-i --name=mysql")

    connect(taskboardContainer.id, NETWORK_TEST)
    connect(mysqlContainer.id, NETWORK_TEST)

    exec(taskboardContainer.id, "gradle flywayMigrate -Denv=test")
    exec(taskboardContainer.id, "gradle repositoryTests -Denv=test")

    step([$class: 'JUnitResultArchiver', testResults: "**/test-results/TEST-*.xml"])
  } catch (err) {
    throw err
  }
    finally {
       disconnect(taskboardContainer.id, NETWORK_TEST)
       disconnect(mysqlContainer.id, NETWORK_TEST)
       stop(taskboardContainer.id)
       stop(mysqlContainer.id)
       removeNetwork(NETWORK_TEST)
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
