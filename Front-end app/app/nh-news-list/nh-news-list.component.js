export var nhNewsListComponent = {
    template : require("./nh-news-list.template.html"),
    controller : NewsListController
};

function NewsListController(newsService, errorMessageService, modalService, $state, $stateParams) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.filterTagIds = ($stateParams.tag) ? $stateParams.tag.toString().split(",") : [];
        $ctrl.filterAuthorIds = ($stateParams.author) ? $stateParams.author.toString().split(",") : [];

        setNewsList();
    };

    $ctrl.getStateInfo = function () {
        return {
            name: "welcome",
            params: $stateParams
        };
    };

    $ctrl.sidebarUpdated = function (tagIds, authorIds) {
        $state.go('.', { page: 1, tag: (tagIds.length == 0) ? null : tagIds.toString(), author: (authorIds.length == 0) ? null : authorIds.toString() }, { notify: false });
    };

    $ctrl.pageChanged = function (currentPage) {
        $state.go('.', { page: currentPage }, { notify: false });
    };

    $ctrl.itemsPerPageChanged = function (itemsPerPage) {
        $state.go('.', { page: 1, limit: itemsPerPage }, { notify: false });
    };

    $ctrl.openAddNewsModal = function () {
        modalService.openAddNewsModal();
    };

    $ctrl.openEditNewsModal = function (newsId) {
        var modalInstance = modalService.openEditNewsModal(newsId);
        modalInstance.result.then(function () {
            $state.go("news", { newsId : newsId });
        });
    };

    $ctrl.openDeleteNewsModal = function (newsId) {
        var modalInstance = modalService.openDeleteNewsModal(newsId);
        modalInstance.result.then(function () {
            setNewsList();
            $ctrl.sidebarCtrl.refreshContent();
        });
    };

    var availableLimits  = [10, 20, 30, 50];

    function setNewsList() {
        var page = parseInt($stateParams.page, 10);
        var limit = parseInt($stateParams.limit, 10);
        if (availableLimits.indexOf(limit) == -1) {
            limit = 10;
        }

        var offset = (page - 1) * limit;
        var promise = newsService.getAllNewsOrderedByCreationTime(offset, limit, $ctrl.filterTagIds, $ctrl.filterAuthorIds);
        promise.then(function (response) {
            $ctrl.newsList = response.newsList;
            $ctrl.totalItems = response.totalCount;
            $ctrl.itemsPerPage = limit;
            $ctrl.currentPage = page;
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    }
}