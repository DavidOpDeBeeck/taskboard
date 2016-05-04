(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("TaskPanelController", TaskPanelController);

  function TaskPanelController( API, $uibModal ) {

    let vm = this;

    ///////////////////

    vm.id;
    vm.title;
    vm.assignee;
    vm.description;
    vm.completed;

    vm.laneId           = undefined;

    ///////////////////

    vm.openEditTask     = openEditTask;
    vm.remove           = remove;
    vm.onRemoveCallback = undefined;

    activate();

    ///////////////////

    function activate () {
        API.getTask(vm.id).then((task) => {
            vm.title        = task.title;
            vm.description  = task.description;
            vm.assignee     = task.assignee;
        });
        API.getLane(vm.laneId).then((lane) => vm.completed = lane.completed );
    }

    function openEditTask () {
      let editTaskModal = $uibModal.open({
        templateUrl  : 'app/task/edit-task.html',
        controller   : 'EditTaskController',
        controllerAs : 'task',
        resolve      : { 'id'  : () => { return vm.id; } }
      });
      editTaskModal.result.then(activate);
    }

    function remove () {
      API.removeTask(vm.id).then(vm.onRemoveCallback);
    }
  }
})();
