export var nhEditCommentModalComponent = {
    template: require('./nh-edit-comment-modal.template.html'),
    controller: EditCommentModalController,
    bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
    }
};

function EditCommentModalController(commentService, errorMessageService) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.newsId = $ctrl.resolve.newsId;
        $ctrl.commentId = $ctrl.resolve.commentId;

        var promise = commentService.getCommentById($ctrl.newsId, $ctrl.commentId);
        promise.then(function (comment) {
            $ctrl.comment = comment;
            $ctrl.comment.newsId = $ctrl.newsId;
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    };

    $ctrl.updateComment = function () {
        var promise = commentService.updateComment($ctrl.comment);
        promise.then(function (updatedComment) {
            $ctrl.close();
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        })
    }
}
