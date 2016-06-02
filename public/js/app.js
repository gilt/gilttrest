/*global require */

(function () {

'use strict';

var angular = require('angular');

/* App Module */
angular.module('gilt-trest',
  [
    require('./gilt-trest/views/pinned').name,
    require('./gilt-trest/views/store').name,
    require('./gilt-trest/views/loginreg').name,
    require('angular-route')
  ])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/sales/women'});
  }]);

})();
