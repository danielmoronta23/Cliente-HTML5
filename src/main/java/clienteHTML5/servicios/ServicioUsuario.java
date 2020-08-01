package clienteHTML5.servicios;

import clienteHTML5.encapsulaciones.Usuario;

public class ServicioUsuario extends ManejadorBD<Usuario> {

    private static ServicioUsuario instancia; //ARMANDO

    public ServicioUsuario() {
        super(Usuario.class);
    }

    public static ServicioUsuario getInstancia(){

        if(instancia==null){ //ARMANDO
            instancia = new ServicioUsuario();
        }
        return instancia;
    }
}
