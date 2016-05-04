(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("taskPanel", taskPanel);

  function taskPanel() {
    let directive = {
      bindToController : {
         id               : '=taskId',
         laneId           : '=laneId',
         onRemoveCallback : '&onRemove'
      },
      restrict         : 'E' ,
      replace          : 'true',
      controller       : 'TaskPanelController',
      controllerAs     : 'task',
      templateUrl      : 'app/task/task-panel.html'
    };
    return directive;
  }
})();
