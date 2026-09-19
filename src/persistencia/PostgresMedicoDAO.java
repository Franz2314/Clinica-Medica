package persistencia;

import dao.MedicoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Especialidad;
import modelos.Medico;

public class PostgresMedicoDAO implements MedicoDAO {
    @Override
    public void guardar(Medico medico) {
        String sql = """
                INSERT INTO medicos (codigo, nombres, apellidos, telefono, cmp, especialidad, correo, turno)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, medico.getCodigo());
            ps.setString(2, medico.getNombres());
            ps.setString(3, medico.getApellidos());
            ps.setString(4, medico.getTelefono());
            ps.setString(5, medico.getCmp());
            ps.setString(6, medico.getEspecialidad().name());
            ps.setString(7, medico.getCorreo());
            ps.setString(8, medico.getTurno());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo guardar el medico.", e);
        }
    }

    @Override
    public boolean actualizar(Medico medico) {
        String sql = """
                UPDATE medicos
                   SET nombres = ?, apellidos = ?, telefono = ?, cmp = ?, especialidad = ?, correo = ?, turno = ?
                 WHERE codigo = ?
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, medico.getNombres());
            ps.setString(2, medico.getApellidos());
            ps.setString(3, medico.getTelefono());
            ps.setString(4, medico.getCmp());
            ps.setString(5, medico.getEspecialidad().name());
            ps.setString(6, medico.getCorreo());
            ps.setString(7, medico.getTurno());
            ps.setString(8, medico.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el medico.", e);
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM medicos WHERE codigo = ?")) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el medico.", e);
        }
    }

    @Override
    public Medico buscarPorCodigo(String codigo) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM medicos WHERE codigo = ?")) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el medico.", e);
        }
    }

    @Override
    public Medico[] listarTodos() {
        List<Medico> datos = new ArrayList<>();
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM medicos");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                datos.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los medicos.", e);
        }
        return datos.toArray(Medico[]::new);
    }

    @Override
    public int total() {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM medicos");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo contar medicos.", e);
        }
    }

    private Medico map(ResultSet rs) throws SQLException {
        return new Medico(
                rs.getString("codigo"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("telefono"),
                rs.getString("cmp"),
                Especialidad.valueOf(rs.getString("especialidad")),
                rs.getString("correo"),
                rs.getString("turno")
        );
    }
}
