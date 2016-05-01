(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("LanePanelController", LanePanelController);

  function LanePanelController( API, $routeParams, $location, $uibModal ) {

    var vm = this;

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

    vm.dragEnd = dragEnd;
    vm.dragStart = dragStart;

    activate();

    ///////////////////

    function activate () {
        API.getLane(vm.id).then((lane) => {
            vm.id = lane.id;
            vm.title = lane.title;
            vm.completed = lane.completed;
            vm.tasks = lane.tasks;
        });
    }

    function openEditLane () {
      var editLaneModal = $uibModal.open({
        templateUrl  : 'app/lane/edit-lane.html',
        controller   : 'EditLaneController',
        controllerAs : 'lane',
        resolve      : {
          'id'  : () => { return vm.id; }
        }
      });
      editLaneModal.result.then(activate);
    }

    function openNewTask () {
      var taskAddModal = $uibModal.open({
        templateUrl  : 'app/task/new-task.html',
        controller   : 'NewTaskController',
        controllerAs : 'task',
        resolve      : {
          'laneId'  : function() { return vm.id; }
        }
      });
      taskAddModal.result.then(activate);
    }

    function remove () {
      API.removeLane($routeParams.id, vm.id).then(vm.onRemove);
    }

    function dragEnd ( item ) {
      API.addTaskToLane(vm.id, item).then(activate);
    }

    function dragStart ( task ) {
      var index = vm.tasks.indexOf(task);
      if (index => 0)
        vm.tasks.splice(index, 1);
      API.removeTaskFromLane(vm.id, task.id).then(activate);
    }
  }
})();
