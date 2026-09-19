package modelos;

/**
 * Representa una Consulta Clinica en el historial del paciente.
 */
public class ConsultaClinica {

    private static int contador = 1;
    private final String codigo;
    private final String codigoPaciente;
    private final String nombrePaciente;
    private final String fecha;
    private final String nombreMedico;
    private final String especialidad;
    private String diagnostico;
    private String medicamentosRecetados;
    private String observaciones;

    public ConsultaClinica(String codigoPaciente, String nombrePaciente, String fecha,
            String nombreMedico, String especialidad,
            String diagnostico, String medicamentosRecetados, String observaciones) {
        this.codigo = String.format("CC%04d", contador++);
        this.codigoPaciente = codigoPaciente;
        this.nombrePaciente = nombrePaciente;
        this.fecha = fecha;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.diagnostico = diagnostico;
        this.medicamentosRecetados = medicamentosRecetados;
        this.observaciones = observaciones;
    }

    public String getCodigo() { return codigo; }
    public String getCodigoPaciente() { return codigoPaciente; }
    public String getNombrePaciente() { return nombrePaciente; }
    public String getFecha() { return fecha; }
    public String getNombreMedico() { return nombreMedico; }
    public String getEspecialidad() { return especialidad; }
    public String getDiagnostico() { return diagnostico; }
    public String getMedicamentosRecetados() { return medicamentosRecetados; }
    public String getObservaciones() { return observaciones; }

    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setMedicamentosRecetados(String medicamentos) { this.medicamentosRecetados = medicamentos; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Dr(a). %s", codigo, fecha, diagnostico, nombreMedico);
    }
}
