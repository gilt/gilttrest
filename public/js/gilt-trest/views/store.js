(function () {

'use strict';

var angular = require('angular');

var storeController = function storeController ($scope, $routeParams, apiRequest) {
  $scope.storeKey = $routeParams.storeKey;

  apiRequest.storeView($scope.storeKey).then(function (resp) {
  	$scope.saleCollection = resp.data.sales;
  });
};

module.exports = angular.module('store', [
	require('angular-route'),
	require('../services/requests').name,
	require('../viewModels/sale').name
])

.controller('storeController', ['$scope', '$routeParams', 'apiRequest', storeController])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/sales/:storeKey', {
    templateUrl: 'assets/templates/views/store.html',
    controller: 'storeController'
  });
}]);

})();
