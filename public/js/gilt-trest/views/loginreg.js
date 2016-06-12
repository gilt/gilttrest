(function () {

'use strict';

var angular = require('angular');

var loginController = function loginController ($scope, $location, apiRequest, dataStore) {
  $scope.user = {'name': undefined, 'username' : undefined, 'password' : undefined};

  $scope.login = function register ($ev) {
    $ev.preventDefault();
    // NOTE: this == $scope

    apiRequest.login($scope.user).
    then(function successFn (response) {
      dataStore.set('name', response.data.username);
      dataStore.set('username', $scope.user.username);
      dataStore.set('password', $scope.user.password);
      $location.path('/sales/women');
    }, function errorFn (error) {
      alert("Login Failed!")
    });
  };

};

var registerController = function registerController ($scope, $location, apiRequest, dataStore) {
  $scope.user = {'name': undefined, 'username' : undefined, 'password' : undefined};

  $scope.register = function register ($ev) {
    $ev.preventDefault();
    // NOTE: this == $scope

    apiRequest.register($scope.user).
    then(function success (response) {
        dataStore.set('name', $scope.user.name);
        dataStore.set('username', $scope.user.username);
        dataStore.set('password', $scope.user.password);
        $location.path('/sales/women');
    }, function errorFn (error) {
        alert("Failed to register user")
    });
  };
};

module.exports = angular.module('login', [
	require('angular-route'),
	require('../services/requests').name,
	require('../services/data').name
])

.controller('loginCtrl', ['$scope', '$location', 'apiRequest', 'dataStore', loginController])
.controller('registerCtrl', ['$scope', '$location', 'apiRequest', 'dataStore', registerController])

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
