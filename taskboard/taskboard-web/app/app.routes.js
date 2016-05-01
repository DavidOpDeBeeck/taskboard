(function() {
  'use strict'
  angular.module( 'taskBoardApp.routes' , ['ngRoute'] )
    .config( function ( $routeProvider ) {
      $routeProvider.when( '/projects' , {
          templateUrl  : 'app/projects/projects.html' ,
          controller   : 'ProjectsController' ,
          controllerAs : 'projects'
      } ).when( '/projects/:id' , {
          templateUrl  : 'app/project/project.html' ,
          controller   : 'ProjectController' ,
          controllerAs : 'project'
      } ).otherwise( {
          redirectTo : '/projects'
      } )
    } );
})();
