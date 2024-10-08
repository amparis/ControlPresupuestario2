const inputPassword = document.getElementById("inputPassword");
const inputRepetPassword = document.getElementById("repeatPassword");
const buttonRegisterEmployee = document.getElementById("btnform-employee")
const menuLateral = document.getElementById("accordionSidebar")
const buttonMenuL = document.getElementById("sidebarToggle")

inputRepetPassword.addEventListener("keyup", confirmSamePassword, false);
buttonMenuL.addEventListener("click", toggleMenu, false);

function confirmSamePassword() {

    if (inputRepetPassword.value.length >= inputPassword.value.length) {
        if (inputPassword.value !== inputRepetPassword.value) {
            alert("Las contrasenas no son iguales");  
        } else {
            buttonRegisterEmployee.style = "visibility : visible"
        }
    }

}

function toggleMenu (){

    alert("si entró")
    menuLateral.classList.toggle("toggled")
}