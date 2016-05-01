(function() {
  'use strict'

  angular.module( 'taskBoardApp.controllers' )
    .controller('ProjectsController', ProjectsController);

  function ProjectsController ( API ) {

      var vm = this;

      ///////////////////

      vm.project  = { title : "" };
      vm.projects = [];

      ///////////////////

      vm.create   = create;
      vm.onProjectRemove = activate;

      activate();

      ///////////////////

      function activate () {
        API.getProjects().then((projects) => vm.projects = projects);
      }

      function create () {
        API.addProject(vm.project).then(activate);
      }
  }
})();
