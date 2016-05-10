( () => {
    'use strict'
    angular.module( 'taskBoardApp.services' )
        .factory( "API", apiService )

    function apiService( $resource, apiUrl, Security, $routeParams ) {

        let project = $resource( apiUrl + "/projects/:projectId", {
            projectId: "@projectId"
        }, {
            update: {
                method: "PUT",
                isArray: false
            }
        } );

        let projects = $resource( apiUrl + "/projects", {}, {
            post: {
                method: "POST",
                isArray: false
            }
        } );

        let projectLanes = $resource( apiUrl + "/projects/:projectId/lanes", {
            projectId: "@projectId"
        }, {
            post: {
                method: "POST",
                isArray: false
            },
            delete: {
                method: "DELETE",
                isArray: false
            }
        } );

        let lane = $resource( apiUrl + "/lanes/:laneId", {
            laneId: "@laneId"
        }, {
            update: {
                method: "PUT",
                isArray: false
            },
            delete: {
                method: "DELETE"
            }
        } );

        let laneTasks = $resource( apiUrl + "/lanes/:laneId/tasks", {
            laneId: "@laneId"
        }, {
            post: {
                method: "POST",
                isArray: false
            }
        } );

        let laneTask = $resource( apiUrl + "/lanes/:laneId/tasks/:taskId", {
            laneId: "@laneId",
            taskId: "@taskId"
        }, {
            delete: {
                method: "DELETE"
            }
        } );

        let task = $resource( apiUrl + "/tasks/:taskId", {
            taskId: "@taskId"
        }, {
            update: {
                method: "PUT",
                isArray: false
            },
            delete: {
                method: "DELETE"
            }
        } );

        ///////////////////

        let service = {
            getProject: getProject,
            updateProject: updateProject,
            removeProject: removeProject,
            getProjects: getProjects,
            addProject: addProject,
            getLanesFromProject: getLanesFromProject,
            addLaneToProject: addLaneToProject,
            removeLaneFromProject: removeLaneFromProject,
            getLane: getLane,
            updateLane: updateLane,
            removeLane: removeLane,
            addTaskToLane: addTaskToLane,
            removeTaskFromLane: removeTaskFromLane,
            getTask: getTask,
            updateTask: updateTask,
            removeTask: removeTask
        };

        return service;

        ///////////////////

        function getProject( projectId ) {
            return Security.wrap( () =>
                project.get( {
                    'projectId': projectId
                } )
                .$promise
            );
        }

        function updateProject( projectId, updated ) {
            return Security.wrap( () =>
                project.update( {
                    'projectId': projectId
                }, updated )
                .$promise
            );
        }

        function removeProject( projectId ) {
            return Security.wrap( () =>
                project.delete( {
                    'projectId': projectId
                } )
                .$promise
            );
        }

        function getProjects() {
            return Security.wrap( () =>
                projects.query()
                .$promise
            );
        }

        function addProject( project ) {
            return Security.wrap( () =>
                projects.post( project )
                .$promise
            );
        }

        function getLanesFromProject( projectId ) {
            return Security.wrap( () =>
                projectLanes.query( {
                    'projectId': projectId
                } )
                .$promise
            );
        }

        function addLaneToProject( projectId, lane ) {
            return Security.wrap( () =>
                projectLanes.post( {
                    'projectId': projectId
                }, lane )
                .$promise
            );
        }

        function removeLaneFromProject( projectId, laneId ) {
            return Security.wrap( () =>
                projectLanes.post( {
                    'projectId': projectId,
                    'laneId': laneId
                } )
                .$promise
            );
        }

        function getLane( laneId ) {
            return Security.wrap( () =>
                lane.get( {
                    'laneId': laneId
                } )
                .$promise
            );
        }

        function updateLane( laneId, updated ) {
            return Security.wrap( () =>
                lane.update( {
                    'laneId': laneId
                }, updated )
                .$promise
            );
        }

        function removeLane( laneId ) {
            return Security.wrap( () =>
                lane.delete( {
                    'laneId': laneId
                } )
                .$promise
            );
        }

        function addTaskToLane( laneId, task ) {
            return Security.wrap( () =>
                laneTasks.post( {
                    'laneId': laneId
                }, task )
                .$promise
            );
        }

        function removeTaskFromLane( laneId, taskId ) {
            return Security.wrap( () =>
                laneTask.delete( {
                    'laneId': laneId,
                    'taskId': taskId
                } )
                .$promise
            );
        }

        function getTask( taskId ) {
            return Security.wrap( () =>
                task.get( {
                    'taskId': taskId
                } )
                .$promise
            );
        }

        function updateTask( taskId, updated ) {
            return Security.wrap( () =>
                task.update( {
                    'taskId': taskId
                }, updated )
                .$promise
            );
        }

        function removeTask( taskId ) {
            return Security.wrap( () =>
                task.delete( {
                    'taskId': taskId
                } )
                .$promise
            );
        }
    };
} )();
