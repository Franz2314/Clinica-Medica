package persistencia;

import dao.ConsultorioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Consultorio;
import modelos.Especialidad;

public class PostgresConsultorioDAO implements ConsultorioDAO {
    @Override
    public void guardar(Consultorio consultorio) {
        String sql = """
                INSERT INTO consultorios (codigo, nombre, piso, especialidad, disponible)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, consultorio.getCodigo());
            ps.setString(2, consultorio.getNombre());
            ps.setInt(3, consultorio.getPiso());
            ps.setString(4, consultorio.getEspecialidad().name());
            ps.setBoolean(5, consultorio.isDisponible());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo guardar el consultorio.", e);
        }
    }

    @Override
    public boolean actualizar(Consultorio consultorio) {
        String sql = """
                UPDATE consultorios
                   SET nombre = ?, piso = ?, especialidad = ?, disponible = ?
                 WHERE codigo = ?
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, consultorio.getNombre());
            ps.setInt(2, consultorio.getPiso());
            ps.setString(3, consultorio.getEspecialidad().name());
            ps.setBoolean(4, consultorio.isDisponible());
            ps.setString(5, consultorio.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el consultorio.", e);
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM consultorios WHERE codigo = ?")) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el consultorio.", e);
        }
    }

    @Override
    public Consultorio buscarPorCodigo(String codigo) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM consultorios WHERE codigo = ?")) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el consultorio.", e);
        }
    }

    @Override
    public Consultorio[] listarTodos() {
        List<Consultorio> datos = new ArrayList<>();
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM consultorios");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                datos.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los consultorios.", e);
        }
        return datos.toArray(Consultorio[]::new);
    }

    @Override
    public Consultorio[] listarDisponiblesPorEspecialidad(Especialidad especialidad) {
        List<Consultorio> datos = new ArrayList<>();
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM consultorios WHERE disponible = TRUE AND especialidad = ?")) {
            ps.setString(1, especialidad.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    datos.add(map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los consultorios disponibles.", e);
        }
        return datos.toArray(Consultorio[]::new);
    }

    @Override
    public boolean esCompatible(String codigoConsultorio, Especialidad especialidad) {
        Consultorio consultorio = buscarPorCodigo(codigoConsultorio);
        return consultorio != null && consultorio.getEspecialidad() == especialidad;
    }

    private Consultorio map(ResultSet rs) throws SQLException {
        Consultorio consultorio = new Consultorio(
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getInt("piso"),
                Especialidad.valueOf(rs.getString("especialidad"))
        );
        consultorio.setDisponible(rs.getBoolean("disponible"));
        return consultorio;
    }
}
