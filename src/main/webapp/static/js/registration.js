import {dataHandler} from "./DataHandler.js";
export let registration = {
    addEventListenerToSubmitRegistrationButton:function () {

        let registrationButton = document.querySelector("#registerSubmit");
        registrationButton.addEventListener("click", function () {

            let userName = document.querySelector("#registerName");
            let userLogin = document.querySelector("#registerEmail");
            let userPassword = document.querySelector("#registerPassword");
            let dataForServlet = {login:userLogin.value, password:userPassword.value, name:userName.value};
            console.log(dataForServlet);
            dataHandler.apiPost("/register", dataForServlet, function (isNotUser) {

                if (isNotUser){
                    let loginMessage = document.querySelector("#registerHelp");
                    loginMessage.innerHTML = `<font color="green">Registration was ended successfully</font>`;
                    userName.value = "";
                    userLogin.value = "";
                    userPassword.value = "";
                    setTimeout(function () {
                        window.location.href = "http://localhost:8080/login";
                    }, 2000);

                }else{
                    let loginMessage = document.querySelector("#registerHelp");
                    loginMessage.innerHTML = `<font color="red">User with such email is already exists</font>`;
                }
            })
        })
    }
};
function init() {
    registration.addEventListenerToSubmitRegistrationButton();

}
$(document).ready(function () {
    init();

});