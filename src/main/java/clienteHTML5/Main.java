package clienteHTML5;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Usuario;
import clienteHTML5.servicios.ConexionDB;
import clienteHTML5.servicios.ServicioUsuario;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //Desde la carpeta de resources
            config.enableCorsForAllOrigins();
        }).start(7000);
        try {
            ConexionDB.getInstance();
            Controladora.getInstance().crearDatosPorDefecto();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
