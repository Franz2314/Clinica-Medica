package persistencia;

import dao.CitaDAO;
import dao.ConsultorioDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;

public class ClinicaMemoria {
    private final PacienteDAO pacienteDAO = new MemoriaPacienteDAO();
    private final MedicoDAO medicoDAO = new MemoriaMedicoDAO();
    private final ConsultorioDAO consultorioDAO = new MemoriaConsultorioDAO();
    private final CitaDAO citaDAO = new MemoriaCitaDAO();

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
