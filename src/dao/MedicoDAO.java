package dao;

import modelos.Medico;

public interface MedicoDAO {
    void guardar(Medico medico);
    boolean actualizar(Medico medico);
    boolean eliminar(String codigo);
    Medico buscarPorCodigo(String codigo);
    Medico[] listarTodos();
    int total();
}
