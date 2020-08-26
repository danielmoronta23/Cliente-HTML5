package clienteHTML5.controlador;

import clienteHTML5.encapsulaciones.*;
import clienteHTML5.util.ControladorBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.List;

public class ControladorWebSocket extends ControladorBase {
    //Creando el repositorio de las sesiones recibidas.
    public static List<Session> usuariosConectados = new ArrayList<>();

    public ControladorWebSocket(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        webSockert();

    }

    public void webSockert(){
        /**
         * Definición del WebSockert en Javalin en contexto.
         */
        app.ws("/conectarServidor", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexion Iniciada - "+ctx.getSessionId());
                usuariosConectados.add(ctx.session);

            });
            ws.onMessage(ctx -> {
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println("Recibiendo Mesaje por WS...");
                List<FormularioIndexDB> formularios = objectMapper.readValue(ctx.message(), new TypeReference<List<FormularioIndexDB>>() {});
                //Enviar formularios a la BD H2
                //Controladora.getInstance().agregarRegistro(formularios);
                System.out.println("Cantidad de Formularios Recibido: "+agregarFormulariosDB(formularios));
                System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.message());
                System.out.println("================================");

            });
            ws.onClose(ctx -> {
                System.out.println("La conexión se ha  cerrada - "+ctx.getSessionId());
                usuariosConectados.remove(ctx.session);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
            });

        });

    }
    public int agregarFormulariosDB(List<FormularioIndexDB> formularioIndexDB){
        //No es la mejor practica. Pero, por la falta de time, se hizo de esa manera.
        Formulario aux = null;
        Usuario auxUsuario = null;
        if(formularioIndexDB.size()>0){
            auxUsuario = Controladora.getInstance().buscarUsuario(formularioIndexDB.get(0).getUsuario());
        }
        for (FormularioIndexDB f: formularioIndexDB) {
            aux = new Formulario(f.getNombre(),f.getSector(),f.getNivelEscolar(), auxUsuario, new Ubicacion(f.getLongitud(),f.getLatitud()));
            Controladora.getControladora().agregarRegistro(aux);
        }
        return  formularioIndexDB.size();
    }
}
