(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("SecurityController", SecurityController);

  function SecurityController( API , $uibModalInstance ) {

    let vm = this;

    ///////////////////

    vm.password;

    ///////////////////

    vm.submit = submit;
    vm.close  = close;

    ///////////////////

    function submit() {
      $uibModalInstance.close({ 'password' : vm.password });
    }

    function close() {
      $uibModalInstance.dismiss('cancel');
    }
  }
})();
