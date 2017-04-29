export var nhNewsCommentsComponent = {
    template: require('./nh-news-comments.template.html'),
    controller: NewsCommentsComponentController,
    bindings: {
        response: '<',
        newsId: '<'
    }
};

function NewsCommentsComponentController(commentService, errorMessageService, modalService, $stateParams, $state) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.comments = $ctrl.response.comments;
        $ctrl.commentCount = $ctrl.response.totalCount;
        $ctrl.currentPage = $stateParams.page;
    };

    $ctrl.pageChanged = function () {
        $state.go(".", { page: $ctrl.currentPage });
    };

    $ctrl.commentAdded = function () {
        loadComments();
    };

    $ctrl.openEditCommentModal = function (commentId) {
        var modalInstance = modalService.openEditCommentModal($ctrl.newsId, commentId);
        modalInstance.result.then(function () {
            loadComments();
        });
    };

    $ctrl.openDeleteCommentModal = function (commentId) {
        var modalInstance = modalService.openDeleteCommentModal($ctrl.newsId, commentId);
        modalInstance.result.then(function () {
            loadComments();
        });
    };

    function loadComments() {
        var offset = ($ctrl.currentPage - 1) * 10;
        var promise = commentService.getCommentsByNews($ctrl.newsId, offset, 10);
        promise.then(function (response) {
            $ctrl.comments = response.comments;
            $ctrl.commentCount = response.totalCount;
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    }
}