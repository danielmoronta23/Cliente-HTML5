package clienteHTML5.servicios;

import clienteHTML5.encapsulaciones.Usuario;

public class ServicioUsuario extends ManejadorBD<Usuario> {
    public ServicioUsuario() {
        super(Usuario.class);
    }
}
