var app = angular.module('chatApp', ['ngMaterial']);
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('orange', {
            'default': '400', // by default use shade 400 from the pink palette for primary intentions
            'hue-1': '100', // use shade 100 for the <code>md-hue-1</code> class
            'hue-2': '600', // use shade 600 for the <code>md-hue-2</code> class
            'hue-3': 'A100' // use shade A100 for the <code>md-hue-3</code> class
        })
        .accentPalette('grey', {
            'default': 'A400'
        });
});



app.controller('chatController', function ($scope, $sce) {

    $scope.messages = []

    var exampleSocket = new WebSocket("wss://swiftcode-ani.herokuapp.com/chatSocket");
    exampleSocket.onmessage = function (event) {
        var jsonData = JSON.parse(event.data);
        jsonData.time = new Date()
            .toLocaleTimeString();
        $scope.messages.push(jsonData);
        $scope.$apply();
        console.log(jsonData);
    };
    $scope.sendMessage = function () {
        exampleSocket.send($scope.userMessage);
        $scope.userMessage = "";
        $scope.trust = $sce.trustAsHtml;
    };

});