package clienteHTML5.Visual;

import clienteHTML5.encapsulaciones.Controladora;
import clienteHTML5.encapsulaciones.Formulario;
import clienteHTML5.encapsulaciones.Usuario;
import clienteHTML5.util.Roles;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import java.util.*;

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

                    Usuario usuario = Controladora.getInstance().buscarUsuario(ctx.sessionAttribute("usuario"));
                    //TOMANDO FORMULARIOS CORRESPONDIENTE A ESE USUARIO
                    List<Formulario> forms = formulariobyUser(Controladora.getInstance().getServicioFormulario(), usuario);
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("user", usuario.getUsuario()); //<-- ENVIAR USUARIO CORRESPONDIENTE
                    modelo.put("forms", forms);
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

                Usuario aux = new Usuario();
                aux.setNombre(nombre);
                aux.setUsuario(usuario);
                aux.setPassword(contraseña);
                //Colocando rol al usuario
                if(rol.matches("Administrador"))
                    aux.setListaRoles(Set.of(Roles.ROLE_ADMIN));
                if(rol.matches("Empleado"))
                    aux.setListaRoles(Set.of(Roles.ROLE_USUARIO));
                else{
                    aux.setListaRoles(Set.of(Roles.ROLES_VOLUNTARIO));
                }
                Controladora.getInstance().agregarUsuario(aux);

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



            //GUARDAR FORMULARIOS
            app.post("/formulario", ctx -> {
               String nombre = ctx.formParam("nombre");
               String sector = ctx.formParam("sector");
               String usuario = ctx.formParam("usuario");


               ctx.redirect("/");
            });


            //MOSTRAR VISTA DE LISTA DE FORMULARIOS
            app.get("/informe", ctx -> {

                Map<String, Object> modelo = new HashMap<>();
                //ENVIADO USUARIO CORRESPONDIENTE A DICHA SESION
                modelo.put("user", ctx.sessionAttribute("usuario"));
                //ENVIANDO TODOS LOS FORMULARIOS DEL SERVIDOR
                modelo.put("formularios", Controladora.getInstance().getServicioFormulario());
                ctx.render("/publico/dist/Informe.html", modelo);
            });



        });
    }

    private List<Formulario> formulariobyUser(List<Formulario> servicioFormulario, Usuario user) {

        List<Formulario> list = new ArrayList<Formulario>();

        for (Formulario f: servicioFormulario) {
            if (f.getUsuario().equals(user)){
                list.add(f);
            }
        }
        return list;
    }


}
