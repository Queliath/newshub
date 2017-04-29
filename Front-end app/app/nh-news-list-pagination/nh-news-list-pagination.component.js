export var nhNewsListPaginationComponent = {
    template: require('./nh-news-list-pagination.template.html'),
    controller: NewsListPaginationController,
    bindings: {
        currentPage: '<',
        itemsPerPage: '<',
        totalItems: '<',
        onPageChanged: '&',
        onItemsPerPageChanged: '&'
    }
};

function NewsListPaginationController() {
    var $ctrl = this;

    $ctrl.setItemsPerPage = function (value) {
        $ctrl.itemsPerPage = value;
        $ctrl.onItemsPerPageChanged({ itemsPerPage: $ctrl.itemsPerPage });
    };

    $ctrl.pageChanged = function () {
        $ctrl.onPageChanged({ currentPage: $ctrl.currentPage });
    };
}