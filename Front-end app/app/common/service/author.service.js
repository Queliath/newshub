export default function authorService($http) {
    var service = this;

    service.getAuthorsOrderedByNewsCount = function (offset, limit) {
        return $http({
            method: "GET",
            url: NEWSHUB_REST_SERVER_URI + "/authors?offset=" + offset + "&limit=" + limit
        }).then(function (response) {
            return response.data;
        });
    };

    service.addAuthor = function (author) {
        return $http({
            method: "POST",
            url: NEWSHUB_REST_SERVER_URI + "/authors",
            data: author
        }).then(function (response) {
            return response.data;
        });
    };

    service.getAuthorsByNameFragment = function (nameFragment, offset, limit) {
        return $http({
            method: "GET",
            url: NEWSHUB_REST_SERVER_URI + "/authors?name=" + nameFragment + "&offset=" + offset + "&limit=" + limit
        }).then(function (response) {
            return response.data;
        });
    };
}
