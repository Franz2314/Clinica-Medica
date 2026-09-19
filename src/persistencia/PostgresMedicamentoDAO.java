package persistencia;

import dao.MedicamentoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Medicamento;

public class PostgresMedicamentoDAO implements MedicamentoDAO {

    @Override
    public List<Medicamento> listarTodos() {
        List<Medicamento> list = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos ORDER BY codigo ASC";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los medicamentos.", e);
        }
        return list;
    }

    @Override
    public Medicamento buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM medicamentos WHERE codigo = ?";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el medicamento por codigo.", e);
        }
        return null;
    }

    @Override
    public List<Medicamento> buscarPorTexto(String texto) {
        String filtro = (texto == null ? "" : texto.trim().toLowerCase());
        if (filtro.isEmpty()) {
            return listarTodos();
        }
        List<Medicamento> list = new ArrayList<>();
        String sql = "SELECT * FROM medicamentos WHERE LOWER(nombre) LIKE ? OR LOWER(categoria) LIKE ? OR LOWER(codigo) LIKE ? ORDER BY codigo ASC";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            String pattern = "%" + filtro + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron buscar los medicamentos.", e);
        }
        return list;
    }

    @Override
    public Medicamento registrar(String nombre, String categoria, String descripcion, double precio, int stock) {
        String codigo = generarSiguienteCodigo();
        String sql = "INSERT INTO medicamentos (codigo, nombre, categoria, descripcion, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, categoria);
            ps.setString(4, descripcion);
            ps.setDouble(5, precio);
            ps.setInt(6, stock);
            ps.executeUpdate();
            return new Medicamento(codigo, nombre, categoria, descripcion, precio, stock);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo registrar el medicamento.", e);
        }
    }

    @Override
    public boolean actualizar(String codigo, String nombre, String categoria, String descripcion, double precio, int stock) {
        String sql = "UPDATE medicamentos SET nombre = ?, categoria = ?, descripcion = ?, precio = ?, stock = ? WHERE codigo = ?";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, categoria);
            ps.setString(3, descripcion);
            ps.setDouble(4, precio);
            ps.setInt(5, stock);
            ps.setString(6, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el medicamento.", e);
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM medicamentos WHERE codigo = ?";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el medicamento.", e);
        }
    }

    @Override
    public int total() {
        String sql = "SELECT COUNT(*) FROM medicamentos";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo contar los medicamentos.", e);
        }
    }

    private String generarSiguienteCodigo() {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(codigo FROM 4) AS INTEGER)), 0) + 1 FROM medicamentos";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            int nextId = rs.getInt(1);
            return "MED" + String.format("%03d", nextId);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo generar el siguiente codigo para el medicamento.", e);
        }
    }

    private Medicamento map(ResultSet rs) throws SQLException {
        return new Medicamento(
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getString("descripcion"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }
}
