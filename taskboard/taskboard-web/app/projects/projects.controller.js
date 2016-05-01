(function() {
  'use strict'

  angular.module( 'taskBoardApp.controllers' )
    .controller('ProjectsController', ProjectsController);

  function ProjectsController ( API ) {

      var vm = this;

      ///////////////////

      vm.project  = {
        title   : "",
        password: ""
      };
      vm.protected = false;
      vm.projects  = [];

      ///////////////////

      vm.create = create;
      vm.onProjectRemove = activate;
      vm.toggleProtected = toggleProtected;

      activate();

      ///////////////////

      function activate () {
        API.getProjects().then((projects) => vm.projects = projects);
      }

      function create () {
        API.addProject(vm.project).then(activate);
      }

      function toggleProtected() {
        vm.protected = !vm.protected;
      }
  }
})();
