export default function modalService($uibModal) {
    var service = this;

    service.openAddNewsModal = function () {
        return $uibModal.open({
            component : "nhAddNewsModal"
        });
    };

    service.openEditNewsModal = function (newsId) {
        return $uibModal.open({
            component : "nhEditNewsModal",
            resolve : {
                newsId : function () {
                    return newsId;
                }
            }
        });
    };

    service.openDeleteNewsModal = function (newsId) {
        return $uibModal.open({
            component : "nhDeleteNewsModal",
            resolve : {
                newsId : function () {
                    return newsId;
                }
            }
        });
    };

    service.openEditCommentModal = function (newsId, commentId) {
        return $uibModal.open({
            component : "nhEditCommentModal",
            resolve : {
                newsId : function () {
                    return newsId;
                },
                commentId : function () {
                    return commentId;
                }
            }
        });
    };

    service.openDeleteCommentModal = function (newsId, commentId) {
        return $uibModal.open({
            component : "nhDeleteCommentModal",
            resolve : {
                newsId : function () {
                    return newsId
                },
                commentId : function () {
                    return commentId;
                }
            }
        });
    };

    service.openAddAuthorModal = function () {
        return $uibModal.open({
            component : "nhAddAuthorModal"
        });
    };

    service.openAddTagModal = function () {
        return $uibModal.open({
            component : "nhAddTagModal"
        });
    };
}