describe('nhNewsService', function () {

    beforeEach(angular.mock.module('app'));

    var httpBackend, newsServiceObj;
    beforeEach(inject(function ($httpBackend, newsService) {
        httpBackend = $httpBackend;
        newsServiceObj = newsService;
    }));

    var actualResponse, expectedResponse;
    afterEach(function () {
        httpBackend.flush();
        expect(actualResponse).toEqual(expectedResponse);
        httpBackend.verifyNoOutstandingExpectation();
    });

    it('should get news by ID', function () {
        expectedResponse = { id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" };
        httpBackend.expect("GET", NEWSHUB_REST_SERVER_URI + "/news/1").respond(200, expectedResponse);
        newsServiceObj.getNewsById(1).then(function (news) {
            actualResponse = news;
        });
    });

    it('should get news ordered by creation time', function () {
        expectedResponse = [{ id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" }, { id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" }]
        httpBackend.expect("GET", NEWSHUB_REST_SERVER_URI + "/news?offset=10&limit=20&tag=3&author=5").respond(200, expectedResponse);
        newsServiceObj.getAllNewsOrderedByCreationTime(10, 20, [3], [5]).then(function (response) {
            actualResponse = response.newsList;
        });
    });

    it('should add news', function () {
        var addingNews = { title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" };
        expectedResponse = { id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" };
        httpBackend.expect("POST", NEWSHUB_REST_SERVER_URI + "/news", addingNews).respond(201, expectedResponse);
        newsServiceObj.addNews(addingNews).then(function (addedNews) {
            actualResponse = addedNews;
        });
    });

    it('should update news', function () {
        var updatingNews = { id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" };
        expectedResponse = { id: 1, title: "Test title", briefContent: "Test brief content", fullContent: "Test full content" };
        httpBackend.expect("PUT", NEWSHUB_REST_SERVER_URI + "/news/1", updatingNews).respond(200, expectedResponse);
        newsServiceObj.editNews(updatingNews).then(function (updatedNews) {
            actualResponse = updatedNews;
        });
    });

    it('should delete news', function () {
        httpBackend.expect("DELETE", NEWSHUB_REST_SERVER_URI + "/news/1").respond(204, null);
        newsServiceObj.deleteNews(1);
    });
});