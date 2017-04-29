describe('nhDirective', function () {

    beforeEach(angular.mock.module('app'));

    var scope, compiledElement;
    beforeEach(inject(function ($compile, $rootScope) {
        scope = $rootScope.$new();
        var element = angular.element('<nh-directive></nh-directive>');
        compiledElement = $compile(element)(scope);
        scope.$digest();
    }));

    it('should watch for text in the scope', function () {
        scope.text = "Text in scope";
        scope.$digest();
        var spanElement = compiledElement.find('span');
        expect(spanElement.text()).toEqual("Text in scope");
    });

    it('should increment the value in scope by clicking button', function () {
        scope.value = 10;
        var buttonElement = compiledElement.find('button');
        buttonElement.triggerHandler('click');
        scope.$digest();
        expect(scope.value).toEqual(11);
    });
});