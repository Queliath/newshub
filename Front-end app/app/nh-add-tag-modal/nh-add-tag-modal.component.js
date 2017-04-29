export var nhAddTagModalComponent = {
    template: require('./nh-add-tag-modal.template.html'),
    controller: AddTagModalController,
    bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
    }
};

function AddTagModalController(tagService, errorMessageService) {
    var $ctrl = this;

    $ctrl.addTag = function () {
        var promise = tagService.addTag($ctrl.tag);
        promise.then(function (addedTag) {
            $ctrl.close();
        }, function (errResponse) {
            if (errResponse.status == 400 && Array.isArray(errResponse.data) && errResponse.data[0].code == 40002) {
                $ctrl.errorMessage = "TAG.NAME_ALREADY_EXIST_MESSAGE";
            } else {
                $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
            }
        });
    };
}