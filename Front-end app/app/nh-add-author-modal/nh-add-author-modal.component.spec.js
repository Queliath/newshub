describe('nhAddAuthorModalController', function () {

    beforeEach(angular.mock.module('app'));

    var passAddAuthorPromise = true;
    beforeEach(angular.mock.module(function ($provide) {
        $provide.factory('authorService', function ($q) {
            function addAuthor(author) {
                if (passAddAuthorPromise) {
                    return $q.when();
                } else {
                    return $q.reject({
                        data: "Error description",
                        status: 400
                    });
                }
            }
            return {
                addAuthor: addAuthor
            };
        });
        $provide.service('errorMessageService', function () {
            this.getErrorMessageByHttpCode = jasmine.createSpy('getErrorMessageByHttpCode').and.callFake(function (httpCode) {
                return "Error message by http code";
            })
        });
    }));

    var rootScope, mockAuthorService, mockErrorMessageService, addAuthorModalController, closeBindingSpy, dismissBindingSpy, resolveBinding;
    beforeEach(inject(function($rootScope, $componentController, authorService, errorMessageService) {
        rootScope = $rootScope;
        mockAuthorService = authorService;
        spyOn(mockAuthorService, 'addAuthor').and.callThrough();
        mockErrorMessageService = errorMessageService;

        closeBindingSpy = jasmine.createSpy('close');
        dismissBindingSpy = jasmine.createSpy('dismiss');
        resolveBinding = { key: "value" };
        addAuthorModalController = $componentController('nhAddAuthorModal', {
            authorService: mockAuthorService,
            errorMessageService: mockErrorMessageService
        }, {
            close: closeBindingSpy,
            dismiss: dismissBindingSpy,
            resolve : resolveBinding
        });
    }));

    it('should call the close() binding when calling close() in the controller', function () {
        addAuthorModalController.close();
        expect(closeBindingSpy).toHaveBeenCalled();
    });

    it('should call the dismiss() binding when calling dismiss() in the controller', function () {
        addAuthorModalController.dismiss();
        expect(dismissBindingSpy).toHaveBeenCalled();
    });

    it('should be the same resolve binding', function () {
        expect(addAuthorModalController.resolve).toEqual(resolveBinding);
    });

    it('should call the addAuthor() method in authorService when the addAuthor() is triggered', function () {
        addAuthorModalController.addAuthor();
        expect(mockAuthorService.addAuthor).toHaveBeenCalledWith(addAuthorModalController.author);
    });

    it('should call the close() method when the author is added', function () {
        passAddAuthorPromise = true;
        addAuthorModalController.addAuthor();
        rootScope.$digest();
        expect(addAuthorModalController.close).toHaveBeenCalled();
    });

    it('should use the errorMessageService when author is not added', function () {
        passAddAuthorPromise = false;
        addAuthorModalController.addAuthor();
        rootScope.$digest();
        expect(mockErrorMessageService.getErrorMessageByHttpCode).toHaveBeenCalledWith(400);
    });

    it('should put the errorMessage when author is not added', function () {
        passAddAuthorPromise = false;
        addAuthorModalController.addAuthor();
        rootScope.$digest();
        expect(addAuthorModalController.errorMessage).toBe("Error message by http code");
    });
});
