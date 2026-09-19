package servicios;

import dao.PacienteDAO;
import modelos.Paciente;
import utilidades.OrdenamientosBusqueda;

public class PacienteServicio {
    private final PacienteDAO pacienteDAO;
    private int correlativo;

    public PacienteServicio(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
        int max = 0;
        for (Paciente p : pacienteDAO.listarTodos()) {
            String codigo = p.getCodigo();
            if (codigo != null && codigo.startsWith("PAC")) {
                try {
                    int num = Integer.parseInt(codigo.substring(3));
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        this.correlativo = max + 1;
    }

    public Paciente registrarPaciente(String nombres, String apellidos, String telefono, String dni, int edad, String genero,
            String tipoSangre, String alergias, String correo, boolean tieneSis, String numeroSis) {
        if (tieneSis && (numeroSis == null || numeroSis.isBlank())) {
            throw new RuntimeException("Ingresa el numero SIS del paciente.");
        }
        String codigo = String.format("PAC%03d", correlativo++);
        Paciente paciente = new Paciente(codigo, nombres, apellidos, telefono, dni, edad, genero, tipoSangre, alergias, correo, tieneSis, numeroSis);
        pacienteDAO.guardar(paciente);
        return paciente;
    }

    public Paciente buscarPorDniArbol(String dni) {
        return pacienteDAO.buscarPorDni(dni);
    }

    public Paciente buscarPorDniLineal(String dni) {
        Paciente[] datos = obtenerPacientesComoArreglo();
        int indice = OrdenamientosBusqueda.busquedaLinealPacientePorDni(datos, dni);
        return indice >= 0 ? datos[indice] : null;
    }

    public Paciente buscarPorDniBinaria(String dni) {
        Paciente[] datos = obtenerPacientesComoArreglo().clone();
        int indice = OrdenamientosBusqueda.busquedaBinariaPacientePorDni(datos, dni);
        return indice >= 0 ? datos[indice] : null;
    }

    public Paciente buscarPorCodigo(String codigo) {
        for (Paciente paciente : obtenerPacientesComoArreglo()) {
            if (paciente.getCodigo().equalsIgnoreCase(codigo)) {
                return paciente;
            }
        }
        return null;
    }

    public Paciente[] obtenerPacientesComoArreglo() {
        return pacienteDAO.listarTodos();
    }

    public Paciente[] listarOrdenadosPorNombre() {
        Paciente[] datos = obtenerPacientesComoArreglo();
        OrdenamientosBusqueda.ordenarPacientesPorNombre(datos);
        return datos;
    }

    public Paciente[] listarOrdenadosPorDni() {
        Paciente[] datos = obtenerPacientesComoArreglo();
        OrdenamientosBusqueda.ordenarPacientesPorDniInsercion(datos);
        return datos;
    }

    public int totalPacientes() {
        return pacienteDAO.total();
    }

    public boolean actualizarPaciente(String codigo, String nombres, String apellidos, String telefono, String dni, int edad,
            String genero, String tipoSangre, String alergias, String correo, boolean tieneSis, String numeroSis) {
        if (tieneSis && (numeroSis == null || numeroSis.isBlank())) {
            throw new RuntimeException("Ingresa el numero SIS del paciente.");
        }
        Paciente paciente = pacienteDAO.buscarPorCodigo(codigo);
        if (paciente == null) {
            return false;
        }
        paciente.setNombres(nombres);
        paciente.setApellidos(apellidos);
        paciente.setTelefono(telefono);
        paciente.setDni(dni);
        paciente.setEdad(edad);
        paciente.setGenero(genero);
        paciente.setTipoSangre(tipoSangre);
        paciente.setAlergias(alergias);
        paciente.setCorreo(correo);
        paciente.setTieneSis(tieneSis);
        paciente.setNumeroSis(numeroSis == null ? "" : numeroSis);
        return pacienteDAO.actualizar(paciente);
    }

    public boolean eliminarPaciente(String codigo) {
        return pacienteDAO.eliminar(codigo);
    }

    public Paciente[] obtenerInorden() {
        return pacienteDAO.obtenerInorden();
    }

    public Paciente[] obtenerPreorden() {
        return pacienteDAO.obtenerPreorden();
    }

    public Paciente[] obtenerPostorden() {
        return pacienteDAO.obtenerPostorden();
    }
}
