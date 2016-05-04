(function() {
  'use strict'

  angular.module( 'taskBoardApp.controllers' )
    .controller('ProjectPanelController', ProjectPanelController);

  function ProjectPanelController ( API , $location ) {

      let vm = this;

      ///////////////////

      vm.id;
      vm.title;
      vm.secured;

      ///////////////////

      vm.onRemove;
      vm.redirect = redirect;

      activate();

      ///////////////////

      function activate () {
        API.getProject(vm.id).then((project) => {
          vm.title = project.title;
          vm.secured = project.secured;
        });
      }

      function redirect () {
        $location.path( "projects/" + vm.id );
      }

  }
})();
