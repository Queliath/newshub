export default function newsService($http) {
    var service = this;

    service.getNewsById = function (newsId) {
        return $http({
            method : "GET",
            url : NEWSHUB_REST_SERVER_URI + "/news/" + newsId
        }).then(function (response) {
            return response.data;
        });
    };

    service.getAllNewsOrderedByCreationTime = function (offset, limit, tagIds, authorIds) {
        var url = NEWSHUB_REST_SERVER_URI + "/news?offset=" + offset + "&limit=" + limit;
        if (tagIds) {
            tagIds.forEach(function (item, i, tagIds) {
                url = url + "&tag=" + item;
            });
        }
        if (authorIds) {
            authorIds.forEach(function (item, i, authorIds) {
                url = url + "&author=" + item;
            });
        }
        return $http({
            method : "GET",
            url : url
        }).then(function(response) {
            return {
                newsList: response.data,
                totalCount: response.headers("X-Total-Count")
            };
        });
    };

    service.addNews = function (addingNews) {
        return $http({
            method : "POST",
            url : NEWSHUB_REST_SERVER_URI + "/news",
            data : addingNews
        }).then(function (response) {
            return response.data;
        });
    };

    service.editNews = function (editingNews) {
        return $http({
            method : "PUT",
            url : NEWSHUB_REST_SERVER_URI + "/news/" + editingNews.id,
            data : editingNews
        }).then(function (response) {
            return response.data;
        });
    };

    service.deleteNews = function (newsId) {
        return $http({
            method : "DELETE",
            url : NEWSHUB_REST_SERVER_URI + "/news/" + newsId
        });
    };
}
