package clienteHTML5;

import clienteHTML5.Visual.ControladorPlantilla;
import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.servicios.ConexionDB;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //Desde la carpeta de resources
            config.addStaticFiles("/publico/dist");
            config.addStaticFiles("/publico/scripts");
            config.enableCorsForAllOrigins();
        }).start(7000);
        try {
            ConexionDB.getInstance();
            Controladora.getInstance().crearDatosPorDefecto();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        new ControladorPlantilla().rutas(app);
    }
}
