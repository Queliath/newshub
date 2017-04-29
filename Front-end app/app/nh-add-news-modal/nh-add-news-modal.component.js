export var nhAddNewsModalComponent = {
    template : require("../common/template/nh-news-fom-modal.template.html"),
    controller : AddNewsModalController,
    bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
    }
};

function AddNewsModalController(newsService, authorService, tagService, errorMessageService, $state) {
    var $ctrl = this;

    $ctrl.$onInit = function () {
        $ctrl.modalName = "NEWS.ADD";
        $ctrl.news = {
            authors: [],
            tags: []
        };
    };

    $ctrl.getAuthorsByNameFragment = function (nameFragment) {
        return authorService.getAuthorsByNameFragment(nameFragment, 0, 10).then(function (authors) {
            return authors;
        });
    };

    $ctrl.getTagsByNameFragment = function (nameFragment) {
        return tagService.getTagsByNameFragment(nameFragment, 0, 10).then(function (tags) {
            return tags;
        });
    };

    $ctrl.selectAuthor = function ($item, $model, $label, $event) {
        $ctrl.authorsInput = "";

        var authorIndex = $ctrl.news.authors.findIndex(function (author, index, authors) {
            if (author.id == $item.id) {
                return true;
            }
        });

        if (authorIndex == -1) {
            $ctrl.news.authors.push($item);
        }
    };

    $ctrl.selectTag = function ($item, $model, $label, $event) {
        $ctrl.tagsInput = "";

        var tagIndex = $ctrl.news.tags.findIndex(function (tag, index, tags) {
            if (tag.id == $item.id) {
                return true;
            }
        });

        if (tagIndex == -1) {
            $ctrl.news.tags.push($item);
        }
    };

    $ctrl.removeAuthor = function (authorToRemove) {
        var authorIndex = $ctrl.news.authors.findIndex(function (author, index, authors) {
            if (author.id == authorToRemove.id) {
                return true;
            }
        });

        $ctrl.news.authors.splice(authorIndex, 1);
    };

    $ctrl.removeTag = function (tagToRemove) {
        var tagIndex = $ctrl.news.tags.findIndex(function (tag, index, tags) {
            if (tag.id == tagToRemove.id) {
                return true;
            }
        });

        $ctrl.news.tags.splice(tagIndex, 1);
    };

    $ctrl.save = function () {
        var promise = newsService.addNews($ctrl.news);
        promise.then(function (createdNews) {
            $ctrl.close();
            $state.go("news", { newsId : createdNews.id });
        }, function (errResponse) {
            $ctrl.errorMessage = errorMessageService.getErrorMessageByHttpCode(errResponse.status);
        });
    };
}