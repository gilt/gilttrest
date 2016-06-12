(function () {

'use strict';

var angular = require('angular');

var saleController = function saleController ($scope, apiRequest, dataStore) {
  // $scope.sale made avalible by storeController scope

  $scope.pinIt = function(ev) {
    var username = dataStore.get('username')
    var password = dataStore.get('password')
    var authToken = btoa(username + ":" + password)

    apiRequest.pinSale(this.sale.detail.sale_key, authToken).then(
      function successFn (saleResp) {
        $scope.sale.pinned = true
      }, function errorFn (error) {
        alert("Error pinning sale")
    });
  };

  $scope.unpinIt = function(ev) {
    var username = dataStore.get('username')
    var password = dataStore.get('password')
    var authToken = btoa(username + ":" + password)
    apiRequest.unpinSale(this.sale.detail.sale_key, authToken).then(
      function successFn (saleResp) {
        $scope.sale.pinned = false
      }, function errorFn (error) {
        alert("Error pinning sale")
    });
  };
};

var saleDirective = function saleDirective () {
  return {
    restrict: 'E',
    controller: 'saleCtrl',
    scope: {
      sale: '='
    },
    templateUrl : '/assets/templates/directive_partials/sale.html'
  };
};

module.exports = angular.module('sale', [
    require('../services/requests').name,
    require('../services/data').name
])

.controller('saleCtrl', ['$scope', 'apiRequest', 'dataStore', saleController])

.directive('sale', saleDirective);

})();
