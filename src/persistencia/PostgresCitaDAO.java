package persistencia;

import dao.CitaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Cita;
import modelos.Consultorio;
import modelos.Especialidad;
import modelos.EstadoCita;
import modelos.Medico;
import modelos.Paciente;

public class PostgresCitaDAO implements CitaDAO {
    @Override
    public void guardar(Cita cita) {
        String sql = """
                INSERT INTO citas (codigo, codigo_paciente, codigo_medico, codigo_consultorio, fecha, hora, motivo, costo, estado)
                VALUES (?, ?, ?, ?, CAST(? AS DATE), CAST(? AS TIME), ?, ?, ?)
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            llenar(ps, cita, false);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo guardar la cita.", e);
        }
    }

    @Override
    public boolean actualizar(Cita cita) {
        String sql = """
                UPDATE citas
                   SET codigo_paciente = ?, codigo_medico = ?, codigo_consultorio = ?,
                       fecha = CAST(? AS DATE), hora = CAST(? AS TIME), motivo = ?, costo = ?, estado = ?
                 WHERE codigo = ?
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            llenar(ps, cita, true);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar la cita.", e);
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM citas WHERE codigo = ?")) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar la cita.", e);
        }
    }

    @Override
    public Cita buscarPorCodigo(String codigo) {
        String sql = selectBase() + " WHERE c.codigo = ?";
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar la cita.", e);
        }
    }

    @Override
    public Cita[] listarTodos() {
        List<Cita> datos = new ArrayList<>();
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(selectBase());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                datos.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar las citas.", e);
        }
        return datos.toArray(Cita[]::new);
    }

    @Override
    public boolean existeCruce(String codigoMedico, String codigoConsultorio, String fecha, String hora, String codigoExcluir) {
        String sql = """
                SELECT COUNT(*)
                  FROM citas
                 WHERE fecha = CAST(? AS DATE)
                   AND hora = CAST(? AS TIME)
                   AND estado <> 'CANCELADA'
                   AND (codigo_medico = ? OR codigo_consultorio = ?)
                   AND (? IS NULL OR codigo <> ?)
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setString(3, codigoMedico);
            ps.setString(4, codigoConsultorio);
            ps.setString(5, codigoExcluir);
            ps.setString(6, codigoExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo validar cruce de citas.", e);
        }
    }

    @Override
    public int total() {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM citas");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo contar citas.", e);
        }
    }

    @Override
    public boolean actualizarEstado(String codigo, EstadoCita estado) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("UPDATE citas SET estado = ? WHERE codigo = ?")) {
            ps.setString(1, estado.name());
            ps.setString(2, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el estado de la cita.", e);
        }
    }

    private void llenar(PreparedStatement ps, Cita cita, boolean update) throws SQLException {
        int i = 1;
        if (!update) {
            ps.setString(i++, cita.getCodigo());
        }
        ps.setString(i++, cita.getPaciente().getCodigo());
        ps.setString(i++, cita.getMedico().getCodigo());
        ps.setString(i++, cita.getConsultorio().getCodigo());
        ps.setString(i++, cita.getFecha());
        ps.setString(i++, cita.getHora());
        ps.setString(i++, cita.getMotivo());
        ps.setDouble(i++, cita.getCostoConsulta());
        ps.setString(i++, cita.getEstado().name());
        if (update) {
            ps.setString(i, cita.getCodigo());
        }
    }

    private String selectBase() {
        return """
                SELECT c.codigo,
                       c.fecha::text AS fecha,
                       TO_CHAR(c.hora, 'HH24:MI') AS hora,
                       c.motivo,
                       c.costo,
                       c.estado,
                       p.codigo AS p_codigo,
                       p.nombres AS p_nombres,
                       p.apellidos AS p_apellidos,
                       p.telefono AS p_telefono,
                       p.dni AS p_dni,
                       p.edad AS p_edad,
                       p.genero AS p_genero,
                       p.tipo_sangre AS p_tipo_sangre,
                       p.alergias AS p_alergias,
                       p.correo AS p_correo,
                       p.tiene_sis AS p_tiene_sis,
                       p.numero_sis AS p_numero_sis,
                       m.codigo AS m_codigo,
                       m.nombres AS m_nombres,
                       m.apellidos AS m_apellidos,
                       m.telefono AS m_telefono,
                       m.cmp AS m_cmp,
                       m.especialidad AS m_especialidad,
                       m.correo AS m_correo,
                       m.turno AS m_turno,
                       o.codigo AS o_codigo,
                       o.nombre AS o_nombre,
                       o.piso AS o_piso,
                       o.especialidad AS o_especialidad,
                       o.disponible AS o_disponible
                  FROM citas c
                  JOIN pacientes p ON p.codigo = c.codigo_paciente
                  JOIN medicos m ON m.codigo = c.codigo_medico
                  JOIN consultorios o ON o.codigo = c.codigo_consultorio
                """;
    }

    private Cita map(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente(
                rs.getString("p_codigo"),
                rs.getString("p_nombres"),
                rs.getString("p_apellidos"),
                rs.getString("p_telefono"),
                rs.getString("p_dni"),
                rs.getInt("p_edad"),
                rs.getString("p_genero"),
                rs.getString("p_tipo_sangre"),
                rs.getString("p_alergias"),
                rs.getString("p_correo"),
                rs.getBoolean("p_tiene_sis"),
                rs.getString("p_numero_sis")
        );
        Medico medico = new Medico(
                rs.getString("m_codigo"),
                rs.getString("m_nombres"),
                rs.getString("m_apellidos"),
                rs.getString("m_telefono"),
                rs.getString("m_cmp"),
                Especialidad.valueOf(rs.getString("m_especialidad")),
                rs.getString("m_correo"),
                rs.getString("m_turno")
        );
        Consultorio consultorio = new Consultorio(
                rs.getString("o_codigo"),
                rs.getString("o_nombre"),
                rs.getInt("o_piso"),
                Especialidad.valueOf(rs.getString("o_especialidad"))
        );
        consultorio.setDisponible(rs.getBoolean("o_disponible"));
        Cita cita = new Cita(
                rs.getString("codigo"),
                paciente,
                medico,
                consultorio,
                rs.getString("fecha"),
                rs.getString("hora"),
                rs.getString("motivo"),
                rs.getDouble("costo")
        );
        cita.setEstado(EstadoCita.valueOf(rs.getString("estado")));
        return cita;
    }
}
