(function () {

var angular = require('angular');

var userUrlBase = 'api/users';
var storeUrlBase = 'api/stores';
var pinUrlBase = 'api/pins';

module.exports = angular.module('request', [])
  .service('apiRequest', function($http, $log, $location) {

    function login (userForm) {
      var url = userUrlBase + '/login';

      return $http({
        method: 'POST',
        url : url,
        data : userForm
      })
    }

    function register (userForm) {
      var url = userUrlBase + '/register';

      return $http({
        method: 'POST',
        url : url,
        data : userForm
      })
    }

    function storeView (storeKey, authToken) {
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authToken;
      var url = storeUrlBase + '/' + storeKey;


      return $http({
        method: 'GET',
        url : url
      })
    }

    function pinList (authToken) {
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authToken;
      var url = saleUrlBase + '/pinned';

      return $http({
        method: 'GET',
        url : url
      }).
      success(function (resp, status, headers, config) {
        $log.debug(resp.sales);
        return resp.sales;
      }).
      error(function (error, status, headers, config) {
        $log.debug(error);

        if (status === 403) {
          $location.path('/register');
        }

        return error;
      });
    }

    function pinSale (saleKey, authToken) {
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authToken;
      var url = saleUrlBase + '/' + saleKey + '/pin';

      return $http({
        method: 'GET',
        url : url
      })
    }

    return {
      login     : login,
      register  : register,
      storeView : storeView,
      pinList   : pinList,
      pinSale   : pinSale
    };

  });

})();
