package clienteHTML5.encapsulaciones;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Ubicacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "longitud")
    private String longitud;
    @Column(name = "latitud")
    private String latitud;

    public Ubicacion() {
    }

    public Ubicacion(String longitud, String latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
}
