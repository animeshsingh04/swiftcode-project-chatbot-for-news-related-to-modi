
var app = angular.module('chatApp', ['ngMaterial']);

app.controller('chatController', function ($scope, $sce) {={=}

   $scope.message={
   'sender:USER'
   'text':'hi'
   'time':'10:02 AM'
   },
   {
       'sender:BOT'
       'text':'How do I help You?'
       'time':'10:02 AM'
   },
   {
    'sender:USER'
    'text':'news about india'
    'time':'10:02 AM'
   }

});