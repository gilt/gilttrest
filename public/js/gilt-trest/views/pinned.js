(function () {

'use strict';

var angular = require('angular');

var pinnedController = function pinnedController ($scope, $location, apiRequest, dataStore) {
  var username = dataStore.get('username')
  var password = dataStore.get('password')
  if (!username || !password) {
    $location.path('/login');
  } else {
    var authToken = btoa(username + ":" + password)
    apiRequest.pinList(authToken).then(function (resp) {
      $scope.saleCollection = resp.data;
    });
  }

  $scope.activeFilter = undefined;

  $scope.customFilter = function customFilter (sale) {
    return $scope.activeFilter === undefined || sale.detail.store === $scope.activeFilter;
  };

  $scope.setFilterVal = function setFilterVal ($ev, val) {
    $ev.preventDefault();

    $scope.activeFilter = val;
  };
};

module.exports = angular.module('pinned', [
  require('angular-route'),
  require('../services/requests').name,
  require('../services/data').name,
  require('../viewModels/sale').name
])

.controller('pinnedController', ['$scope', '$location', 'apiRequest', 'dataStore', pinnedController])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/sales/pinned', {
    templateUrl: 'assets/templates/views/pinned.html',
    controller: 'pinnedController'
  });
}]);

})();
