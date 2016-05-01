(function() {
  'use strict'

  angular.module( 'taskBoardApp.controllers' )
    .controller('ProjectPanelController', ProjectPanelController);

  function ProjectPanelController ( API , $location ) {

      var vm = this;

      ///////////////////

      vm.id;
      vm.title;
      vm.protected;

      ///////////////////

      vm.onRemove;
      vm.redirect = redirect;

      activate();

      ///////////////////

      function activate () {
        API.getProject(vm.id).then((project) => {
          vm.title = project.title;
          vm.protected = project.protected;
        });
      }

      function redirect () {
        $location.path( "projects/" + vm.id );
      }

  }
})();
