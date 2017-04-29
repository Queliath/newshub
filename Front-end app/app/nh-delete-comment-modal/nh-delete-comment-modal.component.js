export var nhDeleteCommentModalComponent = {
    template: require('./nh-delete-comment-modal.template.html'),
    controller: DeleteCommentModalController,
    bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
    }
};

function DeleteCommentModalController(commentService, errorMessageService) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.newsId = $ctrl.resolve.newsId;
        $ctrl.commentId = $ctrl.resolve.commentId;
    };

    $ctrl.deleteComment = function () {
        var promise = commentService.deleteComment($ctrl.newsId, $ctrl.commentId);
        promise.then(function () {
            $ctrl.close();
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    };
}