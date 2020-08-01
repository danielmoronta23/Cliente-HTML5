package clienteHTML5.encapsulaciones;

import clienteHTML5.util.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "password")
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Roles> listaRoles;

    public Usuario() {
    }
    public Usuario(String usuario, String nombre, String password) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
    }
    public Usuario(String usuario, String nombre, String password, Set<Roles> listaRoles) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.password = password;
        this.listaRoles = listaRoles;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(Set<Roles> listaRoles) {
        this.listaRoles = listaRoles;
    }
}



