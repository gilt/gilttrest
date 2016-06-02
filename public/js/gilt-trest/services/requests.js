(function () {

var angular = require('angular');

var userUrlBase = '/users';
var saleUrlBase = '/sales';

module.exports = angular.module('request', [])
  .service('apiRequest', function($http, $log, $location) {

    function login (userForm) {
      var url = userUrlBase + '/login';

      return $http({
        method: 'POST',
        url : url,
        data : userForm
      }).
      success(function (resp, status, headers, config) {
        $log.debug(resp.data);
        $http.defaults.headers.common.username = resp.username;
        return saleUrlBase + '/women';
      }).
      error(function (error, status, headers, config) {
        $log.debug(error);
        $http.defaults.headers.common.username = undefined;
        return error;
      });
    }

    function register (userObject) {
      var url = userUrlBase + '/register';

      return $http({
        method: 'POST',
        url : url,
        data : userObject
      }).
      success(function (resp, status, headers, config) {
        $log.debug(resp.data);
        $http.defaults.headers.common.username = resp.username;
        return saleUrlBase + '/women';
      }).
      error(function (error, status, headers, config) {
        $log.debug(error);
        return error;
      });
    }

    function storeView (storeKey) {
      var url = saleUrlBase + '/' + storeKey;

      $http.defaults.headers.common.username = 'kyle';

      return $http({
        method: 'GET',
        url : url
      }).
      success(function (resp, status, headers, config) {
        $log.debug(resp);
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

    function pinList () {
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

    function pinSale (saleKey) {
      var url = saleUrlBase + '/' + saleKey + '/pin';

      return $http({
        method: 'GET',
        url : url
      }).
      success(function (resp, status, headers, config) {
        $log.debug(resp);
        return resp;
      }).
      error(function (error, status, headers, config) {
        $log.debug(error);
        return error;
      });
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
