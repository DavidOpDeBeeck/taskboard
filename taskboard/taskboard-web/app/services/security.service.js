( () => {
    'use strict'
    angular.module( 'taskBoardApp.services' )
        .factory( "Security", securityService )

    function securityService( $resource, apiUrl, $uibModal, $q, $cookies, $routeParams ) {

        let Auth = $resource( apiUrl + "/authenticate", {}, {
            post: {
                method: "POST",
                isArray: false
            }
        } );

        ///////////////////

        let service = {
            wrap: wrap
        };

        return service;

        ///////////////////

        function wrap( request ) {
            return request()
                .then( response => {
                    if ( response.code && response.code == 401 ) {
                        return authenticate( $routeParams.id , response.title )
                            .then( request )
                            .then( res => res );
                    }
                    return response;
                } );
        }

        function authenticate( projectId , projectTitle) {
            let securityModal;
            let endpoint = ( id, password ) => Auth.post( {
                    'projectId': id,
                    'password': password
                } )
                .$promise;
            let promise = ( resolve, reject ) => {
                securityModal = $uibModal.open( {
                    templateUrl: 'app/security/security.html',
                    controller: 'SecurityController',
                    controllerAs: 'security',
                    backdrop: 'static',
                    resolve: {
                        'projectTitle' : () => projectTitle
                    }
                } );
                securityModal.result
                    .then( result => {
                        endpoint( projectId, result.password )
                            .then( response => {
                                if ( response.success ) {
                                    $cookies.put( projectId, response.token );
                                    resolve();
                                } else {
                                    securityModal.close();
                                    return promise( resolve, reject );
                                }
                            } );
                    } );
            }
            return $q( promise );
        }
    };
} )();
