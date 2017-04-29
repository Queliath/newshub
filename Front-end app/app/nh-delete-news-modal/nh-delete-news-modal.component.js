export var nhDeleteNewsModalComponent = {
    template : require("./nh-delete-news-modal.template.html"),
    controller : DeleteNewsModalController,
    bindings : {
        resolve: '<',
        close: '&',
        dismiss: '&'
    }
};

function DeleteNewsModalController(newsService, errorMessageService) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.newsId = $ctrl.resolve.newsId;
    };

    $ctrl.deleteNews = function () {
        var promise = newsService.deleteNews($ctrl.newsId);
        promise.then(function () {
            $ctrl.close();
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    };
}
