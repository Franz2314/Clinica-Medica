package modelos;

public abstract class Persona {
    private String codigo;
    private String nombres;
    private String apellidos;
    private String telefono;

    protected Persona(String codigo, String nombres, String apellidos, String telefono) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
