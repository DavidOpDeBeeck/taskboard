(function() {
  'use strict'
  angular.module( 'taskBoardApp.services')
    .factory("Security", securityService)

  function securityService ( $uibModal , $q , $cookies ) {

      let modal = {
        templateUrl : 'app/security/security.html',
        controller  : 'SecurityController',
        controllerAs: 'security',
        backdrop    : 'static'
      };

      function checkPassword( projectId , password ) {
        return $q((resolve, reject) => {
          reject();
        });
      }

      var service = {
        getPassword : getPassword
      };

      return service;

      ///////////////////

      function getPassword ( projectId ) {
        return $q((resolve, reject) => {

          modal['resolve'] = { 'projectId' : () => { return projectId; } };
          let password = $cookies.get(projectId);
          if (password == undefined) {
            let securityModal = $uibModal.open(modal);
            securityModal.result.then((result) => {
              checkPassword(projectId,result.password).then(() => {
                $cookies.put(projectId,result.password );
                resolve(result.password);
              }, () => {
                securityModal.close();
                getPassword(projectId);
              });
            });
          } else {
            return resolve(password);
          }
         });
      }
  };
})();
