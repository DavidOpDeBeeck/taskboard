/**
 * Angular Application
 */

var taskBoardApp = angular.module( 'taskBoardApp' , [
    'ngRoute' ,
    'ngResource' ,
    'dndLists' ,
    'taskBoardApp.config' ,
    'taskBoardApp.controllers' ,
    'taskBoardApp.services' ,
    'taskBoardApp.directives'
] );

/**
 * Routing
 */

taskBoardApp.config( function ( $routeProvider ) {
    $routeProvider.when( '/projects' , {
        templateUrl  : 'views/projects.html' ,
        controller   : 'ProjectsController' ,
        controllerAs : 'projects'
    } ).when( '/projects/:id' , {
        templateUrl  : 'views/project.html' ,
        controller   : 'ProjectController' ,
        controllerAs : 'project'
    } ).otherwise( {
        redirectTo : '/projects'
    } )
} );