import {dataHandler} from "./DataHandler.js";
export let dom = {
    addListenerToAddCartButtons: function () {
        let buttons = document.querySelectorAll(".btn");
        buttons.forEach(function (button) {
            button.addEventListener("click", function () {
                dataHandler.getOrderProducts(1,function (products) {
                    console.log();
                })
            })
        })
    }

};