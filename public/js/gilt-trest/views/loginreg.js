(function () {

'use strict';

var angular = require('angular');

var loginController = function loginController ($scope, $location, apiRequest) {
  $scope.user = {'username' : undefined};

  $scope.login = function register ($ev) {
    $ev.preventDefault();
    // NOTE: this == $scope

    apiRequest.login($scope.user).
    then(function successFn (nextUrl) {
      $location.path(nextUrl);
    }, function errorFn (error) {
      // TODO: add error handling
    });
  };

};

var registerController = function registerController ($scope, $location, apiRequest) {
  $scope.user = {'username' : undefined, 'email' : undefined };

  $scope.register = function register ($ev) {
    $ev.preventDefault();
    // NOTE: this == $scope

    apiRequest.register($scope.user).
    then(function success (nextUrl) {
      $location.path(nextUrl);
    }, function errorFn (error) {
      // TODO: add error handling
    });
  };
};

module.exports = angular.module('login', [
	require('angular-route'),
	require('../services/requests').name
])

.controller('loginCtrl', ['$scope', '$location', 'apiRequest', loginController])
.controller('registerCtrl', ['$scope', '$location', 'apiRequest', registerController])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'assets/templates/views/login.html',
    controller: 'loginCtrl'
  }).when('/register', {
    templateUrl: 'assets/templates/views/register.html',
    controller: 'registerCtrl'
  });
}]);

})();
