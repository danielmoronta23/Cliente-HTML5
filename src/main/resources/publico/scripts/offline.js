
console.log("Script para detectar offline o online")

window.addEventListener('online',  updateOnlineStatus);
window.addEventListener('offline', updateOnlineStatus);

function updateOnlineStatus(event) {
    var condition = navigator.onLine ? "online" : "offline";
    document.body.className = condition;
    indicador(condition);
}

//FUNCIÓN PARA EL FRONTEND
function indicador(condition) {

    if (condition === "online") {
        ///alerta para el usuario
        var signal = document.getElementById("demo");
        signal.innerHTML = "En linea <i id=\"logo\" class=\"fa fa-check-circle fa-fw\"></i>";
        signal.setAttribute("class", "btn-success");
        document.getElementById("logo").setAttribute("class", "fa fa-check-circle fa-fw");

        ///botones para interactual con el servidor
        document.getElementById("enviarForm").removeAttribute("disabled")
        document.getElementById("listForm").setAttribute("href","Informe.html");
    }
    if (condition === "offline"){

        ///alerta para el usuario
        var signal = document.getElementById("demo");
        signal.innerHTML = "Sin Conexión <i id=\"logo\" class=\"fa fa-times-circle fa-fw\"></i>";
        signal.setAttribute("class", "btn-danger");

        ///botones para interactual con el servidor
        document.getElementById("enviarForm").setAttribute("disabled", "true");
        document.getElementById("listForm").setAttribute("href", "#");
    }
}

//FUNCION PARA LA UTILIZACIÓN DE WEBSTORAGE
function Local() {
    var almacenamiento = localStorage.getItem("BackUp_fecha");
    if(almacenamiento == null){
        var pag = document.body;
        var tmp = new Date().toUTCString();
        localStorage.setItem("BackUp_fecha", tmp);
        localStorage.setItem("pagina", pag);

        //Almacenando información en el ambiente de sesión.
        sessionStorage.setItem("BackUp_fecha", new Date().toUTCString())
    }

}
