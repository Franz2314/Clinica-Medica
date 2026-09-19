package servicios;

import dao.MedicoDAO;
import modelos.Especialidad;
import modelos.Medico;
import utilidades.OrdenamientosBusqueda;

public class MedicoServicio {
    private final MedicoDAO medicoDAO;
    private int correlativo;

    public MedicoServicio(MedicoDAO medicoDAO) {
        this.medicoDAO = medicoDAO;
        int max = 0;
        for (Medico m : medicoDAO.listarTodos()) {
            String codigo = m.getCodigo();
            if (codigo != null && codigo.startsWith("MED")) {
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

    public Medico registrarMedico(String nombres, String apellidos, String telefono, String cmp, Especialidad especialidad,
            String correo, String turno) {
        String codigo = String.format("MED%03d", correlativo++);
        Medico medico = new Medico(codigo, nombres, apellidos, telefono, cmp, especialidad, correo, turno);
        medicoDAO.guardar(medico);
        return medico;
    }

    public Medico buscarPorCodigo(String codigo) {
        return medicoDAO.buscarPorCodigo(codigo);
    }

    public Medico[] obtenerMedicosComoArreglo() {
        return medicoDAO.listarTodos();
    }

    public Medico buscarPorCmpLineal(String cmp) {
        Medico[] datos = obtenerMedicosComoArreglo();
        int indice = OrdenamientosBusqueda.busquedaLinealMedicoPorCmp(datos, cmp);
        return indice >= 0 ? datos[indice] : null;
    }

    public Medico buscarPorCmpBinaria(String cmp) {
        Medico[] datos = obtenerMedicosComoArreglo().clone();
        int indice = OrdenamientosBusqueda.busquedaBinariaMedicoPorCmp(datos, cmp);
        return indice >= 0 ? datos[indice] : null;
    }

    public Medico[] listarOrdenadosPorNombre() {
        Medico[] datos = medicoDAO.listarTodos();
        OrdenamientosBusqueda.ordenarMedicosPorNombre(datos);
        return datos;
    }

    public Medico[] listarOrdenadosPorCmp() {
        Medico[] datos = medicoDAO.listarTodos();
        OrdenamientosBusqueda.ordenarMedicosPorCmpSeleccion(datos);
        return datos;
    }

    public int totalMedicos() {
        return medicoDAO.total();
    }

    public boolean actualizarMedico(String codigo, String nombres, String apellidos, String telefono, String cmp,
            Especialidad especialidad, String correo, String turno) {
        Medico medico = medicoDAO.buscarPorCodigo(codigo);
        if (medico == null) {
            return false;
        }
        medico.setNombres(nombres);
        medico.setApellidos(apellidos);
        medico.setTelefono(telefono);
        medico.setCmp(cmp);
        medico.setEspecialidad(especialidad);
        medico.setCorreo(correo);
        medico.setTurno(turno);
        return medicoDAO.actualizar(medico);
    }

    public boolean eliminarMedico(String codigo) {
        return medicoDAO.eliminar(codigo);
    }
}
