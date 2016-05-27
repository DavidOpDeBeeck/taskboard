( () => {
    'use strict'
    angular.module( 'taskBoardApp.controllers' )
        .controller( "SecurityController", SecurityController );

    function SecurityController( $uibModalInstance, $location , projectTitle ) {

        let vm = this;

        ///////////////////

        vm.password = "";
        vm.projectTitle = projectTitle;

        ///////////////////

        vm.submit = submit;
        vm.close = close;

        ///////////////////

        function submit() {
            $uibModalInstance.close( {
                'password': vm.password
            } );
        }

        function close() {
            $location.path( "/projects" );
            $uibModalInstance.dismiss( 'cancel' );
        }
    }
} )();
