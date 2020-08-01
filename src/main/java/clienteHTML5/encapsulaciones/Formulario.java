package clienteHTML5.encapsulaciones;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Formulario")
public class Formulario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "Sector")
    private String Sector;
    @Column(name = "NivelEscolar")
    private String NivelEscolar;
    @OneToOne
    private Usuario usuario;

    public Formulario() {
    }
    public Formulario(String nombre, String sector, String nivelEscolar, Usuario usuario) {
        this.nombre = nombre;
        Sector = sector;
        NivelEscolar = nivelEscolar;
        this.usuario = usuario;
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
}
