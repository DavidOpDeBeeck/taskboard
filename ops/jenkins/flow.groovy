
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
  try {
    createNetwork(TEST_NETWORK)
    sh 'cp ops/taskboard/back-end/test/Dockerfile taskboard/Dockerfile'
    def taskboardImage = docker.build(TASKBOARD_CONTAINER, "taskboard")

    sh 'cp ops/taskboard/db/test/Dockerfile taskboard/Dockerfile'
    def mysqlImage = docker.build(MYSQL_CONTAINER, "taskboard")

    def taskboardContainer = taskboardImage.run()
    def mysqlContainer = mysqlImage.run()

    connect(taskboardContainer.id, TEST_NETWORK)
    connect(mysqlContainer.id, TEST_NETWORK)

    exec(taskboardContainer.id, "gradle repositoryTests -Denv=test")
  } catch (err) {}
    finally {
        try { taskboardContainer.stop() } catch(err) {}
        try { mysqlContainer.stop() } catch(err) {}
        try { removeNetwork(TEST_NETWORK) } catch(err) {}
    }
}

def exec(container, cmd) {
  sh "docker exec -i ${container} ${cmd}"
}

def connect(container, network) {
	sh "docker network connect ${network} ${container}"
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
