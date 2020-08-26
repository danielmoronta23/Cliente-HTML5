package clienteHTML5;

import clienteHTML5.Visual.ControladorPlantilla;
import clienteHTML5.api.ApiReset;
import clienteHTML5.api.ApiSoap;
import clienteHTML5.controlador.ControladorWebSocket;
import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.servicios.ConexionDB;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //Desde la carpeta de resources.
            config.addStaticFiles("/publico/dist/");
            config.enableCorsForAllOrigins();
        });
        try {
            ConexionDB.getInstance();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        new ApiSoap(app).aplicarRutas();
        app.start(8043);

        new ControladorPlantilla().rutas(app);
        new ControladorWebSocket(app).aplicarRutas();
        new ApiReset(app).aplicarRutas();

    }
}
