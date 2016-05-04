(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("NewLaneController", NewLaneController);

  function NewLaneController( API , $routeParams , $uibModalInstance ) {

    let vm = this;

    ///////////////////

    vm.title     = "";
    vm.sequence  = 0;
    vm.completed = false;
    vm.lanes     = [];

    ///////////////////

    vm.create = create;
    vm.close  = close;

    activate();

    ///////////////////

    function activate () {
        API.getProject($routeParams.id).then((project) => vm.lanes = project.lanes);
    }

    function create() {
      API.addLaneToProject($routeParams.id, {
        'title'     : vm.title,
        'sequence'  : vm.sequence + 1,
        'completed' : vm.completed
      }).then($uibModalInstance.close);
    }

    function close() {
      $uibModalInstance.dismiss('cancel');
    }
  }
})();
