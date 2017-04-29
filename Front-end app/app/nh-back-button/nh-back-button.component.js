export var nhBackButtonComponent = {
    template : require("./nh-back-button.template.html"),
    controller : BackButtonController,
    bindings : {
        stateInfo: '<'
    }
};

function BackButtonController($state) {
    var $ctrl = this;

    $ctrl.back = function () {
        $state.go($ctrl.stateInfo.name, $ctrl.stateInfo.params);
    };
}
