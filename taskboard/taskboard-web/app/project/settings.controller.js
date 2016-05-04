(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("SettingsController", SettingsController);

  function SettingsController( API, $routeParams, $location, $uibModalInstance ) {

    let vm = this;

    ///////////////////

    vm.title;
    vm.password;
    vm.secured;

    ///////////////////

    vm.save   = save;
    vm.remove = remove;
    vm.close  = close;

    activate();

    ///////////////////

    function activate () {
      API.getProject($routeParams.id).then((project) => {
        vm.title = project.title;
        vm.secured = project.secured;
      });
    }

    function save() {
      API.updateProject($routeParams.id, {
        'title'   : vm.title,
        'secured' : vm.secured,
        'password': vm.password
      }).then($uibModalInstance.close);
    }

    function remove() {
      API.removeProject($routeParams.id).then(() => {
          $uibModalInstance.close();
          $location.path("/projects");
      });
    }

    function close() {
      $uibModalInstance.dismiss('cancel');
    }
  }
})();