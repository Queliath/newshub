describe('nhAuthorService', function () {

    beforeEach(angular.mock.module('app'));

    var httpBackend, authorServiceObj;
    beforeEach(inject(function ($httpBackend, authorService) {
        httpBackend = $httpBackend;
        authorServiceObj = authorService;
    }));

    var actualResponse, expectedResponse;
    afterEach(function () {
        httpBackend.flush();
        expect(actualResponse).toEqual(expectedResponse);
        httpBackend.verifyNoOutstandingExpectation();
    });

    it('should get authors ordered by news count', function () {
        expectedResponse = [{ id: 1, name: "Test author name 1", newsCount: 500 }, { id: 2, name: "Test author name 2", newsCount: 1000 }];
        httpBackend.expect("GET", NEWSHUB_REST_SERVER_URI + "/authors?offset=10&limit=20").respond(200, expectedResponse);
        authorServiceObj.getAuthorsOrderedByNewsCount(10, 20).then(function (authors) {
            actualResponse = authors;
        });
    });

    it('should add author', function () {
        var addingAuthor = { name: "Test author name" };
        expectedResponse = { id: 500, name: "Test author name" };
        httpBackend.expect("POST", NEWSHUB_REST_SERVER_URI + "/authors", addingAuthor).respond(201, expectedResponse);
        authorServiceObj.addAuthor(addingAuthor).then(function (createdAuthor) {
            actualResponse = createdAuthor;
        });
    });

    it('should get authors by name fragment', function () {
        expectedResponse = [{ id: 1, name: "Test author name 1", newsCount: 500 }, { id: 2, name: "Test author name 2", newsCount: 1000 }];
        httpBackend.expect("GET", NEWSHUB_REST_SERVER_URI + "/authors?name=test&offset=10&limit=20").respond(200, expectedResponse);
        authorServiceObj.getAuthorsByNameFragment('test', 10, 20).then(function (authors) {
            actualResponse = authors;
        });
    });
});