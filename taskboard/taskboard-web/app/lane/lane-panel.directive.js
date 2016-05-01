(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("lanePanel", lanePanel);

  function lanePanel() {
    var directive = {
      bindToController : {
         id       : '=laneId',
         onRemove : '&onRemove'
      },
      restrict         : 'EA' ,
      replace          : 'true',
      controller       : 'LanePanelController',
      controllerAs     : 'lane',
      templateUrl      : 'app/lane/lane-panel.html'
    };
    return directive;
  }
})();
