package clienteHTML5.encapsulaciones;

import clienteHTML5.servicios.ServicioUsuario;

import java.util.List;

public class FormularioIndexDB {
    private String nombre;
    private String sector;
    private String nivelEscolar;
    private String latitud;
    private String longitud;
    private String id;
    private String usuario;
    private Foto foto;

    public FormularioIndexDB() {
    }

    public FormularioIndexDB(String nombre, String sector, String nivelEscolar, String latitud, String longitud, String id, String usuario) {
        this.nombre = nombre;
        this.sector = sector;
        this.nivelEscolar = nivelEscolar;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
        this.usuario = usuario;
    }
    public FormularioIndexDB(String nombre, String sector, String nivelEscolar, String latitud, String longitud, String id, String usuario, Foto foto) {
        this.nombre = nombre;
        this.sector = sector;
        this.nivelEscolar = nivelEscolar;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
        this.usuario = usuario;
        this.foto = foto;
    }

    public Foto getFoto() {
        return foto;
    }

    public Foto setFoto(Foto foto) {
        this.foto = foto;
        return foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int agregarFormulariosDB(List<FormularioIndexDB> formularioIndexDB){
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
    public Formulario agregarFormulariosDB(FormularioIndexDB f){
        Formulario aux = null;
        Usuario auxUsuario = null;
        System.out.println("Entro!!!!!!!!!!!");
        if(f!=null){
            auxUsuario = Controladora.getInstance().buscarUsuario(f.getUsuario());
            if(auxUsuario != null) {
                aux = new Formulario(f.getNombre(),f.getSector(),f.getNivelEscolar(), auxUsuario,
                        new Ubicacion(f.getLongitud(),f.getLatitud()),
                        new Foto(f.foto.getNombre(),f.foto.getMimeType(), f.foto.getFotoBase64()));
                System.out.println("Se creo el form!!");
                Controladora.getControladora().agregarRegistro(aux);
                return new Formulario();
            }

        }
        return null;
    }
    public boolean actualizarFormulariosDB(FormularioIndexDB f){
        Formulario aux = null;
        Usuario auxUsuario = null;
        if(f!=null){
            auxUsuario = Controladora.getInstance().buscarUsuario(f.getUsuario());
            aux = Controladora.getInstance().buscarFormulario(f.getId());
            if(auxUsuario!= null && aux !=  null ){
                aux = new Formulario(f.getNombre(),f.getSector(),f.getNivelEscolar(), auxUsuario, new Ubicacion(f.getLongitud(),f.getLatitud()));
                return Controladora.getControladora().actualizarRegistro(aux);
            }
        }
        return false;
    }
}
