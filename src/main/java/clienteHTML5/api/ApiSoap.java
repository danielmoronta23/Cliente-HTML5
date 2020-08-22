package clienteHTML5.api;

import clienteHTML5.util.ControladorBase;
import com.sun.net.httpserver.HttpContext;
import io.javalin.Javalin;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import clienteHTML5.soap.FormularioWebServices;

import javax.xml.ws.Endpoint;
import java.lang.reflect.Method;

public class ApiSoap extends ControladorBase {
    public ApiSoap(Javalin app) {
        super(app);
    }

    // Para acceder al wsdl -> http://localhost:8043/ws/FormularioWebServices?wsdl
    @Override
    public void aplicarRutas() {
        Server server = app.server().server();
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
        server.setHandler(contextHandlerCollection);
        try {
            HttpContext context = build(server, "/ws");
            //El o los servicios que se estan agrupando en ese contexto
            FormularioWebServices formularioWebServices = new FormularioWebServices();
            Endpoint endpoint = Endpoint.create(formularioWebServices);
            endpoint.publish(context);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private HttpContext build(Server server, String contextString) throws Exception {
        JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true);
        JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(contextString);
        Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
        method.setAccessible(true);
        HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
        contextHandler.start();
        return ctx;
    }

}
