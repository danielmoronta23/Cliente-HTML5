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
                    //ENVIANDO FORMULARIOS
                    modelo.put("forms", forms);
                    ctx.render("publico/dist/index.html", modelo);
                }else{
                    ctx.redirect("/login");
                }
            });


            //REGISTRAR USUARIO
            app.post("/registrar", ctx -> {
                Map<String, Object> modelo = new HashMap<>();

                String nombre = ctx.formParam("nombre");
                String usuario = ctx.formParam("usuario");
                String contrasenna = ctx.formParam("contra");
                String rol = ctx.formParam("Rol");
                System.out.println("el valor es> "+ctx.formParam("Rol"));
                Usuario aux = new Usuario(usuario, nombre, contrasenna);
                //Colocando rol al usuario
                if(rol!=null){
                if(rol.matches("Administrador"))
                    aux.setListaRoles(Set.of(Roles.ROLE_ADMIN));
                if(rol.matches("Empleado"))
                    aux.setListaRoles(Set.of(Roles.ROLE_USUARIO));
                else{
                    aux.setListaRoles(Set.of(Roles.ROLES_VOLUNTARIO));
                }
                if(Controladora.getInstance().buscarUsuario(usuario)==null){
                    Controladora.getInstance().agregarUsuario(aux);
                    ctx.render("publico/dist/index.html", modelo);
                    System.out.println("CREADO EXISTOSAMENTE");
                }else{
                    //El nombre de usuario ya existe, intentelo de nuevo.
                    modelo.put("Error", "El nombre de usuario ya existe. Intentelo de nuevo! ");
                    ctx.render("publico/dist/register.html", modelo);
                    System.out.println("NO SE PUDO CREAR EXISTOSAMENTE");

                }
                }else{
                    //Se debe asignar un rol
                    System.out.println("Se debe asignar un rol");
                }

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
                Map<String, Object> modelo = new HashMap<>();
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
                    modelo.put("Error", "Please check username & password! ");
                    //RETORNO A LOGIN
                    ctx.render("publico/dist/login.html", modelo);
                }

            });




            //MOSTRAR VISTA DE LISTA DE FORMULARIOS
            app.get("/Dameinforme", ctx -> {

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
        System.out.println("Buscando Formulario del usuario");
        for (Formulario f: servicioFormulario) {
            if (f.getUsuario().equals(user)){
                list.add(f);
            }
        }
        return list;
    }


}
