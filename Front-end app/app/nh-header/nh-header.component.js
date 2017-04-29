export var nhHeaderComponent = {
    template : require("./nh-header.template.html"),
    controller : HeaderController
};

function HeaderController(translateService) {
    var $ctrl = this;

    $ctrl.isCurrentLanguage = function (languageId) {
        return languageId == translateService.getCurrentLanguageId();
    };

    $ctrl.changeLanguage = function (languageId) {
        translateService.changeLanguage(languageId);
    };
}