describe('nhDirectiveWithScope', function () {

    beforeEach(angular.mock.module('app'));

    var scope, compiledElement;
    beforeEach(inject(function ($compile, $rootScope) {
        scope = $rootScope.$new();
        scope.twoWayBinding = {
            key: "value"
        };
        scope.oneWayBinding = {
            key: "value"
        };
        scope.callbackBinding = jasmine.createSpy('callbackBinding');

        var element = angular.element('<nh-directive-with-scope two-way-binding="twoWayBinding" one-way-binding="oneWayBinding" callback-binding="callbackBinding()"></nh-directive-with-scope>');
        compiledElement = $compile(element)(scope);
        scope.$digest();
    }));

    it('should support two-way binding', function () {
        var isolatedScope = compiledElement.isolateScope();
        isolatedScope.twoWayBinding.key = "anotherValue";
        expect(scope.twoWayBinding.key).toEqual("anotherValue");
    });

    it('should support one-way binding', function () {
        var isolatedScope = compiledElement.isolateScope();
        isolatedScope.oneWayBinding.key = "anotherValue";
        expect(scope.oneWayBinding.key).toEqual("value");
    });

    it('should support callback binding', function () {
        var isolatedScope = compiledElement.isolateScope();
        isolatedScope.callbackBinding();
        expect(scope.callbackBinding).toHaveBeenCalled();
    });
});