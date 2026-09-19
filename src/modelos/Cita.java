package modelos;

public class Cita {
    private String codigo;
    private Paciente paciente;
    private Medico medico;
    private Consultorio consultorio;
    private String fecha;
    private String hora;
    private String motivo;
    private double costoConsulta;
    private EstadoCita estado;

    public Cita(String codigo, Paciente paciente, Medico medico, Consultorio consultorio, String fecha, String hora,
            String motivo, double costoConsulta) {
        this.codigo = codigo;
        this.paciente = paciente;
        this.medico = medico;
        this.consultorio = consultorio;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.costoConsulta = costoConsulta;
        this.estado = EstadoCita.PROGRAMADA;
    }

    public String getCodigo() {
        return codigo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public double getCostoConsulta() {
        return costoConsulta;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setCostoConsulta(double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Paciente: %s | Medico: %s | Consultorio: %s | %s %s | %s",
                codigo, estado, paciente.getNombreCompleto(), medico.getNombreCompleto(),
                consultorio == null ? "Sin asignar" : consultorio.getNombre(), fecha, hora, motivo);
    }
}
