describe( "Taskboard Application" , () => {

    let apiUrl , $controller , $httpBackend , $routeParams;

    beforeEach( angular.mock.module( "taskBoardApp" ) );

    beforeEach( () => {
        angular.mock.inject( ( $injector ) => {

            apiUrl = mockAPI = $injector.get( "apiUrl" );
            $httpBackend = $injector.get( "$httpBackend" );
            $routeParams = $injector.get( "$routeParams" );
            $controller = $injector.get( "$controller" );

            setDefaultRouteBehaviour();

        } )
    } );

    // Data

    let projects;

    beforeEach( () => {
        angular.mock.inject( ( $injector ) => {

            projects = [
                {
                    id    : "eb701fa8-9b08-4aac-8afe-73da7e1ff1cd" ,
                    title : "Project #1" ,
                    lanes : []
                } ,
                {
                    id    : "1973e2e7-e904-436d-83ec-27fdb6027da8" ,
                    title : "Project #2" ,
                    lanes : []
                }
            ];

        } )
    } );

    describe( "ProjectsController" , () => {

        let controller;

        beforeEach( () => {
            angular.mock.inject( ( $injector ) => {

                controller = $controller( "ProjectsController" );

                $httpBackend.when( "GET" , apiUrl + "/projects" )
                    .respond( projects );

            } )
        } );

        it( "should get all the projects" , inject( () => {
            $httpBackend.flush();

            let result = controller.projects;

            expect( result.length ).toEqual( 2 );
            expect( result[ 0 ].title ).toEqual( "Project #1" );
            expect( result[ 1 ].title ).toEqual( "Project #2" );
        } ) );
        it( "should create a new project when [ create ] is called" , inject( () => {
            $httpBackend.expectPOST( apiUrl + "/projects" )
                .respond( ( method , url , data ) => {
                    projects.push( angular.fromJson( data ) );
                    return success();
                } );

            controller.project.title = "Project #3";

            controller.create();

            $httpBackend.flush();

            let result = controller.projects;

            expect( result.length ).toEqual( 3 );
            expect( result[ 0 ].title ).toEqual( "Project #1" );
            expect( result[ 1 ].title ).toEqual( "Project #2" );
            expect( result[ 2 ].title ).toEqual( "Project #3" );
        } ) );
        it( "should remove a project when [ remove ] is called" , inject( () => {
            $httpBackend.expectDELETE( new RegExp( apiUrl + "/projects/(.*)" ) )
                .respond( ( method , url ) => {
                    let id = new RegExp( apiUrl + "/projects/(.*)" ).exec( url )[ 1 ];
                    let i = findWithAttr( projects , "id" , id );
                    projects.splice( i , 1 );
                    return success();
                } );

            let projectToRemove = projects[ 0 ];

            controller.remove( projectToRemove.id );

            $httpBackend.flush();

            let result = controller.projects;

            expect( result.length ).toEqual( 1 );
            expect( result[ 0 ].title ).toEqual( "Project #2" );
        } ) );
    } );


    //-------------------------------------//
    // region Route
    //-------------------------------------//

    const pages = [
        "pages/project.html" ,
        "pages/projects.html" ,
    ];

    const routes = [
        "/projects" ,
        new RegExp( "/projects/.*" )
    ];

    function setDefaultRouteBehaviour () {
        pages.forEach( page => $httpBackend.when( "GET" , page ).respond( {} ) );
        routes.forEach( route => $httpBackend.when( "GET" , route ).respond( {} ) );
    }

    //-------------------------------------//
    // endregion
    //-------------------------------------//

    //-------------------------------------//
    // region Utilities
    //-------------------------------------//

    function success ( obj ) {
        return [
            200 ,
            (obj == undefined) ? {} : obj ,
            {}
        ];
    }

    function findWithAttr ( array , attr , value ) {
        for ( var i = 0 ; i < array.length ; i += 1 ) {
            if ( array[ i ][ attr ] === value ) {
                return i;
            }
        }
    }

    //-------------------------------------//
    // endregion
    //-------------------------------------//

} );