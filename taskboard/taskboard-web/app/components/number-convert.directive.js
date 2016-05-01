(function() {
  'use strict'
  angular.module( 'taskBoardApp.directives')
    .directive("convertToNumber", convertToNumber);

  function convertToNumber() {

    var directive = {
      link        : link,
      require     : 'ngModel'
    };

    return directive;
    function link(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function ( val ) {
          return parseInt( val , 10 );
      } );
      ngModel.$formatters.push(function ( val ) {
          return '' + val;
      } );
    }
  }

})();
