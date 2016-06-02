/*global require  */
/**
 All the Javascript Tasks:

 Linting,
 Bundling,
 Minifying

*/

'use strict';

var gulp  = require('gulp');

var browserify = require('browserify');
var debug = require('gulp-debug');
var jshint  = require('gulp-jshint');
var gulpprint = require('gulp-print');
var rename = require('gulp-rename');
var source = require('vinyl-source-stream');
var uglify = require('gulp-uglify');
var streamify = require('gulp-streamify');

var configObject = require('../config');

var JS_BASE_FILE = configObject.js.JS_BASE_FILE;
var JS_DEST_BROWSERIFIED_FILE = configObject.js.JS_DEST_BROWSERIFIED_FILE;
var JS_DEST_BROWSERIFIED_MINIFIED_FILE = configObject.js.JS_DEST_BROWSERIFIED_MINIFIED_FILE;
var JS_DEST_FOLDER = configObject.js.JS_DEST_FOLDER;
var JS_SOURCE_FILES = configObject.js.JS_SOURCE_FILES;

/**
 * Gulp task to lint all of the javascript.
 */
gulp.task('lint-js', function() {
  gulp.src(JS_SOURCE_FILES)
    .pipe(debug({title : 'debug-jshint'}))
    .pipe(jshint())
    .pipe(jshint.reporter('jshint-stylish'));
});


/**
 * Gulp task to bundle the javascript with
 * browserify included.
 */
gulp.task('bundle-browserified-js', function() {
  var bundleStream = browserify(JS_BASE_FILE).bundle();

  return bundleStream
    .pipe(source(JS_BASE_FILE))
    .pipe(gulpprint())
    .pipe(rename(JS_DEST_BROWSERIFIED_FILE))
    .pipe(gulp.dest(JS_DEST_FOLDER))
    .pipe(gulpprint());
});

/**
 * Gulp task to obfuscate the browserified javascript.
 */
gulp.task('uglify-browserified-js', function() {
  var bundleStream = browserify(JS_BASE_FILE).bundle();

  return bundleStream
    .pipe(source(JS_BASE_FILE))
    .pipe(gulpprint())
    .pipe(streamify(uglify()))
    .pipe(rename(JS_DEST_BROWSERIFIED_MINIFIED_FILE))
    .pipe(gulp.dest(JS_DEST_FOLDER))
    .pipe(gulpprint());
});

gulp.task('js', ['lint-js', 'bundle-browserified-js', /*'uglify-browserified-js'*/]);
