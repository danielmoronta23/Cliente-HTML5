package clienteHTML5.Visual;

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

            app.get("/", ctx ->{

                //VERIFICAR SI EXISTE COOKIE PARA ENTRAR A LA PAGINA PRINCIPAL O LLEVAR AL LOGIN
                if (ctx.cookie("sesion") != null){

                    //FUNCION PARA IDENTIFICAR USUARIO MEDIANTE COOKIE

                    Map<String, Object> modelo = new HashMap<>();
                    //modelo.put("user", ); <-- ENVIAR USUARIO CORRESPONDIENTE
                    //modelo.put("forms", ); <-- PERSONAS REGISTRADAS POR EL USUARIO
                    ctx.render("publico/index.html", modelo);
                }else{
                    ctx.redirect("/login");
                }
            });

            app.get("/geo",ctx -> {


            });

            app.get("/login", ctx -> {

                ctx.render("publico/dist/login.html");
            });

            app.post("/ingresar", ctx -> {
                String user = ctx.formParam("usuario");
                String pass = ctx.formParam("password");
                String boton = ctx.formParam("checkbox");

                if (autenticacion(user, pass)){
                    //creando un atributo de sesion
                    ctx.sessionAttribute("usuario", user);
                    //RECORDAR USUARIO
                    if(boton != null && ctx.sessionAttribute("sesion") == null) {
                        int login_HASH = (int) Math.random();

                        //ENCRIPTACIÓN DEL HASH de la Cookie
                        BasicIntegerNumberEncryptor numberEncryptor = new BasicIntegerNumberEncryptor();
                        numberEncryptor.setPassword("secreto");
                        BigInteger myEncryptedNumber = numberEncryptor.encrypt(new BigInteger(String.valueOf(login_HASH)));

                        // CREANDO COOKIE DE 604800 seg =  1 semana
                        ctx.cookie("sesion", myEncryptedNumber.toString(), 604800);
                    }

                    //PAGINA PRINCIPAL
                    ctx.redirect("/");
                }
                else{
                    //RETORNO A LOGIN
                    ctx.redirect("/login");
                }

            });



        });
    }

    //FUNCION PARA AUTENTICAR EL LOGIN DE UN USUARIO
    private boolean autenticacion(String user, String pass) {
        boolean resultado = true;
        try{
            if (true){//ServicioUsuario.getInstancia().find(user).getPassword().matches(pass)){  <-- QUITAR TRUE CUANDO CLASE ESTE LISTA
                //resultado = true;
            }

        }catch (Exception e){
            System.out.println("Usuario NO encontrado");
        }
        return resultado;
    }

}
