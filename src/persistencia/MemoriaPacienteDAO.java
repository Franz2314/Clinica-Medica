package persistencia;

import dao.PacienteDAO;
import estructuras.ArbolBinarioBusqueda;
import estructuras.ListaEnlazada;
import modelos.Paciente;

public class MemoriaPacienteDAO implements PacienteDAO {
    private final ListaEnlazada<Paciente> pacientes = new ListaEnlazada<>();
    private final ArbolBinarioBusqueda<String, Paciente> indicePorDni = new ArbolBinarioBusqueda<>();

    @Override
    public void guardar(Paciente paciente) {
        pacientes.agregar(paciente);
        indicePorDni.insertar(paciente.getDni(), paciente);
    }

    @Override
    public boolean actualizar(Paciente paciente) {
        for (int i = 0; i < pacientes.tamanio(); i++) {
            Paciente antiguo = pacientes.obtener(i);
            if (antiguo.getCodigo().equalsIgnoreCase(paciente.getCodigo())) {
                // Si el DNI cambió, debemos remover la clave vieja
                if (!antiguo.getDni().equals(paciente.getDni())) {
                    indicePorDni.eliminar(antiguo.getDni());
                }
                pacientes.reemplazar(i, paciente);
                indicePorDni.insertar(paciente.getDni(), paciente);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < pacientes.tamanio(); i++) {
            Paciente paciente = pacientes.obtener(i);
            if (paciente.getCodigo().equalsIgnoreCase(codigo)) {
                pacientes.eliminar(i);
                indicePorDni.eliminar(paciente.getDni());
                return true;
            }
        }
        return false;
    }

    @Override
    public Paciente buscarPorCodigo(String codigo) {
        for (int i = 0; i < pacientes.tamanio(); i++) {
            Paciente paciente = pacientes.obtener(i);
            if (paciente.getCodigo().equalsIgnoreCase(codigo)) {
                return paciente;
            }
        }
        return null;
    }

    @Override
    public Paciente buscarPorDni(String dni) {
        return indicePorDni.buscar(dni);
    }

    @Override
    public Paciente[] listarTodos() {
        Paciente[] datos = new Paciente[pacientes.tamanio()];
        for (int i = 0; i < pacientes.tamanio(); i++) {
            datos[i] = pacientes.obtener(i);
        }
        return datos;
    }

    @Override
    public int total() {
        return pacientes.tamanio();
    }

    @Override
    public Paciente[] obtenerInorden() {
        estructuras.ListaEnlazada<Paciente> lista = indicePorDni.recorridoInorden();
        Paciente[] arr = new Paciente[lista.tamanio()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lista.obtener(i);
        }
        return arr;
    }

    @Override
    public Paciente[] obtenerPreorden() {
        estructuras.ListaEnlazada<Paciente> lista = indicePorDni.recorridoPreorden();
        Paciente[] arr = new Paciente[lista.tamanio()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lista.obtener(i);
        }
        return arr;
    }

    @Override
    public Paciente[] obtenerPostorden() {
        estructuras.ListaEnlazada<Paciente> lista = indicePorDni.recorridoPostorden();
        Paciente[] arr = new Paciente[lista.tamanio()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lista.obtener(i);
        }
        return arr;
    }
}
