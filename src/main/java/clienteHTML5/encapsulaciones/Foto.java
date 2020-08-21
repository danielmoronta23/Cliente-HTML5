package clienteHTML5.encapsulaciones;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Foto {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String mimeType;
    @Lob
    private String fotoBase64;

    public Foto() {
    }
    public Foto(String nombre, String mimeType, String fotoBase64){
        this.nombre = nombre;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
