/*global module */

'use strict';

var MODULE_NAME = 'gilt-trest';

module.exports = {

  js : {
    JS_BASE_FILE              : './public/js/app.js',
    BASE_MODULE_FILE          : './public/js/' + MODULE_NAME + '/' + MODULE_NAME + '.js',
    JS_SOURCE_FILES           : './public/js/' + MODULE_NAME + '/**/*.js',
    JS_DEST_FULL_FILE         : './' + MODULE_NAME + '.module.full.js',
    JS_DEST_MINIFIED_FILE     : './' + MODULE_NAME + '.module.min.js',
    JS_DEST_BROWSERIFIED_FILE : './' + MODULE_NAME + '.browserified.full.js',
    JS_DEST_BROWSERIFIED_MINIFIED_FILE  : './' + MODULE_NAME + '.browserified.min.js',
    JS_DEST_FOLDER            : './public/dist/js'
  },

  css : {
    LESS_SOURCE_FILES : './public/css/' + MODULE_NAME + '/*.less',
    LESS_BASE_FILE    : './public/css/' + MODULE_NAME + '/' + MODULE_NAME + '.less',
    CSS_DEST_FOLDER       : './public/dist/css'
  },

  spec : {
    SOURCE_FILES  : './test/*.js',
    SOURCE_FOLDER : './test/'
  },

  ext_deps : [
    'angular',
    'angular-route'
  ],

  source_map : [
    'js/' + MODULE_NAME + '/config.js',
    'js/' + MODULE_NAME + '/' + MODULE_NAME + '.js',
  ]
};
