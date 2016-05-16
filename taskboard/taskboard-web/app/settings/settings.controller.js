( () => {
    'use strict'
    angular.module( 'taskBoardApp.controllers' )
        .controller( "SettingsController", SettingsController );

    function SettingsController( API, $routeParams, $location, $uibModalInstance ) {

        let vm = this;

        ///////////////////

        vm.title;
        vm.password;
        vm.secured;

        ///////////////////

        vm.save = save;
        vm.remove = remove;
        vm.close = close;
        vm.toggleSecured = toggleSecured;

        activate();

        ///////////////////

        function activate() {
            console.log( "ze" );
            API.getProject( $routeParams.id )
                .then( project => {
                    vm.title = project.title;
                    vm.secured = project.secured;
                } );
        }

        function save() {
            API.updateProject( $routeParams.id, {
                    'title': vm.title,
                    'secured': vm.secured,
                    'password': vm.password && vm.password.length > 0 ? vm.password : null
                } )
                .then( $uibModalInstance.close );
        }

        function remove() {
            API.removeProject( $routeParams.id )
                .then( () => {
                    $uibModalInstance.dismiss();
                    $location.path( "/projects" );
                } );
        }

        function close() {
            $uibModalInstance.dismiss( 'cancel' );
        }

        function toggleSecured() {
            vm.secured = !vm.secured;
        }
    }
} )();
