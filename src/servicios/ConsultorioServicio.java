package servicios;

import dao.ConsultorioDAO;
import modelos.Consultorio;
import modelos.Especialidad;

public class ConsultorioServicio {
    private final ConsultorioDAO consultorioDAO;
    private int correlativo;

    public ConsultorioServicio(ConsultorioDAO consultorioDAO) {
        this.consultorioDAO = consultorioDAO;
        int max = 0;
        for (Consultorio c : consultorioDAO.listarTodos()) {
            String codigo = c.getCodigo();
            if (codigo != null && codigo.startsWith("CON")) {
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

    public Consultorio registrarConsultorio(String nombre, int piso, Especialidad especialidad) {
        String codigo = String.format("CON%03d", correlativo++);
        Consultorio consultorio = new Consultorio(codigo, nombre, piso, especialidad);
        consultorioDAO.guardar(consultorio);
        return consultorio;
    }

    public Consultorio buscarPorCodigo(String codigo) {
        return consultorioDAO.buscarPorCodigo(codigo);
    }

    public Consultorio[] listarConsultorios() {
        return consultorioDAO.listarTodos();
    }

    public Consultorio[] listarDisponiblesPorEspecialidad(Especialidad especialidad) {
        return consultorioDAO.listarDisponiblesPorEspecialidad(especialidad);
    }

    public boolean esCompatible(String codigoConsultorio, Especialidad especialidad) {
        return consultorioDAO.esCompatible(codigoConsultorio, especialidad);
    }

    public boolean actualizarConsultorio(String codigo, String nombre, int piso, Especialidad especialidad, boolean disponible) {
        Consultorio consultorio = consultorioDAO.buscarPorCodigo(codigo);
        if (consultorio == null) {
            return false;
        }
        consultorio.setNombre(nombre);
        consultorio.setPiso(piso);
        consultorio.setEspecialidad(especialidad);
        consultorio.setDisponible(disponible);
        return consultorioDAO.actualizar(consultorio);
    }

    public boolean eliminarConsultorio(String codigo) {
        return consultorioDAO.eliminar(codigo);
    }
}
