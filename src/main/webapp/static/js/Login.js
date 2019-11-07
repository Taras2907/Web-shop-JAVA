import {dataHandler} from "./DataHandler.js";
export let login = {
    addEventListenerToSubmitLoginButton:function () {

        let submitLoginButton = document.querySelector("#loginSubmit");
        submitLoginButton.addEventListener("click", function () {
            let userLogin = document.querySelector("#loginUserEmail");
            let userPassword = document.querySelector("#loginPassword");
            let dataForServlet = {login:userLogin.value, password:userPassword.value};
            dataHandler.apiPost("/login/check", dataForServlet, function (isARegisteredUser) {
                if (isARegisteredUser){
                    window.location.href = "http://localhost:8080/";
                    let loginButton = document.getElementById("#loginButton");
                    loginButton.innerText = "Log out";

                }else{
                    let loginMessage = document.querySelector("#loginHelp");
                    loginMessage.innerHTML = `<font color="red">Invalid username or password</font>`;

                    userLogin.value = "";
                    userPassword.value = "";


                }
            })
        })
    }
};
function init() {
    login.addEventListenerToSubmitLoginButton();

}
$(document).ready(function () {
    init();

});