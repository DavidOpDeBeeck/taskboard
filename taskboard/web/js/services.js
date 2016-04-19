var services = angular.module( 'taskBoardApp.services' , [] );

function api ( $resource , apiUrl ) {
    return {
        Projects     : $resource( apiUrl + "/projects" , {} , {
            post : {
                method  : "POST" ,
                isArray : false
            }
        } ) ,
        Project      : $resource( apiUrl + "/projects/:projectId" , { projectId : "@projectId" } , {
            update : {
                method  : "PUT" ,
                isArray : false
            }
        } ) ,
        ProjectLanes : $resource( apiUrl + "/projects/:projectId/lanes" , { projectId : "@projectId" } , {
            post : {
                method  : "POST" ,
                isArray : false
            }
        } ) ,
        Lane         : $resource( apiUrl + "/lanes/:laneId" , {
            laneId : "@laneId"
        } , {
            update : {
                method  : "PUT" ,
                isArray : false
            } ,
            delete : {
                method : "DELETE"
            }
        } ) ,
        LaneTasks    : $resource( apiUrl + "/lanes/:laneId/tasks" , {
            laneId : "@laneId"
        } , {
            post : {
                method  : "POST" ,
                isArray : false
            }
        } ) ,
        LaneTask     : $resource( apiUrl + "/lanes/:laneId/tasks/:taskId" , {
            laneId : "@laneId" ,
            taskId : "@taskId"
        } , {
            delete : {
                method : "DELETE"
            }
        } ) ,
        Task         : $resource( apiUrl + "/tasks/:taskId" , {
            taskId : "@taskId"
        } , {
            update : {
                method  : "PUT" ,
                isArray : false
            } ,
            delete : {
                method : "DELETE"
            }
        } )
    }
}

services.factory( "API" , api );