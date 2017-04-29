export default function errorMessageService() {
    var service = this;

    service.getErrorMessageByHttpCode = function (httpCode) {
        switch (httpCode) {
            case 400:
                return "BAD_REQUEST_ERROR_MESSAGE";
            case 404:
                return "NOT_FOUND_ERROR_MESSAGE";
            case 500:
                return "INTERNAL_SERVER_ERROR_MESSAGE";
            default:
                return "REQUEST_ERROR_DEFAULT_MESSAGE";
        }
    };
}