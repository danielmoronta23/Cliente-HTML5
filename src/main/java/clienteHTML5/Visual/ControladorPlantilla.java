package clienteHTML5.Visual;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Usuario;
import clienteHTML5.servicios.ServicioUsuario;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import org.jasypt.util.numeric.BasicIntegerNumberEncryptor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ControladorPlantilla {

    public ControladorPlantilla() {
        registrarPlantilla();
    }

    public void registrarPlantilla() {
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    public void rutas(Javalin app) {
        app.routes(() -> {

            //VISTA PRINCIPAL
            app.get("/", ctx ->{

                //VERIFICAR SI EXISTE COOKIE PARA ENTRAR A LA PAGINA PRINCIPAL O LLEVAR AL LOGIN
                if (ctx.sessionAttribute("usuario") != null){

                    //FUNCION PARA IDENTIFICAR USUARIO MEDIANTE COOKIE

                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("user", ctx.sessionAttribute("usuario")); //<-- ENVIAR USUARIO CORRESPONDIENTE
                    //modelo.put("forms", ); <-- PERSONAS REGISTRADAS POR EL USUARIO
                    ctx.render("publico/index.html", modelo);
                }else{
                    ctx.redirect("/login");
                }
            });



            //ENVIAR DATOS AL SERVIDOR
            app.post("/EnviarServidor", ctx -> {

            });

            //REGISTRAR USUARIO
            app.post("/registrar", ctx -> {
                String nombre = ctx.formParam("nombre");
                String usuario = ctx.formParam("usuario");
                String contraseña = ctx.formParam("contra");
                String rol = ctx.formParam("rol");


                ctx.redirect("/");
            });

            //VISTA DEL LOGIN
            app.get("/login", ctx -> {

                ctx.render("publico/dist/login.html");
            });

            //CERRAR SESION
            app.get("/loginOUT", ctx -> {
               ctx.clearCookieStore();
               ctx.sessionAttribute("usuario", null);

               ctx.redirect("/login");
            });


            //AUTENTICACIÓN EN EL LOGIN
            app.post("/ingresar", ctx -> {
                String user = ctx.formParam("usuario");
                String pass = ctx.formParam("password");
                String boton = ctx.formParam("checkbox");

                Usuario aux = Controladora.getInstance().autenticarUsuario(user, pass);
                if (aux != null){
                    //creando un atributo de sesion
                    ctx.sessionAttribute("usuario", user);
                    //PAGINA PRINCIPAL
                    ctx.redirect("/");
                }
                else{
                    //RETORNO A LOGIN
                    ctx.redirect("/login");
                }

            });



            //GUARDAR FORMULARIO
            app.post("/formulario", ctx -> {
               String nombre = ctx.formParam("nombre");
               String sector = ctx.formParam("sector");
               String usuario = ctx.formParam("usuario");


               ctx.redirect("/");
            });



        });
    }



}
