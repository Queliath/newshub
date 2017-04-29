export var nhNewsListSidebarComponent = {
    template: require('./nh-news-list-sidebar.template.html'),
    controller: NewsListSidebarController,
    bindings: {
        innerCtrl: '=',
        tagIds: '<',
        authorIds: '<',
        onUpdate: '&'
    }
};

function NewsListSidebarController(authorService, tagService, modalService, errorMessageService) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.displayingSearchTags = false;
        $ctrl.displayingSearchAuthors = false;
        setDefaultTags();
        setDefaultAuthors();

        $ctrl.innerCtrl = {
            refreshContent: function () {
                setDefaultTags();
                setDefaultAuthors();
            }
        };
    };

    $ctrl.getTagsByNameFragment = function (nameFragment) {
        return tagService.getTagsByNameFragment(nameFragment, 0, 10).then(function (tags) {
            $ctrl.sidebarTags = tags;
            $ctrl.displayingSearchTags = true;
            return tags;
        });
    };

    $ctrl.resetTagsSearch = function () {
        setDefaultTags();
        $ctrl.displayingSearchTags = false;
        $ctrl.tagSearchInput = "";
    };

    $ctrl.getAuthorsByNameFragment = function (nameFragment) {
        return authorService.getAuthorsByNameFragment(nameFragment, 0, 10).then(function (authors) {
            $ctrl.sidebarAuthors = authors;
            $ctrl.displayingSearchAuthors = true;
            return authors;
        });
    };

    $ctrl.resetAuthorsSearch = function () {
        setDefaultAuthors();
        $ctrl.displayingSearchAuthors = false;
        $ctrl.authorSearchInput = "";
    };

    $ctrl.isActiveFilterTag = function (tagId) {
        return $ctrl.tagIds.indexOf(String(tagId)) != -1;
    };

    $ctrl.isActiveFilterAuthor = function (authorId) {
        return $ctrl.authorIds.indexOf(String(authorId)) != -1;
    };

    $ctrl.toggleFilterTag = function (tagId) {
        var indexOfTag = $ctrl.tagIds.indexOf(String(tagId));
        if (indexOfTag == -1) {
            $ctrl.tagIds[$ctrl.tagIds.length] = String(tagId);
        } else {
            $ctrl.tagIds.splice(indexOfTag, 1);
        }

        $ctrl.onUpdate({ tagIds: $ctrl.tagIds, authorIds: $ctrl.authorIds });
    };

    $ctrl.toggleFilterAuthor = function (authorId) {
        var indexOfAuthor = $ctrl.authorIds.indexOf(String(authorId));
        if (indexOfAuthor == -1) {
            $ctrl.authorIds[$ctrl.authorIds.length] = String(authorId);
        } else {
            $ctrl.authorIds.splice(indexOfAuthor, 1);
        }

        $ctrl.onUpdate({ tagIds: $ctrl.tagIds, authorIds: $ctrl.authorIds });
    };

    $ctrl.resetFilters = function () {
        $ctrl.onUpdate({ tagIds: [], authorIds: [] });
    };

    $ctrl.openAddAuthorModal = function () {
        var modalInstance = modalService.openAddAuthorModal();
        modalInstance.result.then(function () {
            setDefaultAuthors();
        });
    };

    $ctrl.openAddTagModal = function () {
        var modalInstance = modalService.openAddTagModal();
        modalInstance.result.then(function () {
            setDefaultTags();
        });
    };

    function setDefaultTags() {
        var tagsPromise = tagService.getTagsOrderedByNewsCount(0, 10);
        tagsPromise.then(function (tags) {
            $ctrl.sidebarTags = tags;
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    }

    function setDefaultAuthors() {
        var authorsPromise = authorService.getAuthorsOrderedByNewsCount(0, 10);
        authorsPromise.then(function (authors) {
            $ctrl.sidebarAuthors = authors;
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    }
}