/*global require */

'use strict';

var gulp  = require('gulp');

var JS_BASE_FILE = require('../config').js.JS_BASE_FILE;
var JS_SOURCE_FILES = require('../config').js.JS_SOURCE_FILES;
var LESS_SOURCE_FILES = require('../config').css.LESS_SOURCE_FILES;
// var SPEC_SOURCE_FILES = require('../config').spec.SOURCE_FILES;

gulp.task('watch', function() {

  gulp.watch(JS_BASE_FILE, ['js'/*, 'mochify-phantomjs'*/]);
  gulp.watch(JS_SOURCE_FILES, ['js']);
  gulp.watch(LESS_SOURCE_FILES, ['less']);
  // gulp.watch(SPEC_SOURCE_FILES, ['mochify']);

});
