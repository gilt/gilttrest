(function () {

  var angular = require('angular');

  module.exports = angular.module('data', [])
    .service('dataStore', function($http, $log, $location) {
      var data = {}


      return {
        set: function(key, value) {
            data[key] = value;
        },
        get: function(key){
            return data[key];
        },
        getAll: function(){
            return data;
        }
      };
  });
})();