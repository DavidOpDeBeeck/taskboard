( () => {
    'use strict'
    angular.module( 'taskBoardApp.directives' )
        .directive( "convertToNumber", convertToNumber );

    function convertToNumber() {
        let directive = {
            link: link,
            require: 'ngModel'
        };

        return directive;

        function link( scope, element, attrs, ngModel ) {
            ngModel.$parsers.push( val => parseInt( val, 10 ) );
            ngModel.$formatters.push( val => '' + val );
        }
    }

} )();
