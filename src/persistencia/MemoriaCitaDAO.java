package persistencia;

import dao.CitaDAO;
import estructuras.ListaEnlazada;
import modelos.Cita;
import modelos.EstadoCita;

public class MemoriaCitaDAO implements CitaDAO {
    private final ListaEnlazada<Cita> citas = new ListaEnlazada<>();

    @Override
    public void guardar(Cita cita) {
        citas.agregar(cita);
    }

    @Override
    public boolean actualizar(Cita cita) {
        for (int i = 0; i < citas.tamanio(); i++) {
            if (citas.obtener(i).getCodigo().equalsIgnoreCase(cita.getCodigo())) {
                citas.reemplazar(i, cita);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < citas.tamanio(); i++) {
            if (citas.obtener(i).getCodigo().equalsIgnoreCase(codigo)) {
                citas.eliminar(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Cita buscarPorCodigo(String codigo) {
        for (int i = 0; i < citas.tamanio(); i++) {
            Cita cita = citas.obtener(i);
            if (cita.getCodigo().equalsIgnoreCase(codigo)) {
                return cita;
            }
        }
        return null;
    }

    @Override
    public Cita[] listarTodos() {
        Cita[] datos = new Cita[citas.tamanio()];
        for (int i = 0; i < citas.tamanio(); i++) {
            datos[i] = citas.obtener(i);
        }
        return datos;
    }

    @Override
    public boolean existeCruce(String codigoMedico, String codigoConsultorio, String fecha, String hora, String codigoExcluir) {
        for (int i = 0; i < citas.tamanio(); i++) {
            Cita cita = citas.obtener(i);
            if (codigoExcluir != null && cita.getCodigo().equalsIgnoreCase(codigoExcluir)) {
                continue;
            }
            if (cita.getEstado() == EstadoCita.CANCELADA) {
                continue;
            }
            boolean mismaFechaHora = cita.getFecha().equals(fecha) && cita.getHora().equals(hora);
            boolean mismoMedico = cita.getMedico().getCodigo().equalsIgnoreCase(codigoMedico);
            boolean mismoConsultorio = cita.getConsultorio() != null
                    && cita.getConsultorio().getCodigo().equalsIgnoreCase(codigoConsultorio);
            if (mismaFechaHora && (mismoMedico || mismoConsultorio)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int total() {
        return citas.tamanio();
    }

    @Override
    public boolean actualizarEstado(String codigo, EstadoCita estado) {
        Cita cita = buscarPorCodigo(codigo);
        if (cita == null) {
            return false;
        }
        cita.setEstado(estado);
        return true;
    }
}
