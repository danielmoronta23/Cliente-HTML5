package clienteHTML5.api;

import clienteHTML5.encapsulaciones.*;
import clienteHTML5.util.ControladorBase;
import io.javalin.Javalin;
import io.javalin.http.ForbiddenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * Api RESET con CRUD de Formulario
 * Referencias:
 * https://github.com/vacax/sparkjava_jwt/blob/master/src/main/java/edu/pucmm/sjjwt/Main.java
 */

public class ApiReset extends ControladorBase {
    public final static String keySecret = "asd12D1234dfr123@#4Fsdcasdd5g78a";

    private Controladora controladora = Controladora.getInstance();
    private FormularioIndexDB formularioIndexDB;

    public ApiReset(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        filtro_Cors();
        app.routes(()->{
            path("api-Reset", () ->{
                //Autenticar Usuario
                get("/:autenticar", ctx -> {
                    System.out.println( "Parametro recibido: " + ctx.pathParam("autenticar"));
                    Usuario usuario = null;
                    usuario = controladora.buscarUsuario(ctx.pathParam("autenticar", String.class).get());
                    if(usuario!= null){
                        RepuestaLogin repuestaLogin = generarJWT(usuario);
                        System.out.println("Enviando repuesta el cliente...");
                        System.out.println("toke -> "+repuestaLogin.getToken());
                        ctx.json(repuestaLogin);
                       // ctx.json("true");
                    }else{
                        ctx.json("false");
                    }
                });
                path("/formulario",()->{
                    before("/*",ctx -> {

                        System.out.println("Analizando que exista el token..");

                        //  Informacion para consultar en la trama.
                        String header = "Authorization";
                        String prefijo = "Bearer";

                        //  Verificando si existe el header de autorizacion.
                        String headerAutentificacion = ctx.header(header);
                        if(headerAutentificacion == null || !headerAutentificacion.startsWith(prefijo)){
                            throw new ForbiddenResponse("No tiene permiso para acceder al recuerso solicitado! :(");
                        }else{
                            //  Recuperando el token y validando
                            String tramaJwt = headerAutentificacion.replace(prefijo, "");
                            try {
                                Claims claims = Jwts.parser()
                                        .setSigningKey(Keys.hmacShaKeyFor(keySecret.getBytes()))
                                        .parseClaimsJws(tramaJwt).getBody();
                                System.out.println("JWT recibido -> " + claims.toString());
                            }catch (ExpiredJwtException | MalformedJwtException | SignatureException e){
                                //Excepciones comunes
                                throw new ForbiddenResponse(e.getMessage());
                            }
                        }

                    });
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

    /**
     * Generariocion de Json Web Token (JWT).
     * @param usuario
     * @return
     */
    private static RepuestaLogin generarJWT(Usuario usuario){
        RepuestaLogin repuestaLogin = new RepuestaLogin();
        SecretKey secretKey = Keys.hmacShaKeyFor(keySecret.getBytes());

        //  Generacion de la fecha valida.
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(5);
        System.out.println("Fecha acutal -> "+localDateTime.toString());

        // Creacion de la trama.
        String jwt = Jwts.builder()
                .setIssuer("PUCMM-PW")
                .setSubject("CLIENTE-HTML5")
                .setExpiration(Date.from(localDateTime.toInstant(ZoneOffset.ofHours(-4))))
                .claim("usuario", usuario.getUsuario())
               // .claim("roles", String.join(",", (CharSequence) usuario.getListaRoles()))
                .signWith(secretKey)
                .compact();
        repuestaLogin.setToken(jwt);
        return repuestaLogin;
    }
    private void filtro_Cors(){
        //  Filtro para validar el CORS.(Intercambio de recursos de origen cruzado).
        app.before("/*", ctx ->{
            ctx.header("Access-Control-Allow-Origin", "*");
        });
        // Enviando la información a solicitud del CORS.
        app.options("/*", ctx -> {
            System.out.println("Accediendo al metodo de options");

            String accessControlRequestHeaders = ctx.header("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                ctx.header("Access-Control-Allow-Headers",accessControlRequestHeaders);
            }

            String accessControlRequestMethod = ctx.header("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                ctx.header("Access-Control-Allow-Methods",accessControlRequestMethod);
            }
            ctx.status(200).result("OK");
        });
    }
}
