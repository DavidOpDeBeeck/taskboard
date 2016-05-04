(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("projectPanel", projectPanel);

  function projectPanel() {
    let directive = {
      bindToController  : {
         id         : '=projectId'
      },
      restrict          : 'E',
      replace           : 'true',
      controller        : 'ProjectPanelController',
      controllerAs      : 'project',
      templateUrl       : 'app/projects/project-panel.html'
    };
    return directive;
  }

})();
