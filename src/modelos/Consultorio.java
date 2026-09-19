package modelos;

public class Consultorio {
    private String codigo;
    private String nombre;
    private int piso;
    private Especialidad especialidad;
    private boolean disponible;

    public Consultorio(String codigo, String nombre, int piso, Especialidad especialidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.piso = piso;
        this.especialidad = especialidad;
        this.disponible = true;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPiso() {
        return piso;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        String detalleEspecialidad = especialidad == null ? "" : " | " + especialidad.name();
        return codigo + " - " + nombre + " | Piso " + piso + detalleEspecialidad;
    }
}
