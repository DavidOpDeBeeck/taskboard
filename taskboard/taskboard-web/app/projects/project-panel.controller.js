(function() {
  'use strict'

  angular.module( 'taskBoardApp.controllers' )
    .controller('ProjectPanelController', ProjectPanelController);

  function ProjectPanelController ( API , $location ) {

      var vm = this;

      ///////////////////

      vm.id;
      vm.title;

      ///////////////////

      vm.onRemove;

      vm.remove = remove;
      vm.redirect = redirect;

      activate();

      ///////////////////

      function activate () {
        API.getProject(vm.id).then((project) => vm.title = project.title);
      }

      function remove () {
        API.removeProject(vm.id).then(vm.onRemove);
      }

      function redirect () {
        $location.path( "projects/" + vm.id );
      }

  }
})();
