package clienteHTML5.api;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Formulario;
import clienteHTML5.util.ControladorBase;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Api RESET con CRUD de Formulario
 */
public class ApiReset extends ControladorBase {
    private Controladora controladora = Controladora.getInstance();

    public ApiReset(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(()->{
            path("api-Reset", () ->{
                path("/formulario",()->{
                    after(ctx -> {
                        ctx.header("Content-Type", "application/json");
                    });
                    //Listar
                    get("/listar", ctx -> {
                        ctx.json(controladora.getServicioFormulario());
                    });
                    //Agregar
                    post("/agregar", ctx -> {
                        //Parseando la informacion del POJO. El forma debe estar en JSON
                        Formulario formulario = ctx.bodyAsClass(Formulario.class);
                        ctx.json(controladora.agregarRegistro(formulario));
                    });
                    //Borrar
                    delete("/borrar:id", ctx -> {
                        ctx.json(controladora.borroarRegistro(ctx.pathParam("id", String.class).get()));
                    });
                    put("/actualizar", ctx -> {
                        Formulario formulario = ctx.bodyAsClass(Formulario.class);
                        ctx.json(controladora.actualizarRegistro(formulario));
                    });
                });
            });
        });

    }
}
