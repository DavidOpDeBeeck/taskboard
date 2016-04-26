var controllers = angular.module( 'taskBoardApp.controllers' , [] );

/**
 * Controller used in a projects overview
 */
function ProjectsController ( API ) {

    var vm = this;

    /**
     * public variables
     */

    vm.project = {
        title : ""
    };
    vm.projects = [];

    /**
     * public actions
     */

    vm.create = create;
    vm.remove = remove;

    init();

    /**
     * private actions
     */

    function init () {
        API.Projects.query( {} , function ( projects ) {
            vm.projects = projects;
        } )
    }

    function create () {
        API.Projects.post( vm.project , function () {
            init();
        } );
    }

    function remove ( id ) {
        API.Project.delete( { projectId : id } , function () {
            init();
        } );
    }
}

/**
 * Controller used in a project overview
 */
function ProjectController ( API , $routeParams , $location , $timeout ) {

    var vm = this;

    /**
     * public variables
     */

    vm.loading = false;
    vm.filter = {};

    vm.title = {};
    vm.lanes = [];

    vm.lowest = undefined;
    vm.highest = undefined;

    vm.formLane = {
        lane      : undefined ,
        title     : "" ,
        sequence  : 0 ,
        completed : false
    };

    vm.formTask = {
        lane        : undefined ,
        task        : undefined ,
        title       : "" ,
        description : "" ,
        assignee    : "" ,
    };

    vm.formSettings = {
        title : ""
    };

    vm.laneToEdit = undefined;
    vm.taskToEdit = undefined;

    init();

    /**
     * public actions
     */

    vm.editLane = editLane;
    vm.initEditLane = initEditLane;
    vm.createLane = createLane;
    vm.removeLane = removeLane;
    vm.moveLaneToLeft = moveLaneToLeft;
    vm.moveLaneToRight = moveLaneToRight;

    vm.createTask = createTask;
    vm.initCreateTask = initCreateTask;
    vm.editTask = editTask;
    vm.initEditTask = initEditTask;
    vm.removeTask = removeTask;

    vm.remove = remove;
    vm.saveSettings = saveSettings;

    vm.dragStart = dragStart;
    vm.dragEnd = dragEnd;

    vm.filterTask = filterTask;

    /**
     * private actions
     */

    // Forms

    function clearLaneForm () {
        vm.formLane = {
            lane      : undefined ,
            title     : "" ,
            sequence  : 0 ,
            completed : false
        };
    }

    function clearTaskForm () {
        vm.formTask = {
            lane        : undefined ,
            task        : undefined ,
            title       : "" ,
            description : "" ,
            assignee    : "" ,
        };
    }

    // Lanes

    function createLane () {
        vm.formLane.sequence += 1;
        API.ProjectLanes.post( { projectId : $routeParams.id } , vm.formLane , function () {
            init();
            clearLaneForm();
        } );
    }

    function editLane () {
        vm.formLane.sequence += 1;
        API.Lane.update( {
            projectId : $routeParams.id ,
            laneId    : vm.formLane.lane.id
        } , vm.formLane , function () {
            init();
            clearLaneForm();
        } );
    }

    function initEditLane ( lane ) {
        var i = vm.lanes.indexOf( lane );
        vm.formLane = {
            lane      : lane ,
            title     : lane.title ,
            sequence  : i - 1 < 0 ? "0" : vm.lanes[ i - 1 ].sequence ,
            completed : lane.completed
        };
    }

    function moveLaneToLeft ( lane ) {
        switchLanes( lane , vm.lanes[ vm.lanes.indexOf( lane ) - 1 ] )
    }

    function moveLaneToRight ( lane ) {
        switchLanes( lane , vm.lanes[ vm.lanes.indexOf( lane ) + 1 ] )
    }

    function switchLanes ( lane1 , lane2 ) {
        var temp = lane1.sequence;

        lane1.sequence = lane2.sequence;
        lane2.sequence = temp;

        API.Lane.update( {
            projectId : $routeParams.id ,
            laneId    : lane1.id
        } , lane1 );

        API.Lane.update( {
            projectId : $routeParams.id ,
            laneId    : lane2.id
        } , lane2 );

        var i , j;

        i = vm.lanes.indexOf( lane1 );
        j = vm.lanes.indexOf( lane2 );

        vm.lanes[ i ] = lane2;
        vm.lanes[ j ] = lane1;
    }

    function removeLane ( id ) {
        API.Lane.delete( {
            projectId : $routeParams.id ,
            laneId    : (id == undefined) ? vm.formLane.lane.id : id
        } , function () {
            init();
            clearLaneForm();
        } );
    }

    // Task

    function initCreateTask ( lane ) {
        vm.formTask.lane = lane;
    }

    function createTask () {
        API.LaneTasks.post( {
            laneId : vm.formTask.lane.id
        } , vm.formTask , function () {
            init();
            clearTaskForm();
        } );
    }

    function initEditTask ( lane , task ) {
        vm.formTask = {
            lane        : lane ,
            task        : task ,
            title       : task.title ,
            description : task.description ,
            assignee    : task.assignee ,
        };
    }

    function editTask () {
        vm.loading = true;
        API.Task.update( {
            taskId : vm.formTask.task.id
        } , vm.formTask , function () {
            var i = vm.lanes.indexOf( vm.formTask.lane );
            var j = vm.formTask.lane.tasks.indexOf( vm.formTask.task );

            vm.lanes[ i ].tasks[ j ].title = vm.formTask.title;
            vm.lanes[ i ].tasks[ j ].description = vm.formTask.description;
            vm.lanes[ i ].tasks[ j ].assignee = vm.formTask.assignee;

            clearTaskForm();
            vm.loading = false;
        } );
    }

    function removeTask ( lane , task , index ) {
        var i = vm.lanes.indexOf( lane );
        vm.lanes[ i ].tasks.splice( index , 1 );
        API.Task.delete( {
            taskId : task.id
        } );
    }

    // Project

    function remove () {
        API.Project.delete( { projectId : $routeParams.id } , function () {
            $timeout( function () { $location.path( "/projects" ); } , 500 );
        } );
    }

    // Settings

    function saveSettings () {
        API.Project.update( { projectId : $routeParams.id } , vm.formSettings , function () {
            init();
        } );
    }

    function init () {
        API.Project.get( { projectId : $routeParams.id } , function ( project ) {
            vm.title = project.title;
            vm.lanes = project.lanes;

            vm.formSettings.title = project.title;

            vm.lowest = Math.min.apply( Math , vm.lanes.map( function ( l ) {return l.sequence;} ) );
            vm.highest = Math.max.apply( Math , vm.lanes.map( function ( l ) {return l.sequence;} ) );

            vm.lanes.sort( function ( a , b ) {
                return parseInt( a.sequence ) - parseInt( b.sequence );
            } );

            vm.lanes.forEach( function ( lane , index ) {
                API.Lane.get( {
                    laneId : lane.id
                } , function ( updatedLane ) {
                    vm.lanes[ index ].tasks = updatedLane.tasks;
                } );
            } );
        } );
    }

    // Drop

    var previousLane;

    function dragStart ( lane ) {
        previousLane = lane;
    }

    function dragEnd ( task , lane ) {
        if ( lane.id != previousLane.id ) {
            vm.loading = true;
            API.LaneTask.delete( {
                laneId : previousLane.id ,
                taskId : task.id
            } , function () {
                API.LaneTasks.post( {
                    laneId : lane.id
                } , task , function () {
                    vm.loading = false;
                } );
            } );
        }
        return task;
    }

    // filter

    function filterTask ( item ) {
        var text = (vm.filter.text == undefined) ? vm.filter.text : vm.filter.text.toLowerCase();
        var assignee = (vm.filter.assignee == undefined) ? vm.filter.assignee : vm.filter.assignee.toLowerCase();

        var itemTitle = item.title.toLowerCase();
        var itemDescription = item.description.toLowerCase();
        var itemAssignee = item.assignee.toLowerCase();

        if ( text == undefined && assignee == undefined )
            return true;

        if ( itemTitle.indexOf( text ) != -1 || itemDescription.indexOf( text ) != -1 ) {
            if ( assignee != undefined && itemAssignee.indexOf( assignee ) != -1 )
                return true;
            else if ( assignee === undefined )
                return true;
        }

        return false;
    }
}

controllers.controller( 'ProjectsController' , ProjectsController );
controllers.controller( 'ProjectController' , ProjectController );
