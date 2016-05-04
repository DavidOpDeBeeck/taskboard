(function() {
  'use strict'

  angular.module( 'taskBoardApp.controllers' )
    .controller('ProjectsController', ProjectsController);

  function ProjectsController ( API ) {

      let vm = this;

      ///////////////////

      vm.title;
      vm.password;
      vm.secured = false;

      vm.projects  = [];

      ///////////////////

      vm.createProject    = createProject;
      vm.toggleSecured    = toggleSecured;

      activate();

      ///////////////////

      function activate () {
        API.getProjects().then((projects) => vm.projects = projects);
      }

      function clearForm () {
        vm.title = "";
        vm.password = "";
        vm.secured = false;
      }

      function createProject () {
        API.addProject({
          'title'   : vm.title,
          'secured' : vm.secured,
          'password': vm.password
        }).then(() => {
          clearForm();
          activate();
        });
      }

      function toggleSecured () {
        vm.secured = !vm.secured;
      }
  }
})();
