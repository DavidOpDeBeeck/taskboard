(function () {
    'use strict'
    angular.module( 'taskBoardApp.controllers' ).controller( "LanePanelController" , LanePanelController );

    function LanePanelController ( API , $routeParams , $location , $uibModal ) {

        let vm = this;

        ///////////////////

        vm.id;
        vm.title;
        vm.completed;
        vm.tasks = [];

        ///////////////////

        vm.onRemove;
        vm.remove = remove;

        vm.openEditLane = openEditLane;
        vm.openNewTask = openNewTask;
        vm.onTaskRemove = activate;

        vm.drop = drop;
        vm.dragStart = dragStart;
        vm.canceled = canceled;

        activate();

        ///////////////////

        function activate () {
            API.getLane( vm.id ).then( ( lane ) => {
                vm.id = lane.id;
                vm.title = lane.title;
                vm.completed = lane.completed;
                vm.tasks = lane.tasks;
            });
        }

        function openEditLane () {
            let editLaneModal = $uibModal.open( {
                templateUrl  : 'app/lane/edit-lane.html' ,
                controller   : 'EditLaneController' ,
                controllerAs : 'lane' ,
                resolve      : {
                    'id' : () => { return vm.id; }
                }
            } );
            editLaneModal.result.then( activate );
        }

        function openNewTask () {
            let taskAddModal = $uibModal.open( {
                templateUrl  : 'app/task/new-task.html' ,
                controller   : 'NewTaskController' ,
                controllerAs : 'task' ,
                resolve      : {
                    'laneId' : () => { return vm.id; }
                }
            } );
            taskAddModal.result.then( activate );
        }

        function remove () {
            API.removeLane( vm.id ).then( vm.onRemove );
        }

        ///////////////////

        function drop (task) {
          API.addTaskToLane( vm.id , task ).then(activate);
        }

        function dragStart (index,task) {
          API.removeTaskFromLane( vm.id , task.id ).then(activate);
        }

        function canceled (task) {
          console.log(task);
          //API.addTaskToLane( vm.id , task ).then(activate);
        }

    }
})();
