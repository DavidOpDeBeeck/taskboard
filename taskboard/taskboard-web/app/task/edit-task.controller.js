(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("EditTaskController", EditTaskController);

  function EditTaskController( API , $routeParams , $uibModalInstance , id ) {

    let vm = this;

    ///////////////////

    vm.title        = "";
    vm.description  = "";
    vm.assignee     = "";

    ///////////////////

    vm.save = save;
    vm.close  = close;

    activate();

    ///////////////////

    function activate() {
      API.getTask(id).then((task) => {
        vm.title        = task.title;
        vm.description  = task.description;
        vm.assignee     = task.assignee;
      });
    }

    function save() {
      API.updateTask(id, {
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
