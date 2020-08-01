package clienteHTML5.Visual;

import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class ControladorPlantilla {

    public ControladorPlantilla() {
        registrarPlantilla();
    }

    public void registrarPlantilla() {
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    public void rutas(Javalin app) {
        app.routes(() -> {

            app.get("/", ctx ->{


                ctx.render("publico/index.html");
            });

            app.get("/geo",ctx -> {


            });

            app.post("/login", ctx -> {


                ctx.redirect("/");
            });



        });
    }

}
