package persistencia;

import dao.CitaDAO;
import dao.ConsultorioDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;

public class ClinicaPostgres {
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;
    private final ConsultorioDAO consultorioDAO;
    private final CitaDAO citaDAO;

    public ClinicaPostgres() {
        ConexionPostgres.validarConexionYEsquema();
        this.pacienteDAO = new PostgresPacienteDAO();
        this.medicoDAO = new PostgresMedicoDAO();
        this.consultorioDAO = new PostgresConsultorioDAO();
        this.citaDAO = new PostgresCitaDAO();
    }

    public PacienteDAO getPacienteDAO() {
        return pacienteDAO;
    }

    public MedicoDAO getMedicoDAO() {
        return medicoDAO;
    }

    public ConsultorioDAO getConsultorioDAO() {
        return consultorioDAO;
    }

    public CitaDAO getCitaDAO() {
        return citaDAO;
    }
}
