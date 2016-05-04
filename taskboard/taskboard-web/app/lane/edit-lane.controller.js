(function() {
  'use strict'
  angular.module( 'taskBoardApp.controllers')
    .controller("EditLaneController", EditLaneController);

  function EditLaneController( API , $rootScope, $routeParams , $uibModalInstance , id ) {

    let vm = this;

    ///////////////////

    vm.id    = id;
    vm.lanes = [];

    vm.title;
    vm.sequence;
    vm.completed;

    ///////////////////

    vm.save = save;
    vm.close  = close;

    activate();

    ///////////////////

    function activate () {
      API.getProject($routeParams.id)
        .then((project) => vm.lanes = project.lanes.filter(lane => lane.id != vm.id))
        .then(() => {
          API.getLane(id).then((lane) => {
            console.log(lane);
              vm.title      = lane.title;
              vm.sequence   = lane.sequence - 1;
              vm.completed  = lane.completed;
          });
      });
    }

    function save() {
      API.updateLane(id, {
        'title'     : vm.title,
        'sequence'  : vm.sequence + 1,
        'completed' : vm.completed
      }).then(() => {
        $rootScope.$broadcast("EditLaneEvent");
        $uibModalInstance.close();
      });
    }

    function close() {
      $uibModalInstance.dismiss('cancel');
    }
  }
})();
