package clienteHTML5.soap;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Formulario;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 *  Clase que permite implementar un servicio web basado en SOAP.
 */

@WebService
public class FormularioWebServices {
    private Controladora controladora = Controladora.getInstance();
    @WebMethod
    public String holaMundo(String hola){
        System.out.println("Ejecuntado en el servidor.");
        return "Hello World: "+hola;
    }
    @WebMethod
    public List<Formulario> getFormulario(){
        return controladora.getServicioFormulario();
    }
    @WebMethod
    public boolean agregarRegistro(Formulario formulario){
        return controladora.agregarRegistro(formulario);
    }
    @WebMethod
    public boolean borroarRegistro(String id){
        return controladora.borroarRegistro(id);
    }
    @WebMethod
    public boolean actualizarRegistro(Formulario formularioActualizado){
       return controladora.actualizarRegistro(formularioActualizado);
    }
    @WebMethod
    public Formulario buscarFormulario(String id){
        return buscarFormulario(id);
    }
    @WebMethod
    public List<Formulario> getFormularioPorUsuario(String usario){
        return controladora.getFormularioPorUsuario(usario);
    }
}
