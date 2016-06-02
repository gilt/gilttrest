(function () {

'use strict';

var angular = require('angular');

var pinnedController = function pinnedController ($scope, apiRequest) {
  apiRequest.pinList().then(function (data) {
    $scope.saleCollection = data.data.sales;
  });

  $scope.activeFilter = undefined;

  $scope.customFilter = function customFilter (sale) {
    return $scope.activeFilter === undefined || sale.store === $scope.activeFilter;
  };

  $scope.setFilterVal = function setFilterVal ($ev, val) {
    $ev.preventDefault();

    $scope.activeFilter = val;
  };
};

module.exports = angular.module('pinned', [
  require('angular-route'),
  require('../services/requests').name,
  require('../viewModels/sale').name
])

.controller('pinnedController', ['$scope', 'apiRequest', pinnedController])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/sales/pinned', {
    templateUrl: 'assets/templates/views/pinned.html',
    controller: 'pinnedController'
  });
}]);

})();
