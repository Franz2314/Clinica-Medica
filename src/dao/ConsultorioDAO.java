package dao;

import modelos.Consultorio;
import modelos.Especialidad;

public interface ConsultorioDAO {
    void guardar(Consultorio consultorio);
    boolean actualizar(Consultorio consultorio);
    boolean eliminar(String codigo);
    Consultorio buscarPorCodigo(String codigo);
    Consultorio[] listarTodos();
    Consultorio[] listarDisponiblesPorEspecialidad(Especialidad especialidad);
    boolean esCompatible(String codigoConsultorio, Especialidad especialidad);
}
