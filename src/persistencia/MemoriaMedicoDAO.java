package persistencia;

import dao.MedicoDAO;
import estructuras.ListaEnlazada;
import modelos.Medico;

public class MemoriaMedicoDAO implements MedicoDAO {
    private final ListaEnlazada<Medico> medicos = new ListaEnlazada<>();

    @Override
    public void guardar(Medico medico) {
        medicos.agregar(medico);
    }

    @Override
    public boolean actualizar(Medico medico) {
        for (int i = 0; i < medicos.tamanio(); i++) {
            if (medicos.obtener(i).getCodigo().equalsIgnoreCase(medico.getCodigo())) {
                medicos.reemplazar(i, medico);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < medicos.tamanio(); i++) {
            if (medicos.obtener(i).getCodigo().equalsIgnoreCase(codigo)) {
                medicos.eliminar(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Medico buscarPorCodigo(String codigo) {
        for (int i = 0; i < medicos.tamanio(); i++) {
            Medico medico = medicos.obtener(i);
            if (medico.getCodigo().equalsIgnoreCase(codigo)) {
                return medico;
            }
        }
        return null;
    }

    @Override
    public Medico[] listarTodos() {
        Medico[] datos = new Medico[medicos.tamanio()];
        for (int i = 0; i < medicos.tamanio(); i++) {
            datos[i] = medicos.obtener(i);
        }
        return datos;
    }

    @Override
    public int total() {
        return medicos.tamanio();
    }
}
