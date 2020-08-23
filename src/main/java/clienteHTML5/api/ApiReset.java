package clienteHTML5.api;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Formulario;
import clienteHTML5.encapsulaciones.FormularioIndexDB;
import clienteHTML5.encapsulaciones.Foto;
import clienteHTML5.util.ControladorBase;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Api RESET con CRUD de Formulario
 */
public class ApiReset extends ControladorBase {
    private Controladora controladora = Controladora.getInstance();
    private FormularioIndexDB formularioIndexDB;

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
                    //Listar por usuario.
                    get("/listar-por-nombre/:usuario", ctx -> {
                        System.out.println( "Parametro recibido: " + ctx.pathParam("usuario"));
                        ctx.json(controladora.getFormularioPorUsuario(ctx.pathParam("usuario", String.class).get()));
                    });
                    //Autenticar Usuario
                    get("/:autenticar", ctx -> {
                        System.out.println( "Parametro recibido: " + ctx.pathParam("autenticar"));
                        if(controladora.buscarUsuario(ctx.pathParam("autenticar", String.class).get()) != null){
                            ctx.json("true");
                        }else{
                            ctx.json("false");
                        }

                    });
                    //Agregar
                    post("/agregar", ctx -> {
                        //Parseando la informacion del POJO. El forma debe estar en JSON
                        FormularioIndexDB f = ctx.bodyAsClass(FormularioIndexDB.class);
                        ctx.json(f.agregarFormulariosDB(f));
                    });
                    //Borrar
                    delete("/borrar:id", ctx -> {
                        ctx.json(controladora.borroarRegistro(ctx.pathParam("id", String.class).get()));
                    });
                    put("/actualizar", ctx -> {
                        FormularioIndexDB f = ctx.bodyAsClass(FormularioIndexDB.class);
                        ctx.json(formularioIndexDB.actualizarFormulariosDB(f));
                    });
                });
            });
        });
    }

}
