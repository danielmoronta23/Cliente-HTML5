package clienteHTML5.soap;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Formulario;
import clienteHTML5.encapsulaciones.FormularioIndexDB;

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
    public boolean agregarRegistro(FormularioIndexDB formulario){
        FormularioIndexDB f = null;

        if(f.agregarFormulariosDB(formulario) != null){
            return true;
        }
        return false;
    }
    @WebMethod
    public boolean borroarRegistro(String id){
        return controladora.borroarRegistro(id);
    }
    @WebMethod
    public boolean actualizarRegistro(FormularioIndexDB formularioActualizado){
        FormularioIndexDB f = null;
       return f.actualizarFormulariosDB(f);
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
