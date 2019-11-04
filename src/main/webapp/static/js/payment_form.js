new Cleave('.creditcard', {
    creditCard: true
});

new Cleave('.cvv', {
    numeral: true,
});

let confirmButton = document.getElementById("submit-button");
let cancelButton = document.getElementById("cancel-button");

confirmButton.addEventListener("click", function() {
    confirmButton.toggleAttribute("disabled");
    cancelButton.toggleAttribute("disabled");

    confirmButton.innerHTML =
        `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Verifying...`;

    setTimeout(displaySuccess, 3000);
    setTimeout(redirectToHome, 8000);


});

let displaySuccess = function () {
    let alertSuccess =  document.querySelector(".alert-success");
    alertSuccess.toggleAttribute("hidden");

    confirmButton.innerHTML = "Verified";
};

let redirectToHome = function () {
    window.location.href = "/";
};

cancelButton.addEventListener("click", function () {
    redirectToHome();
});