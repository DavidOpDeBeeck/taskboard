(function() {
  'use strict'
  angular.module( 'taskBoardApp.services')
    .factory("Security", securityService)

  function securityService ( $resource , apiUrl , $uibModal , $q , $cookies , $routeParams ) {

      let modal = {
        templateUrl : 'app/security/security.html',
        controller  : 'SecurityController',
        controllerAs: 'security',
        backdrop    : 'static'
      };

      let validate = $resource( apiUrl + "/validate" , {} , {
          post : {
              method  : "POST",
              isArray : false
          }
      });

      ///////////////////

      let service = {
        wrap : wrap
      };

      return service;

      ///////////////////

      function wrap ( request ) {
        return request.$promise
          .then(res => {
            console.log(res);
            if (res.code && res.code == 401) {
              return authenticate($routeParams.id)
                .then(() => {return request.$promise})
                .then((r) => console.log(r));
            }
            return res;
          });
      }

      function validateProject ( projectId , password ) {
        return validate.post({'projectId' : projectId , 'password' : password}).$promise;
      }

      function authenticate ( projectId ) {
        let password, securityModal;

        let promise = (resolve, reject) => {
          password = $cookies.get(projectId);
          if (password == undefined) {
            securityModal = $uibModal.open(modal);
            securityModal.result
              .then((result) => {
                validateProject(projectId,result.password)
                  .then((res) => {
                    if (res.success) {
                      $cookies.put(projectId, res.token);
                      resolve();
                    } else {
                      securityModal.close();
                      return authenticate(projectId);
                    }
                });
              });
          } else {
            resolve();
          }
        }
        return $q(promise);
      }
  };
})();
