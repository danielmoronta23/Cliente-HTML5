package clienteHTML5.encapsulaciones;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Formulario")
public class Formulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "nombref")
    private String nombre;
    @Column(name = "Sector")
    private String Sector;
    @Column(name = "NivelEscolar")
    private String NivelEscolar;
    @OneToOne
    private Usuario usuario;
    @Embedded
    private Ubicacion ubicacion;
    @Embedded
    private Foto foto;
    public Formulario() {
    }
    public Formulario(String nombre, String sector, String nivelEscolar, Usuario usuario, Ubicacion ubicacion) {
        this.nombre = nombre;
        Sector = sector;
        NivelEscolar = nivelEscolar;
        this.usuario = usuario;
        this.ubicacion = ubicacion;
    }
    public Formulario(String nombre, String sector, String nivelEscolar, Usuario usuario, Ubicacion ubicacion, Foto Foto) {
        this.nombre = nombre;
        Sector = sector;
        NivelEscolar = nivelEscolar;
        this.usuario = usuario;
        this.ubicacion = ubicacion;
        this.foto = foto;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getNivelEscolar() {
        return NivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        NivelEscolar = nivelEscolar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
