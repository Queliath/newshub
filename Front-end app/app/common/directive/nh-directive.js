export default function nhDirective() {
    return {
        template: '<button>nhDirective</button>',
        link: function (scope, elem) {
            var spanElement = angular.element("<span>" + scope.text + "</span>");
            elem.append(spanElement);

            scope.$watch('text', function (newVal, oldVal) {
                spanElement.text(newVal);
            });

            elem.find('button').on('click', function () {
                scope.value++;
            });
        }
    }
};