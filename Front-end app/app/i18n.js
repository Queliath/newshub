import 'angular-translate-loader-static-files';
import 'angular-translate-storage-cookie';

export default function translate($translateProvider) {
    $translateProvider.useCookieStorage();

    $translateProvider.useStaticFilesLoader({
        prefix: 'i18n/',
        suffix: '.json'
    });

    $translateProvider.preferredLanguage("en");
};