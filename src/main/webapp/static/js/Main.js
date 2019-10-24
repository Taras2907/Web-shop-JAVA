import {dom} from "./Dom.js";

function init() {
    dom.addListenerToAddCartButtons();
    dom.addListenerToCartButton();

}

$(document).ready(function () {
    init();

});