var app = angular.module('chatApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('grey')
        .accentPalette('green');
});

app.controller('chatController', function ($scope) {
    $scope.messages = [
        {
            'sender': 'USER',
            'text': "Hello"
    	},
        {
            'sender': 'BOT',
            'text': "Hi, what can I do for you?"
    	},
        {
            'sender': 'USER',
            'text': "Who will win IPL?"
    	},
        {
            'sender': 'BOT',
            'text': "Ee sala cup namde"
    	}
    ];
});