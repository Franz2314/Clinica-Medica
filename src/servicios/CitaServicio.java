package servicios;

import dao.CitaDAO;
import modelos.Cita;
import modelos.Consultorio;
import modelos.EstadoCita;
import modelos.Medico;
import modelos.Paciente;
import utilidades.OrdenamientosBusqueda;

public class CitaServicio {
    private final CitaDAO citaDAO;
    private int correlativo;

    public CitaServicio(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
        int max = 0;
        for (Cita c : citaDAO.listarTodos()) {
            String codigo = c.getCodigo();
            if (codigo != null && codigo.startsWith("CIT")) {
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

    public Cita programarCita(Paciente paciente, Medico medico, Consultorio consultorio, String fecha, String hora,
            String motivo, double costoConsulta) {
        String codigo = String.format("CIT%03d", correlativo++);
        Cita cita = new Cita(codigo, paciente, medico, consultorio, fecha, hora, motivo, costoConsulta);
        citaDAO.guardar(cita);
        return cita;
    }

    public Cita buscarPorCodigo(String codigo) {
        return citaDAO.buscarPorCodigo(codigo);
    }

    public Cita[] listarOrdenadasPorFecha() {
        Cita[] datos = citaDAO.listarTodos();
        OrdenamientosBusqueda.ordenarCitasPorFecha(datos);
        return datos;
    }

    public boolean existeCruce(String codigoMedico, String codigoConsultorio, String fecha, String hora, String codigoExcluir) {
        return citaDAO.existeCruce(codigoMedico, codigoConsultorio, fecha, hora, codigoExcluir);
    }

    public int totalCitas() {
        return citaDAO.total();
    }

    public boolean cambiarEstado(String codigo, EstadoCita estado) {
        return citaDAO.actualizarEstado(codigo, estado);
    }

    public boolean actualizarCita(Cita cita) {
        return citaDAO.actualizar(cita);
    }

    public boolean eliminarCita(String codigo) {
        return citaDAO.eliminar(codigo);
    }
}
