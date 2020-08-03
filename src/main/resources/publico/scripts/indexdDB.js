//Referencia: https://github.com/vacax/javalin-demo

console.log("Entrando a Scrip para IndexdDB")
//Referencia: https://github.com/vacax/javalin-demo

//Dependiendo el navegador se busca la referencia del objeto.
const indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB

//Se indica el nombre y la versión
const dataBase = indexedDB.open("ClienteHTML", 1);

//Se ejecuta la primera vez que se crea la estructura o se cambia la versión de la base de datos.
dataBase.onupgradeneeded = function (e) {
    console.log("Creando la estructura de la tabla");

    //obteniendo la conexión activa
    active = dataBase.result;

    //creando la colección:
    //En este caso, la colección, tendrá un Id autogenerado.
    const formulario = active.createObjectStore("formulario", { keyPath : 'id', autoIncrement : true });

    formulario.createIndex('por_indice', 'indice', {unique : true});

};
//El evento que se dispara una vez, lo
dataBase.onsuccess = function (e) {
    console.log('Proceso ejecutado de forma correctamente');
};

dataBase.onerror = function (e) {
    console.error('Error en el proceso: '+e.target.errorCode);
};

//---------------------------GEOLOGIZACIÓN--------------------------------
//Obteniendo GEOLOCALIZACIÓN
var lati="";
var longi="";
function GEOPosicion() {
    function success(pos) {
        var crd = pos.coords;
        lati = crd.latitude;
        longi = crd.longitude;
    }
    navigator.geolocation.getCurrentPosition(success);
}


//--------------------------------GRUD Formulario---------------------------
function agregarFormulario() {
    //Agregar lo de Nivel Escolar...
    var name="";
    var sector="";

    name=$("#nombreF").val();
    sector=$("#sector").val();
    //validando campos..
    if(name!="" && sector!="") {

        var dbActiva = dataBase.result; //Nos retorna una referencia al IDBDatabase.

        // Para realizar una operación de agreagr, actualización o borrar.
        // Es necesario abrir una transacción e indicar un modo: readonly, readwrite y versionchange
        var transaccion = dbActiva.transaction(["formulario"], "readwrite");

        //Manejando los errores.
        transaccion.onerror = function (e) {
            alert(request.error.name + '\n\n' + request.error.message);
        };

        transaccion.oncomplete = function (e) {
            document.querySelector("#nombreF").value = '';
            alert('Formulario agregado correctamente!');
            listarDatos();
        };

        //Abriendo la colección de datos que estaremos usando.
        var formulario = transaccion.objectStore("formulario");




        //Para agregar se puede usar add o put; el add requiere que no exista el objeto.
        var request = formulario.put({
            //id: document.querySelector("#id").value,
            nombre: document.querySelector("#nombreF").value,
            sector: document.querySelector("#sector").value,
            nivelEscolar: document.querySelector("#nivelEscolar").value,
            latitud: lati,
            longitud: longi
        });


        request.onerror = function (e) {
            var mensaje = "Error: " + e.target.errorCode;
            console.error(mensaje);
            alert(mensaje)
        };

        request.onsuccess = function (e) {
            console.log("Datos Procesado con exito");
            //document.querySelector("#id").value = "";
            document.querySelector("#nombreF").value = "";
            document.querySelector("#sector").value = "";
            document.querySelector("#nivelEscolar").value = "";
        };

    }
    else {
        console.log("Debe Completar todos los campos requeridos...")
    }
}

function borrarFormulario() {

    const id = $("#idBorrar").val();
    console.log("ID digitadO: "+id);

    const data = dataBase.result.transaction(["formulario"], "readwrite");
    const formulario = data.objectStore("formulario");

    formulario.delete(parseInt(id)).onsuccess = function (e) {
        console.log("Formulario eliminado...");
        listarDatos();
    };

}
function editarFormulario() {

    //recuperando la id.
    var id = $("#idEdit").val();
    console.log("ID digitadO:  "+id);

    var nombre = $("#nombreEdit").val();
    console.log("el nombre digitada: "+nombre);

    var sector = $("#sectorEdit").val();
    console.log("el nombre digitada: "+sector);

    var nivelEscolar = $("#nivelEscolarEdit").val();
    console.log("el nombre digitada: "+nivelEscolar);

    //abriendo la transacción en modo escritura.
    var data = dataBase.result.transaction(["formulario"],"readwrite");
    var formulario = data.objectStore("formulario");

    //buscando formulario por la referencia al key.
    formulario.get(parseInt(id)).onsuccess = function(e) {

        var resultado = e.target.result;
        console.log("los datos: "+JSON.stringify(resultado));

        if(resultado !== undefined){

            resultado.nombre = nombre;
            resultado.sector = sector;
            resultado.nivelEscolar = nivelEscolar;

            var solicitudUpdate = formulario.put(resultado);

            //eventos.
            solicitudUpdate.onsuccess = function (e) {
                console.log("Datos Actualizados....");
                listarDatos();
            }
            solicitudUpdate.onerror = function (e) {
                console.error("Error Datos Actualizados....");
            }
        }else{
            console.log("Formulario no encontrado...");
        }
    };
}
function listarDatos() {
    //por defecto si no lo indico el tipo de transacción será readonly
    var data = dataBase.result.transaction(["formulario"]);
    var formulario = data.objectStore("formulario");
    var contador = 0;
    var formulario_recuperados=[];

    //Abriendo el cursor.
    formulario.openCursor().onsuccess=function(e) {
        //Recuperando la posicion del cursor
        var cursor = e.target.result;
        if(cursor){
            contador++;
            //recuperando el objeto.
            formulario_recuperados.push(cursor.value);

            //Función que permite seguir recorriendo el cursor.
            cursor.continue();
        }else{
            console.log("La cantidad de registros recuperados es: "+contador);
        }
    };
    //Una vez que se realiza la operación se llama la impresión.
    data.oncomplete = function () {
        imprimirTabla(formulario_recuperados);
    }
}

function imprimirTabla(lista_formulario) {
    // creando la tabla...
    var fila =""
    for (var key in lista_formulario) {
        //console.log("indice: ", key)
        fila +="<tr>"
        fila+= "<td>"+lista_formulario[key].id+"</td>"
        fila+= "<td>"+lista_formulario[key].nombre+"</td>"
        fila+=  "<td>"+lista_formulario[key].sector+"</td>"
        fila+=  "<td>"+lista_formulario[key].nivelEscolar+"</td>"
        fila+= "<td>"
        fila+=  "<a id='editar' href='#editFromModal' class='edit' data-toggle='modal'>"+ "<i class='material-icons' data-toggle='tooltip' title='Edit'>"+"&#xE254;"+"</i>" +"</a>"
        fila+= 	"<a id='borrar' href='#deleteFroModal' class='delete' data-toggle='modal'>"+"<i class='material-icons' data-toggle='tooltip' title='Delete'>"+"&#xE872;"+"</i>"+"</a>"
        fila+= "</td>"
        fila+=  "</tr>"

    }
    document.getElementById("listaFormulario").innerHTML=fila;

}
