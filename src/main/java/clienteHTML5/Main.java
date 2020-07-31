package clienteHTML5;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //Desde la carpeta de resources
            config.enableCorsForAllOrigins();
        }).start(7000);

    }
}
