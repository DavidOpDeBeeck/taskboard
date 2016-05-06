module.exports = function(grunt) {

  var app = './app/';
  var lib = './lib/';

  ///////////////////

  var dependencies = {
    js: [
      ['jquery', 'jquery.min.js'],
      ['jquery.nicescroll', 'jquery.nicescroll.min.js'],
      ['angular', 'angular.min.js'],
      ['bootstrap', 'bootstrap.min.js'],
      ['angular-route', 'angular-route.min.js'],
      ['angular-cookies', 'angular-cookies.min.js'],
      ['angular-resource', 'angular-resource.min.js'],
      ['angular-nicescroll', 'angular-nicescroll.js'],
      ['angular-drag-and-drop-lists', 'angular-drag-and-drop-lists.min.js'],
      ['angular-bootstrap', 'ui-bootstrap-tpls.min.js']
    ],
    css: [
      ['bootstrap', 'bootstrap.min.css'],
      ['css-spaces', 'spaces.bootstrap.min.css']
    ],
    fonts: [
      ['bootstrap', '*.*']
    ]
  };

  dependencies.js.forEach((dependency, index) => {
    dependencies.js[index] = lib + dependency[0] + "/js/" + dependency[1]
  });

  dependencies.css.forEach((dependency, index) => {
    dependencies.css[index] = lib + dependency[0] + "/css/" + dependency[1]
  });

  dependencies.fonts.forEach((dependency, index) => {
    dependencies.fonts[index] = lib + dependency[0] + "/fonts/" + dependency[1]
  });

  ///////////////////

  var host = grunt.option('host') || 'localhost';
  var port = grunt.option('port') || '8080';

  ///////////////////

  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: ['dist', 'lib'],
    bower: {
        install: {
          options: {
            targetDir: lib,
            layout: 'byComponent'
          }
        }
    },
    ngconstant: {
      'app-config': {
        options: {
          dest: 'app/app.config.js',
          name: 'taskBoardApp.config',
        },
        constants: {
          'apiUrl': 'http://' + host + ':' + port
        }
      }
    },
    concat: {
      'libs-js': {
        options: {
            separator: ';'
        },
        src: dependencies.js,
        dest: 'dist/js/libs.js'
      },
      'app-js': {
        options: {
            separator: ';'
        },
        src: [app + '*/app.*.js', app + '**/*.js'],
        dest: 'dist/js/app.js'
      },
      'libs-css': {
        src: [ dependencies.css ],
        dest: 'dist/css/libs.css'
      },
      'app-css': {
        src: app + '**/*.css',
        dest: 'dist/css/app.css'
      }
    },
    babel: {
      options: {
        sourceMap: false,
        presets: ['es2015']
      },
      app: {
        files: [{
          src: 'dist/js/app.js',
          dest: 'dist/js/app.js'
        }]
      }
    },
    copy: {
      fonts: {
        files: [{
           expand: true,
           flatten: true,
           src: dependencies.fonts,
           dest: 'dist/fonts'
        }]
      }
    }
  });

  ///////////////////

  grunt.loadNpmTasks('grunt-babel');
  grunt.loadNpmTasks('grunt-bower-task');
  grunt.loadNpmTasks('grunt-ng-constant');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-concat');

  grunt.registerTask('default', ['clean', 'bower', 'ngconstant' ,'concat', 'babel', 'copy']);
};
