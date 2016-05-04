(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("lanePanel", lanePanel);

  function lanePanel() {
    let directive = {
      bindToController : {
         id               : '=laneId',
         onRemoveCallback : '&onRemove'
      },
      restrict         : 'E' ,
      replace          : 'true',
      controller       : 'LanePanelController',
      controllerAs     : 'lane',
      templateUrl      : 'app/lane/lane-panel.html'
    };
    return directive;
  }
})();
