export default function commentService($http) {
    var service = this;

    service.getCommentsByNews = function (newsId, offset, limit) {
        return $http({
            method: "GET",
            url: NEWSHUB_REST_SERVER_URI + "/news/" + newsId + "/comments?offset=" + offset + "&limit=" + limit
        }).then(function (response) {
            return {
                comments: response.data,
                totalCount: response.headers("X-Total-Count")
            };
        });
    };

    service.addComment = function (comment) {
        return $http({
            method: "POST",
            url: NEWSHUB_REST_SERVER_URI + "/news/" + comment.newsId + "/comments",
            data: comment
        }).then(function (response) {
            return response.data;
        });
    };

    service.updateComment = function (comment) {
        return $http({
            method: "PUT",
            url: NEWSHUB_REST_SERVER_URI + "/news/" + comment.newsId + "/comments/" + comment.id,
            data: comment
        }).then(function (response) {
            return response.data;
        });
    };

    service.getCommentById = function (newsId, commentId) {
        return $http({
            method: "GET",
            url: NEWSHUB_REST_SERVER_URI + "/news/" + newsId + "/comments/" + commentId
        }).then(function (response) {
            return response.data;
        });
    };

    service.deleteComment = function (newsId, commentId) {
        return $http({
            method: "DELETE",
            url: NEWSHUB_REST_SERVER_URI + "/news/" + newsId + "/comments/" + commentId
        });
    }
}