import {dataHandler} from "./DataHandler.js";
export let login = {
    addEventListenerToSubmitLoginButton:function () {

        let submitLoginButton = document.querySelector("#loginSubmit");
        submitLoginButton.addEventListener("click", function () {
            let userLogin = document.querySelector("#loginUserName").value;
            let userPassword = document.querySelector("#loginPassword").value;
            let dataForServlet = {login:userLogin, password:userPassword};
            dataHandler.apiPost("/login/check", dataForServlet, function (isARegisteredUser) {
                if (isARegisteredUser){
                    window.location.href = "http://localhost:8080/";
                }else{
                    //window.location.href = "http://localhost:8080/login";
                    let loginMessage = document.querySelector("#loginHelp");
                    loginMessage.innerHTML = "wrong password";

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