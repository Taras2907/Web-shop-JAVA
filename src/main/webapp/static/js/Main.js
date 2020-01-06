import {dom} from "./Dom.js";

function init() {
    dom.addListenerToAddCartButtons();
    dom.addListenerToCartButton();
    dom.addEventListenerToLogoutButton();

}
$(document).ready(function () {
    init();


});