( () => {
    'use strict'

    angular.module( 'taskBoardApp.controllers' )
        .controller( 'ProjectPanelController', ProjectPanelController );

    function ProjectPanelController( $location ) {

        let vm = this;

        ///////////////////

        vm.id;
        vm.title;
        vm.secured;

        ///////////////////

        vm.redirect = redirect;

        activate();

        ///////////////////

        function activate() {
            vm.id = vm.project.id;
            vm.title = vm.project.title;
            vm.secured = vm.project.secured;
        }

        function redirect() {
            $location.path( "projects/" + vm.id );
        }

    }
} )();
