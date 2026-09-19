package persistencia;

import dao.ConsultorioDAO;
import estructuras.ArbolBinarioBusqueda;
import estructuras.ListaEnlazada;
import modelos.Consultorio;
import modelos.Especialidad;

public class MemoriaConsultorioDAO implements ConsultorioDAO {
    private final ListaEnlazada<Consultorio> consultorios = new ListaEnlazada<>();
    private final ArbolBinarioBusqueda<String, Consultorio> indice = new ArbolBinarioBusqueda<>();

    @Override
    public void guardar(Consultorio consultorio) {
        consultorios.agregar(consultorio);
        indice.insertar(consultorio.getCodigo(), consultorio);
    }

    @Override
    public boolean actualizar(Consultorio consultorio) {
        for (int i = 0; i < consultorios.tamanio(); i++) {
            Consultorio antiguo = consultorios.obtener(i);
            if (antiguo.getCodigo().equalsIgnoreCase(consultorio.getCodigo())) {
                consultorios.reemplazar(i, consultorio);
                indice.insertar(consultorio.getCodigo(), consultorio);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < consultorios.tamanio(); i++) {
            Consultorio consultorio = consultorios.obtener(i);
            if (consultorio.getCodigo().equalsIgnoreCase(codigo)) {
                consultorios.eliminar(i);
                indice.eliminar(codigo);
                return true;
            }
        }
        return false;
    }

    @Override
    public Consultorio buscarPorCodigo(String codigo) {
        return indice.buscar(codigo);
    }

    @Override
    public Consultorio[] listarTodos() {
        Consultorio[] datos = new Consultorio[consultorios.tamanio()];
        for (int i = 0; i < consultorios.tamanio(); i++) {
            datos[i] = consultorios.obtener(i);
        }
        return datos;
    }

    @Override
    public Consultorio[] listarDisponiblesPorEspecialidad(Especialidad especialidad) {
        ListaEnlazada<Consultorio> compatibles = new ListaEnlazada<>();
        for (int i = 0; i < consultorios.tamanio(); i++) {
            Consultorio consultorio = consultorios.obtener(i);
            if (consultorio.isDisponible() && coincideEspecialidad(consultorio, especialidad)) {
                compatibles.agregar(consultorio);
            }
        }
        Consultorio[] datos = new Consultorio[compatibles.tamanio()];
        for (int i = 0; i < compatibles.tamanio(); i++) {
            datos[i] = compatibles.obtener(i);
        }
        return datos;
    }

    @Override
    public boolean esCompatible(String codigoConsultorio, Especialidad especialidad) {
        Consultorio consultorio = buscarPorCodigo(codigoConsultorio);
        return consultorio != null && coincideEspecialidad(consultorio, especialidad);
    }

    private boolean coincideEspecialidad(Consultorio consultorio, Especialidad especialidad) {
        return consultorio.getEspecialidad() == especialidad;
    }
}
