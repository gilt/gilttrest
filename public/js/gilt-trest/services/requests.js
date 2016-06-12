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
      var url = storeUrlBase + '/pinned';

      return $http({
        method: 'GET',
        url : url
      });
    }

    function pinSale (saleKey, authToken) {
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authToken;
      var url = pinUrlBase + '/' + saleKey;

      return $http({
        method: 'PUT',
        url : url
      })
    }

    function unpinSale(saleKey, authToken) {
      $http.defaults.headers.common['Authorization'] = 'Basic ' + authToken;
      var url = pinUrlBase + '/' + saleKey;

      return $http({
        method: 'DELETE',
        url : url
      })
    }

    return {
      login     : login,
      register  : register,
      storeView : storeView,
      pinList   : pinList,
      pinSale   : pinSale,
      unpinSale : unpinSale
    };

  });

})();
