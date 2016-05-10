( () => {
    'use strict'
    angular.module( 'taskBoardApp.controllers' )
        .controller( "LanePanelController", LanePanelController );

    function LanePanelController( API, $uibModal ) {

        let vm = this;

        ///////////////////

        vm.id;
        vm.title;
        vm.completed;
        vm.tasks;

        ///////////////////

        vm.openEditLane = openEditLane;
        vm.openNewTask = openNewTask;
        vm.onTaskRemove = activate;

        vm.taskDropped = taskDropped;
        vm.taskDragStart = taskDragStart;
        vm.taskDragEnd = taskDragEnd;
        vm.taskDragCanceled = taskDragCanceled;

        vm.remove = remove;
        vm.onRemoveCallback;

        activate();

        ///////////////////

        function activate() {
            API.getLane( vm.id )
                .then( lane => {
                    vm.id = lane.id;
                    vm.title = lane.title;
                    vm.completed = lane.completed;
                    vm.tasks = lane.tasks;
                } );
        }

        function openEditLane() {
            let editLaneModal = $uibModal.open( {
                templateUrl: 'app/lane/edit-lane.html',
                controller: 'EditLaneController',
                controllerAs: 'lane',
                resolve: {
                    'id': () => vm.id
                }
            } );
            editLaneModal.result.then( activate );
        }

        function openNewTask() {
            let newTaskModal = $uibModal.open( {
                templateUrl: 'app/task/new-task.html',
                controller: 'NewTaskController',
                controllerAs: 'task',
                resolve: {
                    'laneId': () => vm.id
                }
            } );
            newTaskModal.result.then( activate );
        }

        function remove() {
            API.removeLane( vm.id )
                .then( vm.onRemoveCallback );
        }

        ///////////////////

        function taskDropped( task, index ) {
            API.addTaskToLane( vm.id, task )
                .then( () => vm.tasks.splice( index, 0, task ) );
            return true;
        }

        function taskDragStart( task, index ) {
            API.removeTaskFromLane( vm.id, task.id );
        }

        function taskDragEnd( task, index ) {
            vm.tasks.splice( index, 1 );
        }

        function taskDragCanceled( task, index ) {
            API.addTaskToLane( vm.id, task )
                .then( () => vm.tasks.splice( index, 0, task ) );
        }

    }
} )();
