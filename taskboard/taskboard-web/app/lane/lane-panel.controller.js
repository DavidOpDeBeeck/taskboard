(function () {
    'use strict'
    angular.module( 'taskBoardApp.controllers' ).controller( "LanePanelController" , LanePanelController );

    function LanePanelController ( API , $routeParams , $location , $uibModal , $scope ) {

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

        function drop (item) {
          API.addTaskToLane( vm.id , item ).then( activate );
          return true;
        }

        $scope.$watchCollection(() => { return vm.tasks }, (newVal, oldVal) => {
            if(newVal !== oldVal) {
              newVal = newVal[0];
              oldVal = oldVal[0];
              if (oldVal != undefined && newVal == undefined && vm.tasks.indexOf(oldVal) < 0)
                API.removeTaskFromLane( vm.id , oldVal.id ).then( activate );
            }
        });
    }
})();
