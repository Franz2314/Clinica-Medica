package dao;

import java.util.List;
import modelos.Medicamento;

public interface MedicamentoDAO {
    List<Medicamento> listarTodos();
    Medicamento buscarPorCodigo(String codigo);
    List<Medicamento> buscarPorTexto(String texto);
    Medicamento registrar(String nombre, String categoria, String descripcion, double precio, int stock);
    boolean actualizar(String codigo, String nombre, String categoria, String descripcion, double precio, int stock);
    boolean eliminar(String codigo);
    int total();
}
