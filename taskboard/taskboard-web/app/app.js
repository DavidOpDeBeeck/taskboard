(function() {
  'use strict'
  angular.module( 'taskBoardApp' , [
      'dndLists' ,
      'ngCookies',
      'angular-nicescroll',
      'taskBoardApp.config' ,
      'taskBoardApp.routes' ,
      'taskBoardApp.controllers' ,
      'taskBoardApp.services' ,
      'taskBoardApp.directives'
  ] );
})();
