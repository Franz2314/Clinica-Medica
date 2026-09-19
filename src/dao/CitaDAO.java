package dao;

import modelos.Cita;
import modelos.EstadoCita;

public interface CitaDAO {
    void guardar(Cita cita);
    boolean actualizar(Cita cita);
    boolean eliminar(String codigo);
    Cita buscarPorCodigo(String codigo);
    Cita[] listarTodos();
    boolean existeCruce(String codigoMedico, String codigoConsultorio, String fecha, String hora, String codigoExcluir);
    int total();
    boolean actualizarEstado(String codigo, EstadoCita estado);
}
