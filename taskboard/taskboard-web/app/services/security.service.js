(function() {
  'use strict'
  angular.module( 'taskBoardApp.services')
    .factory("Security", securityService)

  function securityService ( $uibModal , $q , $cookies , apiUrl , $resource ) {

      let modal = {
        templateUrl : 'app/security/security.html',
        controller  : 'SecurityController',
        controllerAs: 'security',
        backdrop    : 'static'
      };

      let validateProject = $resource( apiUrl + "/validate" , {} , {
          post : {
              method  : "POST" ,
              isArray : false
          }
      });

      ///////////////////

      let service = {
        validate : validate
      };

      return service;

      ///////////////////

      function validate ( projectId ) {
        let password, securityModal;

        let validateRequest = (projectId, password) => {
          return validateProject.post({'projectId' : projectId , 'password' : password}).$promise;
        }

        return $q((resolve, reject) => {
          password = $cookies.get(projectId);
         if (password == undefined) {
            securityModal = $uibModal.open(modal);
            securityModal.result.then((result) => {
              validateRequest(projectId,result.password).then((res) => {
                console.log(res);
                  if (res.success) {
                    $cookies.put(projectId, res.token);
                    resolve();
                  } else {
                    securityModal.close();
                    return validate(projectId);
                  }
                });
            });
          } else {
            resolve();
          }
        });
      }

  };
})();
