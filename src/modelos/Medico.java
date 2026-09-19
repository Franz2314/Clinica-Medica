package modelos;

public class Medico extends Persona {
    private String cmp;
    private Especialidad especialidad;
    private String correo;
    private String turno;

    public Medico(String codigo, String nombres, String apellidos, String telefono, String cmp, Especialidad especialidad,
            String correo, String turno) {
        super(codigo, nombres, apellidos, telefono);
        this.cmp = cmp;
        this.especialidad = especialidad;
        this.correo = correo;
        this.turno = turno;
    }

    public String getCmp() {
        return cmp;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTurno() {
        return turno;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return String.format("[%s] Dr(a). %s | CMP: %s | %s | Turno: %s",
                getCodigo(), getNombreCompleto(), cmp, especialidad, turno);
    }
}
