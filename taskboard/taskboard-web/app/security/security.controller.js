(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("SecurityController", SecurityController);

  function SecurityController( API , $uibModalInstance , projectId ) {

    let vm = this;

    ///////////////////

    vm.title;
    vm.password;

    ///////////////////

    vm.submit = submit;
    vm.close  = close;

    activate();

    ///////////////////

    function activate () {
      API.getProject(projectId).then((project) => vm.title = project.title );
    }

    function submit() {
      $uibModalInstance.close({ 'password' : vm.password });
    }

    function close() {
      $uibModalInstance.dismiss('cancel');
    }
  }
})();
