package persistencia;

import java.util.ArrayList;
import java.util.List;
import modelos.Medicamento;

/**
 * Gestor de almacenamiento en memoria para medicamentos.
 * Implementa las operaciones de creacion, lectura, actualizacion y eliminacion.
 */
public class MemoriaMedicamentoDAO implements dao.MedicamentoDAO {

    private final List<Medicamento> medicamentos = new ArrayList<>();
    private int contador = 1;

    public MemoriaMedicamentoDAO() {
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        guardar(new Medicamento("MED001", "Paracetamol 500mg", "Analgesico", "Para el dolor y la fiebre", 3.50, 100));
        guardar(new Medicamento("MED002", "Amoxicilina 500mg", "Antibiotico", "Antibiotico de amplio espectro", 12.00, 60));
        guardar(new Medicamento("MED003", "Ibuprofeno 400mg", "Antiinflamatorio", "Para inflamacion and dolor", 5.80, 80));
        guardar(new Medicamento("MED004", "Loratadina 10mg", "Antialergico", "Para alergias y rinitis", 8.50, 45));
        guardar(new Medicamento("MED005", "Omeprazol 20mg", "Gastroprotector", "Para gastritis y acidez", 9.00, 70));
        guardar(new Medicamento("MED006", "Metformina 850mg", "Antidiabetico", "Control de glucosa en sangre", 6.50, 55));
    }

    // Registra un nuevo medicamento existente
    public void guardar(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    // Crea y registra un nuevo medicamento
    public Medicamento registrar(String nombre, String categoria, String descripcion, double precio, int stock) {
        String codigo = "MED" + String.format("%03d", contador + medicamentos.size());
        Medicamento m = new Medicamento(codigo, nombre, categoria, descripcion, precio, stock);
        medicamentos.add(m);
        return m;
    }

    // Retorna todos los medicamentos
    public List<Medicamento> listarTodos() {
        return new ArrayList<>(medicamentos);
    }

    // Busca un medicamento por su codigo
    public Medicamento buscarPorCodigo(String codigo) {
        for (Medicamento m : medicamentos) {
            if (m.getCodigo().equalsIgnoreCase(codigo)) return m;
        }
        return null;
    }

    // Busca medicamentos por texto en nombre, categoria o codigo
    public List<Medicamento> buscarPorTexto(String texto) {
        String filtro = texto == null ? "" : texto.trim().toLowerCase();
        List<Medicamento> resultado = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            if (filtro.isEmpty()
                    || m.getNombre().toLowerCase().contains(filtro)
                    || m.getCategoria().toLowerCase().contains(filtro)
                    || m.getCodigo().toLowerCase().contains(filtro)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    // Actualiza los datos de un medicamento existente
    public boolean actualizar(String codigo, String nombre, String categoria, String descripcion, double precio, int stock) {
        for (Medicamento m : medicamentos) {
            if (m.getCodigo().equalsIgnoreCase(codigo)) {
                m.setNombre(nombre);
                m.setCategoria(categoria);
                m.setDescripcion(descripcion);
                m.setPrecio(precio);
                m.setStock(stock);
                return true;
            }
        }
        return false;
    }

    // Elimina un medicamento por su codigo
    public boolean eliminar(String codigo) {
        return medicamentos.removeIf(m -> m.getCodigo().equalsIgnoreCase(codigo));
    }

    // Retorna la cantidad total de medicamentos
    public int total() {
        return medicamentos.size();
    }
}
