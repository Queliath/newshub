export default function translateService($translate) {
    var service = this;

    service.getCurrentLanguageId = function () {
        return $translate.use();    
    };
    
    service.changeLanguage = function (languageId) {
        $translate.use(languageId);
    };
}
