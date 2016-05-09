(function() {
  'use strict'
  angular.module('taskBoardApp.config')
    .config(($httpProvider) => {
      $httpProvider.defaults.withCredentials  = true
    });
})();
