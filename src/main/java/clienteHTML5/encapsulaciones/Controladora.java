package clienteHTML5.encapsulaciones;

import clienteHTML5.servicios.ServicioUsuario;

public class Controladora {
    private static Controladora controladora = null;
    private final ServicioUsuario servicioUsuario = new ServicioUsuario();

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
    //Implementados metodos necesarios para Crear usuarios y autentificar.
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
    public void crearDatosPorDefecto(){
        //Usuario por defecto
        if(servicioUsuario.buscar("admin")==null){
            //Asignar un rol
            servicioUsuario.crear(new Usuario("admin", "admin", "1234"));
        }
    }

}
