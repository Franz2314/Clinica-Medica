package modelos;

public class Paciente extends Persona {
    private String dni;
    private int edad;
    private String genero;
    private String tipoSangre;
    private String alergias;
    private String correo;
    private boolean tieneSis;
    private String numeroSis;

    public Paciente(String codigo, String nombres, String apellidos, String telefono, String dni, int edad, String genero,
            String tipoSangre, String alergias, String correo, boolean tieneSis, String numeroSis) {
        super(codigo, nombres, apellidos, telefono);
        this.dni = dni;
        this.edad = edad;
        this.genero = genero;
        this.tipoSangre = tipoSangre;
        this.alergias = alergias;
        this.correo = correo;
        this.tieneSis = tieneSis;
        this.numeroSis = numeroSis;
    }

    public String getDni() {
        return dni;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public String getAlergias() {
        return alergias;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isTieneSis() {
        return tieneSis;
    }

    public String getNumeroSis() {
        return numeroSis;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTieneSis(boolean tieneSis) {
        this.tieneSis = tieneSis;
        if (!tieneSis) {
            this.numeroSis = "";
        }
    }

    public void setNumeroSis(String numeroSis) {
        this.numeroSis = numeroSis;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | DNI: %s | Edad: %d | Sangre: %s | SIS: %s | Tel: %s",
                getCodigo(), getNombreCompleto(), dni, edad, tipoSangre,
                tieneSis ? "SI (" + (numeroSis == null || numeroSis.isBlank() ? "SIN NUMERO" : numeroSis) + ")" : "NO",
                getTelefono());
    }
}
