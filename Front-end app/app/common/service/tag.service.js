export default function tagService($http) {
    var service = this;

    service.getTagsOrderedByNewsCount = function (offset, limit) {
        return $http({
            method: "GET",
            url: NEWSHUB_REST_SERVER_URI + "/tags?offset=" + offset + "&limit=" + limit
        }).then(function (response) {
            return response.data;
        });
    };

    service.addTag = function (tag) {
        return $http({
            method: "POST",
            url: NEWSHUB_REST_SERVER_URI + "/tags",
            data: tag
        }).then(function (response) {
            return response.data;
        });
    };

    service.getTagsByNameFragment = function (nameFragment, offset, limit) {
        return $http({
            method: "GET",
            url: NEWSHUB_REST_SERVER_URI + "/tags?name=" + nameFragment + "&offset=" + offset + "&limit=" + limit
        }).then(function (response) {
            return response.data;
        });
    };
}