export var nhNewsComponent = {
    template : require("./nh-news.template.html"),
    controller : NewsController,
    bindings : {
        news : '<'
    }
};

function NewsController(newsService, errorMessageService, modalService, $stateParams, $state) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.newsId = $stateParams.newsId;
        $ctrl.prevState = (!$stateParams.prevState.name) ? { name: "welcome", params: {} } : $stateParams.prevState;
        if ($state.is("news.comments")) {
            $ctrl.showingComments = true;
        }
    };

    $ctrl.showComments = function () {
        $ctrl.showingComments = true;
        $state.go(".comments");
    };

    $ctrl.openEditNewsModal = function (newsId) {
        var modalInstance = modalService.openEditNewsModal(newsId);
        modalInstance.result.then(function () {
            getNewsById();
        });
    };

    $ctrl.openDeleteNewsModal = function (newsId) {
        var modalInstance = modalService.openDeleteNewsModal(newsId);
        modalInstance.result.then(function () {
            $state.go("welcome");
        });
    };

    function getNewsById() {
        var promise = newsService.getNewsById($stateParams.newsId);
        promise.then(function (news) {
            $ctrl.news = news;
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    }
}