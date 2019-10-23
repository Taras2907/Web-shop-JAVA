import {dom} from "./Dom.js";

function init() {
    dom.addListenerToAddCartButtons();
}

$(document).ready(function () {
    init();

});