package clienteHTML5.servicios;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private String URL = "jdbc:h2:tcp://localhost/~/ClienteHTML5";
    private static ConexionDB conexionBD;
    private static Server tcp;
    private static Server webServer;


    public ConexionDB() throws SQLException {
        registroDriver();
        InciarBD();
    }

    public static ConexionDB getInstance() throws SQLException {
        if(conexionBD==null){
            conexionBD = new ConexionDB();
        }
        return conexionBD;
    }
    public static void InciarBD() throws SQLException {
        //subiendo en modo servidor H2
        tcp = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
        String status = Server.createWebServer( "-webPort", "9091", "-webAllowOthers", "-webDaemon").start().getStatus();
        System.out.println("Status Web: "+status);

    }
    public static void detenerBD() throws SQLException {
        tcp.stop();
    }
    /**
     * Registrar Driver. 1er paso para hacer una conectividad JDBS
     */
    private void registroDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * No es necesario hacerlo En los Driver modernos porque se registran automatico.
         * pero se hace con fines educativos
         */
    }

    /**
     * Abrir Objeto de conexion. 2do paso.
     * URL = protocolo:subprotocolo://servidor:puerto/subnombre
     */
    public Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
            //Por defecto se le pasa password vacio, pero claramente se puede cambiar

        } catch (SQLException ex) {
        }
        return con;
    }


}
