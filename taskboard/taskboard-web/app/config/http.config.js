(function() {
  'use strict'
  angular.module('taskBoardApp.config')
  .config(httpConfig);

  function httpConfig ( $httpProvider ) {
    $httpProvider.defaults.withCredentials = true;
  }
})();
