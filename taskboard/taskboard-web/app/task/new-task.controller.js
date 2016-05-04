(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("NewTaskController", NewTaskController);

  function NewTaskController( API , $uibModalInstance , laneId ) {

    let vm = this;

    ///////////////////

    vm.title        = "";
    vm.description  = "";
    vm.assignee     = "";

    ///////////////////

    vm.create = create;
    vm.close  = close;

    ///////////////////

    function create() {
      API.addTaskToLane(laneId, {
        'title'       : vm.title,
        'description' : vm.description,
        'assignee'    : vm.assignee
      }).then($uibModalInstance.close);
    }

    function close() {
      $uibModalInstance.dismiss('cancel');
    }
  }
})();
