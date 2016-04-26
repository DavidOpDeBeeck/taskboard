var directives = angular.module( 'taskBoardApp.directives' , [] );

function convertToNumber () {
    return {
        require : 'ngModel' ,
        link    : function ( scope , element , attrs , ngModel ) {
            ngModel.$parsers.push( function ( val ) {
                return parseInt( val , 10 );
            } );
            ngModel.$formatters.push( function ( val ) {
                return '' + val;
            } );
        }
    };
}

function loadingStatus () {
    return {
        templateUrl      : "directives/loading-status.html" ,
        restrict         : 'EA' ,
        controller       : ProjectController ,
        controllerAs     : 'project' ,
        bindToController : true
    }
}

directives.directive( 'convertToNumber' , convertToNumber );
directives.directive( 'taskboardLoadingStatus' , loadingStatus );