(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("projectPanel", projectPanel);

  function projectPanel() {
    var directive = {
      bindToController  : {
         id         : '=projectId',
         onRemove   : '&onRemove',
      },
      restrict          : 'EA' ,
      replace           : 'true',
      controller        : 'ProjectPanelController',
      controllerAs      : 'project',
      templateUrl       : 'app/projects/project-panel.html'
    };
    return directive;
  }

})();
