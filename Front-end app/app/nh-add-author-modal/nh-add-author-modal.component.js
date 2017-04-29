export var nhAddAuthorModalComponent = {
    template: require('./nh-add-author-modal.template.html'),
    controller: AddAuthorModalController,
    bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
    }
};

function AddAuthorModalController(authorService, errorMessageService) {
    var $ctrl = this;

    $ctrl.addAuthor = function () {
        var promise = authorService.addAuthor($ctrl.author);
        promise.then(function (addedAuthor) {
            $ctrl.close();
        }, function (errResponse) {
            if (errResponse.status == 400 && Array.isArray(errResponse.data) && errResponse.data[0].code == 40001) {
                $ctrl.errorMessage = "AUTHOR.NAME_ALREADY_EXIST_MESSAGE";
            } else {
                $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
            }
        });
    };
}