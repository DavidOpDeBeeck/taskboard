(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("ProjectController", ProjectController);

  function ProjectController( API , $routeParams , $location , $timeout , $uibModal , Security ) {

    var vm = this;

    ///////////////////

    vm.id;
    vm.title;
    vm.lanes = [];

    ///////////////////

    vm.goBack = goBack;
    vm.openNewLane = openNewLane;
    vm.openSettings = openSettings;

    vm.onLaneRemove = onLaneRemove;

    activate();

    ///////////////////

    function activate () {
      Security.getPassword($routeParams.id).then(() => {
        API.getProject($routeParams.id).then((project) => {
            vm.id = project.id;
            vm.title = project.title;
            vm.lanes = project.lanes;
            vm.lanes.sort((a,b) => {
                return parseInt( a.sequence ) - parseInt( b.sequence );
            });
            vm.lanes.forEach((lane,index) => {
                API.getLane(lane.id).then((updated) => vm.lanes[ index ].tasks = updated.tasks);
            });
        });
      });
    }

    function goBack() {
      $location.path("/projects");
    }

    function openNewLane() {
      var addLaneModal = $uibModal.open({
        templateUrl : 'app/lane/new-lane.html',
        controller  : 'NewLaneController',
        controllerAs: 'lane'
      });
      addLaneModal.result.then(activate);
    }

    function openSettings() {
      var settingsModal = $uibModal.open({
        templateUrl  : 'app/project/settings.html',
        controller   : 'SettingsController',
        controllerAs : 'settings'
      });
      settingsModal.result.then(activate);
    }

    function onLaneRemove () {
      activate();
    }

  }
})();
