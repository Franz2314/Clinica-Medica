package modelos;

public class RegistroTriaje {
    private static int contador = 1;
    private final String codigo;
    private final String codigoPaciente;
    private final String nombrePaciente;
    private final String fecha;
    private final double peso;
    private final double talla;
    private final double temperatura;
    private final String presionArterial;
    private final int frecuenciaCardiaca;
    private final String nivelPrioridad;

    public RegistroTriaje(String codigoPaciente, String nombrePaciente, String fecha,
            double peso, double talla, double temperatura, String presionArterial,
            int frecuenciaCardiaca, String nivelPrioridad) {
        this.codigo = "TR" + String.format("%04d", contador++);
        this.codigoPaciente = codigoPaciente;
        this.nombrePaciente = nombrePaciente;
        this.fecha = fecha;
        this.peso = peso;
        this.talla = talla;
        this.temperatura = temperatura;
        this.presionArterial = presionArterial;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.nivelPrioridad = nivelPrioridad;
    }

    public RegistroTriaje(String codigo, String codigoPaciente, String nombrePaciente, String fecha,
            double peso, double talla, double temperatura, String presionArterial,
            int frecuenciaCardiaca, String nivelPrioridad) {
        this.codigo = codigo;
        this.codigoPaciente = codigoPaciente;
        this.nombrePaciente = nombrePaciente;
        this.fecha = fecha;
        this.peso = peso;
        this.talla = talla;
        this.temperatura = temperatura;
        this.presionArterial = presionArterial;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.nivelPrioridad = nivelPrioridad;
    }

    public String getCodigo() { return codigo; }
    public String getCodigoPaciente() { return codigoPaciente; }
    public String getNombrePaciente() { return nombrePaciente; }
    public String getFecha() { return fecha; }
    public double getPeso() { return peso; }
    public double getTalla() { return talla; }
    public double getTemperatura() { return temperatura; }
    public String getPresionArterial() { return presionArterial; }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public String getNivelPrioridad() { return nivelPrioridad; }
}
