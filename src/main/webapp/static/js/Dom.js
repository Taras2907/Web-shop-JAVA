import {dataHandler} from "./DataHandler.js";
export let dom = {
    addListenerToAddCartButtons: function () {
        let addToCartButtons = document.querySelectorAll("[data-id]");
        addToCartButtons.forEach(function (button) {
            button.addEventListener("click", function () {
                let id = button.dataset["id"];
                dataHandler.addProductToCart(id, function () {})
            })
        })
    },
    addListenerToCartButton:function () {

        let cartButton = document.querySelector("#cartButton");
        cartButton.addEventListener("click", function () {
            dataHandler.getOrderProducts(function (products) {
                dom.createTableOfProducts(products);
            });
        })
    },
    createTableOfProducts: function (products) {
        let tbody = document.querySelector("#cartTable>tbody");
        let tableRows = dom.createTableRows(products.length, products);
        tbody.innerHTML = tableRows;
        dom.addEventListenerToMinusButton();
        dom.addEventListenerToPlusButton();
    },
    createTableRows: function (rows, products) {
        let table = "";
        for (let row = 0; row < rows; row++){
            let product = products[row];
            let thisRow = `<tr>
                            <td>${product["name"]}</td>
                            <td><button class="btn btn-success btn-sm" data-minus="-1" data-productid="${product["id"]}">-</button>
                                <label><input type="text" size="1" value="${product["quantity"]}" style="text-align: center"> </label>
                                <button class="btn btn-success btn-sm" data-plus="1" data-productid="${product["id"]}">+</button></td>
                            <td>${product["price"]}</td>

                        </tr>`;
            table += thisRow;
        }
        return table;
    },
    addEventListenerToPlusButton:function () {
        let plusButtons = document.querySelectorAll("[data-plus]");

        plusButtons.forEach(function (button) {
            let productId = button.dataset["productid"];
            button.addEventListener("click", function () {
                dataHandler.addProductToCart(productId, function () {
                    dataHandler.getOrderProducts(function (products) {
                        dom.createTableOfProducts(products);
                    });
                });
            })
        })
    },
    addEventListenerToMinusButton:function () {
        let minusButtons = document.querySelectorAll("[data-minus]");

        minusButtons.forEach(function (button) {
            let productId = button.dataset["productid"];
            button.addEventListener("click", function () {
                dataHandler.deleteProductFromCart(productId, function (resp) {
                    dataHandler.getOrderProducts(function (products) {
                        dom.createTableOfProducts(products);
                    });
                });

            })
        })
    },



};