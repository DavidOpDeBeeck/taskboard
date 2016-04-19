
/**
* Networks
*/

TEST_NETWORK = 'test-network';
ACC_NETWORK = 'acc-network';

/**
* Containers
*/

TASKBOARD_CONTAINER = 'taskboard-container';
MYSQL_CONTAINER = 'mysql-container';

/**
* Workflow
*/

node {

    git 'https://github.com/DavidOpDeBeeck/taskboard.git'

    testEnvironment()

}

def testEnvironment() {
  def taskboardContainer, mysqlContainer;

  try {
    createNetwork(TEST_NETWORK)

    def currentDir = pwd()


    sh 'cp ops/taskboard/back-end/test/Dockerfile taskboard/Dockerfile'
    def taskboardImage = docker.build(TASKBOARD_CONTAINER, "taskboard")

    sh 'cp ops/taskboard/db/test/Dockerfile taskboard/Dockerfile'
    def mysqlImage = docker.build(MYSQL_CONTAINER, "taskboard")

    taskboardContainer = taskboardImage.run("-i -v $currentDir/taskboard/rest-api:/app/rest-api")
    mysqlContainer = mysqlImage.run("-i --name=mysql")

    connect(taskboardContainer.id, TEST_NETWORK)
    connect(mysqlContainer.id, TEST_NETWORK)

    exec(taskboardContainer.id, "gradle repositoryTests -Denv=test")
    step([$class: 'JUnitResultArchiver', testResults: "$currentDir/taskboard/rest-api/taskboard-domain/build/reports/tests/index.html"])

    sh "cat $currentDir/taskboard/rest-api/taskboard-domain/build/reports/tests/index.html"

  } catch (err) {}
    finally {
       disconnect(taskboardContainer.id, TEST_NETWORK)
       disconnect(mysqlContainer.id, TEST_NETWORK)
       stop(taskboardContainer.id)
       stop(mysqlContainer.id)
       removeNetwork(TEST_NETWORK)
    }
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

/*
 Test Omgeving :

 - Maak container van REST API
 - Maak container van MYSQL (test)

 - Run MYSQL
 - Run REST API

 - Maak bridge netwerk aan en voeg beide toe

 - Run gradle tests en kijk naar output.

 -> TEST SUCCESSFULL
 -> TEST FAILED

 Acceptatie Omgeving :


 Productie (eindresultaat) :


*/
