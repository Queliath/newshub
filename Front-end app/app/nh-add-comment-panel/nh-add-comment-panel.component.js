export var nhAddCommentPanelComponent = {
    template: require('./nh-add-comment-panel.template.html'),
    controller: AddCommentPanelController,
    bindings: {
        newsId: '<',
        onUpdate: '&'
    }
};

function AddCommentPanelController(commentService, errorMessageService) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.comment = {
            newsId: $ctrl.newsId
        }
    };

    $ctrl.addComment = function (commentForm) {
        var promise = commentService.addComment($ctrl.comment);
        promise.then(function (addedComment) {
            $ctrl.comment.content = "";
            commentForm.content.$touched = false;

            $ctrl.onUpdate();
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    }
}
