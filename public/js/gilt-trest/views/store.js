(function () {

'use strict';

var angular = require('angular');

var storeController = function storeController ($scope, $routeParams, $location, apiRequest, dataStore) {
  $scope.storeKey = $routeParams.storeKey;

  var username = dataStore.get('username')
  var password = dataStore.get('password')
  if (!username || !password) {
    $location.path('/login');
  } else {
    var authToken = btoa(username + ":" + password)
    apiRequest.storeView($scope.storeKey, authToken).then(function (resp) {
    	$scope.saleCollection = resp.data;
    });
  }

};

module.exports = angular.module('store', [
	require('angular-route'),
	require('../services/requests').name,
	require('../services/data').name,
	require('../viewModels/sale').name
])

.controller('storeController', ['$scope', '$routeParams', '$location', 'apiRequest', 'dataStore', storeController])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/sales/:storeKey', {
    templateUrl: 'assets/templates/views/store.html',
    controller: 'storeController'
  });
}]);

})();
