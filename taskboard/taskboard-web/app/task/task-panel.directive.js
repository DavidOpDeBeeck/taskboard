(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("taskPanel", taskPanel);

  function taskPanel() {
    var directive = {
      bindToController : {
         id      : '=taskId',
         laneId  : '=laneId',
         onRemove: '&onRemove'
      },
      restrict         : 'EA' ,
      replace          : 'true',
      controller       : 'TaskPanelController',
      controllerAs     : 'task',
      templateUrl      : 'app/task/task-panel.html'
    };
    return directive;
  }
})();
