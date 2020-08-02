package clienteHTML5.encapsulaciones;

import clienteHTML5.servicios.ServicioFormulario;
import clienteHTML5.servicios.ServicioUsuario;

import java.util.List;

public class Controladora {
    private static Controladora controladora = null;
    private final ServicioUsuario servicioUsuario = new ServicioUsuario();
    private final ServicioFormulario servicioFormulario = new ServicioFormulario();

    public Controladora() {

    }
    public  static Controladora getInstance() {
        if (controladora == null) {
            controladora = new Controladora();
        }
        return controladora;
    }
    public static Controladora getControladora() {
        return controladora;
    }

    //------------------------------Metodos para crear Usuarios y autentificar.------------------------------
    public boolean agregarUsuario(Usuario usuario){
        return servicioUsuario.crear(usuario);
    }
    public Usuario buscarUsuario(String usuario){
        return  servicioUsuario.buscar(usuario);
    }
    public  Usuario autenticarUsuario(String usuario, String password){
        Usuario auxUsuario = buscarUsuario(usuario);
        if(auxUsuario!=null){
            if (!auxUsuario.getPassword().equals(password)){
                System.out.println("No se pudo  autentificar el usuario de forma correcta! \n");
                return null;
            }
        }
        return auxUsuario;
    }
    public List<Usuario> getServicioUsuario() {
        return servicioUsuario.explorarTodo();
    }

    // ------------------------------CRUD FORMULARIO ------------------------------
    public boolean agregarRegistro(Formulario formulario){
      return servicioFormulario.crear(formulario);
    }
    public boolean borroarRegistro(String id){
        return servicioFormulario.eliminar(servicioFormulario.buscar(id));
    }
    public boolean actualizarRegistro(Formulario formularioActualizado){
        Formulario formulario = servicioFormulario.buscar(formularioActualizado.getId());
        if(formulario!=null){
             return  servicioFormulario.editar(formularioActualizado);
        }
        return false;
    }
    public List<Formulario> getServicioFormulario() {
        return servicioFormulario.explorarTodo();
    }

    //------------------------------Creando Datos por defecto------------------------------
    public void crearDatosPorDefecto(){
        //Usuario por defecto
        if(servicioUsuario.buscar("admin")==null){
            //Asignar un rol
            servicioUsuario.crear(new Usuario("admin", "admin", "1234"));
        }
    }
}
