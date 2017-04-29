describe('nhNewsComponent', function () {

    beforeEach(angular.mock.module('app'));

    var passGetNewsPromise;
    beforeEach(angular.mock.module(function ($provide) {
        $provide.factory('newsService', function ($q) {
            function getNewsById(newsId) {
                if (passGetNewsPromise) {
                    return $q.when({ id: newsId, title: "Test title", briefContent: "Brief content", fullContent: "Full content" });
                } else {
                    return $q.reject({ data: "Error description", status: 400 });
                }
            }
            return {
                getNewsById: getNewsById
            };
        });
        $provide.factory('modalService', function ($q) {
            function openEditNewsModal(newsId) {
                return { result: $q.when() };
            }
            function openDeleteNewsModal(newsId) {
                return { result: $q.when() };
            }
            return {
                openEditNewsModal: openEditNewsModal,
                openDeleteNewsModal: openDeleteNewsModal
            };
        });
        $provide.service('$stateParams', function () {
            this.newsId = 1;
            this.prevState = {
                name: "welcome",
                params: {}
            }
        });
        $provide.service('$state', function () {
            this.is = jasmine.createSpy('is').and.callFake(function (stateName) {
                return false;
            });
            this.go = jasmine.createSpy('go');
        });
        $provide.service('errorMessageService', function () {
            this.getErrorMessageByHttpCode = jasmine.createSpy('getErrorMessageByHttpCode').and.callFake(function (httpCode) {
                return "Error message by http code";
            })
        });
    }));

    var rootScope, newsServiceMock, modalServiceMock, $stateParamsMock, $stateMock, errorMessageServiceMock, nhNewsComponentController, newsBinding;
    beforeEach(inject(function ($rootScope, $componentController, newsService, $stateParams, $state, errorMessageService, modalService) {
        rootScope = $rootScope;
        newsServiceMock = newsService;
        spyOn(newsServiceMock, 'getNewsById').and.callThrough();
        modalServiceMock = modalService;
        spyOn(modalServiceMock, 'openEditNewsModal').and.callThrough();
        spyOn(modalServiceMock, 'openDeleteNewsModal').and.callThrough();
        $stateParamsMock = $stateParams;
        $stateMock = $state;
        errorMessageServiceMock = errorMessageService;
        newsBinding = { id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" };

        nhNewsComponentController = $componentController('nhNews',
            { newsService: newsServiceMock, errorMessageService: errorMessageServiceMock, modalService: modalServiceMock, $state: $stateMock, $stateParams: $stateParamsMock},
            { news: newsBinding });
    }));

    it('should initialize correctly', function () {
        nhNewsComponentController.$onInit();
        expect(nhNewsComponentController.newsId).toEqual($stateParamsMock.newsId);
        expect(nhNewsComponentController.prevState).toEqual($stateParamsMock.prevState);
    });

    it('should support news binding', function () {
        expect(nhNewsComponentController.news).toEqual(newsBinding);
    });

    it('should set showingComments to true value when call showComments()', function () {
        nhNewsComponentController.showComments();
        expect(nhNewsComponentController.showingComments).toEqual(true);
    });

    it('should go to news.comments state when call showComments()', function () {
        nhNewsComponentController.showComments();
        expect($stateMock.go).toHaveBeenCalledWith(".comments");
    });

    it('should use modalService when the openEditNewsModal is triggered', function () {
        nhNewsComponentController.openEditNewsModal(1);
        expect(modalServiceMock.openEditNewsModal).toHaveBeenCalledWith(1);
    });

    it('should use the newsService when the editing news modal is closed', function () {
        nhNewsComponentController.openEditNewsModal(1);
        rootScope.$digest();
        expect(newsServiceMock.getNewsById).toHaveBeenCalledWith(1);
    });

    it('should update news when the editing news modal is closed', function () {
        passGetNewsPromise = true;
        nhNewsComponentController.openEditNewsModal(1);
        rootScope.$digest();
        rootScope.$digest();
        expect(nhNewsComponentController.news).toEqual({ id: 1, title: "Test title", briefContent: "Brief content", fullContent: "Full content" });
    });

    it('should use the errorMessageService when there is error while getting news', function () {
        passGetNewsPromise = false;
        nhNewsComponentController.openEditNewsModal(1);
        rootScope.$digest();
        rootScope.$digest();
        expect(errorMessageServiceMock.getErrorMessageByHttpCode).toHaveBeenCalledWith(400);
    });

    it('should put the error message when there is error while getting news', function () {
        passGetNewsPromise = false;
        nhNewsComponentController.openEditNewsModal(1);
        rootScope.$digest();
        rootScope.$digest();
        expect(nhNewsComponentController.errorMessage).toEqual("Error message by http code");
    });

    it('should use the modalService when the openDeleteNewsModal() is triggered', function () {
        nhNewsComponentController.openDeleteNewsModal(1);
        expect(modalServiceMock.openDeleteNewsModal).toHaveBeenCalledWith(1);
    });

    it('should go to the welcome state when the news was deleted', function () {
        nhNewsComponentController.openDeleteNewsModal(1);
        rootScope.$digest();
        expect($stateMock.go).toHaveBeenCalledWith("welcome");
    });
});