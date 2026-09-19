package persistencia;

import estructuras.Cola;
import estructuras.ColaEnlazada;
import estructuras.ColaPrioridad;
import estructuras.ListaEnlazada;
import modelos.Paciente;
import modelos.RegistroTriaje;

/**
 * Gestor de almacenamiento en memoria para el triaje medico.
 * Utiliza estructuras de cola propias para la atencion de pacientes.
 */
public class MemoriaTriajeDAO implements dao.TriajeDAO {

    private Cola<RegistroTriaje> colaPacientes = new ColaEnlazada<>();
    private final ListaEnlazada<RegistroTriaje> historialTriajes = new ListaEnlazada<>();
    private boolean usarPrioridad = false;

    private final java.util.Comparator<RegistroTriaje> comparadorPrioridad = (t1, t2) -> {
        int p1 = obtenerValorPrioridad(t1.getNivelPrioridad());
        int p2 = obtenerValorPrioridad(t2.getNivelPrioridad());
        return Integer.compare(p1, p2);
    };

    private int obtenerValorPrioridad(String nivel) {
        if (nivel == null) return 3;
        switch (nivel.toUpperCase()) {
            case "URGENTE": return 1;
            case "PRIORITARIO": return 2;
            case "NORMAL":
            default: return 3;
        }
    }

    public void setModoPrioridad(boolean usarPrioridad) {
        if (this.usarPrioridad == usarPrioridad) return;

        RegistroTriaje[] actualEnEspera = listarEnEspera();
        
        this.usarPrioridad = usarPrioridad;
        if (usarPrioridad) {
            this.colaPacientes = new ColaPrioridad<>(comparadorPrioridad);
        } else {
            this.colaPacientes = new ColaEnlazada<>();
        }
        
        for (RegistroTriaje triaje : actualEnEspera) {
            this.colaPacientes.enqueue(triaje);
        }
    }

    public boolean isModoPrioridad() {
        return usarPrioridad;
    }

    public RegistroTriaje registrar(Paciente paciente, String fecha,
            double peso, double talla, double temperatura,
            String presionArterial, int frecuenciaCardiaca, String nivelPrioridad) {
        RegistroTriaje triaje = new RegistroTriaje(
                paciente.getCodigo(), paciente.getNombreCompleto(),
                fecha, peso, talla, temperatura, presionArterial, frecuenciaCardiaca, nivelPrioridad);
        colaPacientes.enqueue(triaje);
        historialTriajes.agregar(triaje);
        return triaje;
    }

    public RegistroTriaje atenderSiguiente() {
        if (colaPacientes.estaVacia()) return null;
        return colaPacientes.dequeue();
    }

    public RegistroTriaje verSiguiente() {
        if (colaPacientes.estaVacia()) return null;
        return colaPacientes.peek();
    }

    public RegistroTriaje[] listarHistorial() {
        RegistroTriaje[] datos = new RegistroTriaje[historialTriajes.tamanio()];
        for (int i = 0; i < datos.length; i++) {
            datos[i] = historialTriajes.obtener(i);
        }
        return datos;
    }

    public RegistroTriaje[] listarEnEspera() {
        return colaPacientes.toArray(new RegistroTriaje[0]);
    }

    public int totalEnEspera() {
        return colaPacientes.tamanio();
    }

    public void eliminarPorPaciente(String codigoPaciente) {
        RegistroTriaje[] actualEnEspera = listarEnEspera();
        if (usarPrioridad) {
            colaPacientes = new ColaPrioridad<>(comparadorPrioridad);
        } else {
            colaPacientes = new ColaEnlazada<>();
        }
        for (RegistroTriaje t : actualEnEspera) {
            if (!t.getCodigoPaciente().equalsIgnoreCase(codigoPaciente)) {
                colaPacientes.enqueue(t);
            }
        }

        for (int i = 0; i < historialTriajes.tamanio(); i++) {
            if (historialTriajes.obtener(i).getCodigoPaciente().equalsIgnoreCase(codigoPaciente)) {
                historialTriajes.eliminar(i);
                i--;
            }
        }
    }
}
