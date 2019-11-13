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
                console.log(products);
                dom.createTableOfProducts(products);
            });
        })
    },
    createTableOfProducts: function (products) {

        let tbody = document.querySelector("#cartTable");
        let tableRows = dom.createTableRows(products.length, products);
        tbody.innerHTML = tableRows;
        if (products.length> 0){
            dom.addEventListenerToMinusButton();
            dom.addEventListenerToPlusButton();
            dom.addEventListenerToDeleteButton();
        }

    },
    createTableRows: function (rows, products) {
        let table = "";
        let totalSum = 0;
        for (let row = 0; row < rows; row++){
            let img = "/static/img/product_" + `${row + 1}` +".jpg"; // indexes of images start from 1 so we need to add 1
            let product = products[row];
            let actualSum = (parseFloat(product["price"]) * parseFloat(product["quantity"])).toFixed(1);
            totalSum += parseFloat(actualSum);

            let thisRow =
            `<div class="row no-gutters mb-2">
                <div class="col-1 text-left pr-3" ><img src="${img}" class="img-thumbnail"></div>
                <div class="col-3 text-left">${product["name"]}</div>
                <div class="col-4 text-center">
                   <button class="btn btn-primary  btn-sm" data-remove data-productid="${product["id"]}">-</button>
                    <label><input class="text-center" id="productQuantity" type="text" size="1" value="${product["quantity"]}"> </label>
                    <button class="btn btn-primary btn-sm" data-add data-productid="${product["id"]}">+</button>
                </div>
                <div class="col-3 text-center">${actualSum} PLN</div>
                <div class="col-1">
                   <button id="deletebutton" class="btn btn-primary btn-sm" data-productid="${product["id"]}">delete</button>
                </div>
            </div>`;

            table += thisRow;
        }
        table += `<div class="row justify-content-end no-gutters "><div class="col-4 bg-success text-white rounded "><p class="text-center" style="color: white !important">Total sum: ${totalSum} PLN</p></div> </div>`;
        return table;
    },
    addRelatedProductsToCart:function(){


    },
    addProductsToCarouselSlide:function(products){
        const productsInOneSlide = 4;
        let slide = "";
        for(let i = 0; i < productsInOneSlide; i++){
            let product = products[i];
            slide += `
        <div class="col-3">
            <div class="container no-gutters">
                <div class="row no-gutters text-center">
                    <div class="col-4">
                    <img class=" img-thumbnail" src="/static/img/product_1.jpg" alt="First slide">
                    </div>
                </div>
                <div class="row  no-gutters text-center">
                    <small>${product["name"]}</small><br>
                </div>
                <div class="row  no-gutters text-center">
                    <small>${product["price"]}</small>
                </div>
                <div class="row  no-gutters text-center">
                    <button class="btn btn-primary btn-sm no-gutters" data-add data-productid="${product["id"]}">add to cart</button>
                </div>
            </div>
        </div>`
        }
        return slide;
    },
    addEventListenerToDeleteButton:function(){
        let deleteButtons = document.querySelectorAll("#deletebutton");
        deleteButtons.forEach(function (button, idx) {
            button.addEventListener("click", function () {
                let productId = button.dataset["productid"];
                let quantity = document.querySelectorAll("#productQuantity")[idx]["value"];
                let idQuantity = {id: productId, quantity: quantity};
                dataHandler.deleteProductFromCart(idQuantity, function () {
                    dataHandler.getOrderProducts(function (products) {
                        dom.createTableOfProducts(products);
                    });
                })

            })
        })
    },
    addEventListenerToPlusButton:function () {
        let plusButtons = document.querySelectorAll("[data-add]");

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
        let minusButtons = document.querySelectorAll("[data-remove]");
        let minQuantity = 1;

        minusButtons.forEach(function (button, idx) {

            let productId = button.dataset["productid"];
            let quantity = document.querySelectorAll("#productQuantity")[idx]["value"];
            let idQuantity = {id: productId, quantity: 1};

            button.addEventListener("click", function () {
                if (quantity> minQuantity) {

                    dataHandler.deleteProductFromCart(idQuantity, function (resp) {
                        dataHandler.getOrderProducts(function (products) {
                            dom.createTableOfProducts(products);
                        });
                    });
                }
            })
        })
    },
    addEventListenerToLogoutButton:function () {
        let logoutButton = document.querySelector("#logOutButton");
        if (logoutButton !== null){
            logoutButton.addEventListener("click", function () {
                dataHandler.userLogout();
                location.reload();
            })
        }
    }

};