package persistencia;

import config.AppConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ConexionPostgres {
    private ConexionPostgres() {
    }

    public static Connection obtenerConexion() throws SQLException {
        AppConfig config = AppConfig.get();
        return DriverManager.getConnection(
                config.getPostgresUrl(),
                config.getPostgresUser(),
                config.getPostgresPassword()
        );
    }

    public static void validarConexionYEsquema() {
        String sql = """
                SELECT COUNT(*)
                FROM information_schema.tables
                WHERE table_schema = 'public'
                  AND table_name IN ('pacientes', 'medicos', 'consultorios', 'citas', 'triaje')
                """;
        try (Connection cn = obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            if (rs.getInt(1) < 5) {
                throw new IllegalStateException("La base PostgreSQL no tiene el esquema del AVANCE 2 (incluyendo la tabla de triaje).");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo conectar a PostgreSQL.", e);
        }
    }
}
