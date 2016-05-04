(function() {
  'use strict'
  angular.module( 'taskBoardApp.services')
    .factory("Security", securityService)

  function securityService ( API , $uibModal , $q , $cookies ) {

      let modal = {
        templateUrl : 'app/security/security.html',
        controller  : 'SecurityController',
        controllerAs: 'security',
        backdrop    : 'static'
      };

      ///////////////////

      let service = {
        validate : validate
      };

      return service;

      ///////////////////

      function validate ( projectId ) {
        let password, securityModal;
        return $q((resolve, reject) => {
          resolve();
          /*password = $cookies.get(projectId);
          if (password == undefined) {
            modal.resolve = { 'projectId' : () => { return projectId; } };
            securityModal = $uibModal.open(modal);
            securityModal.result.then((result) => {
              API.validate(projectId,result.password)
                .then(() => {
                  $cookies.put(projectId,result.password );
                  resolve(result.password);
                }, () => {
                  securityModal.close();
                  validate(projectId);
                });
            });
          } else {
            return resolve(password);
          }*/
        });
      }
  };
})();
