(function() {
  'use strict'
  angular.module('taskBoardApp.config')
    .config(($compileProvider) => {
      $compileProvider.debugInfoEnabled(false);
    });
})();
