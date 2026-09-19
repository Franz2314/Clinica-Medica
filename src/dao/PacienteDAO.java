package dao;

import modelos.Paciente;

public interface PacienteDAO {
    void guardar(Paciente paciente);
    boolean actualizar(Paciente paciente);
    boolean eliminar(String codigo);
    Paciente buscarPorCodigo(String codigo);
    Paciente buscarPorDni(String dni);
    Paciente[] listarTodos();
    int total();
    Paciente[] obtenerInorden();
    Paciente[] obtenerPreorden();
    Paciente[] obtenerPostorden();
}
