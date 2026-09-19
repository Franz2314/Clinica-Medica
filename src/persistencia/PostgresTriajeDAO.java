package persistencia;

import dao.TriajeDAO;
import estructuras.Cola;
import estructuras.ColaEnlazada;
import estructuras.ColaPrioridad;
import modelos.Paciente;
import modelos.RegistroTriaje;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PostgresTriajeDAO implements TriajeDAO {

    private boolean usarPrioridad = false;

    private final Comparator<RegistroTriaje> comparadorPrioridad = (t1, t2) -> {
        int p1 = obtenerValorPrioridad(t1.getNivelPrioridad());
        int p2 = obtenerValorPrioridad(t2.getNivelPrioridad());
        return Integer.compare(p1, p2);
    };

    private int obtenerValorPrioridad(String nivel) {
        if (nivel == null) return 3;
        switch (nivel.toUpperCase()) {
            case "URGENTE": return 1;
            case "PRIORITARIO": return 2;
            case "NORMAL":
            default: return 3;
        }
    }

    @Override
    public void setModoPrioridad(boolean usarPrioridad) {
        this.usarPrioridad = usarPrioridad;
    }

    @Override
    public boolean isModoPrioridad() {
        return usarPrioridad;
    }

    @Override
    public RegistroTriaje registrar(Paciente paciente, String fecha, double peso, double talla,
                                    double temperatura, String presionArterial, int frecuenciaCardiaca,
                                    String nivelPrioridad) {
        String codigo = obtenerSiguienteCodigo();
        RegistroTriaje triaje = new RegistroTriaje(
                codigo,
                paciente.getCodigo(),
                paciente.getNombreCompleto(),
                fecha,
                peso,
                talla,
                temperatura,
                presionArterial,
                frecuenciaCardiaca,
                nivelPrioridad
        );

        String sql = """
                INSERT INTO triaje (codigo, codigo_paciente, nombre_paciente, fecha, peso, talla,
                                    temperatura, presion_arterial, frecuencia_cardiaca, nivel_prioridad, atendido)
                VALUES (?, ?, ?, CAST(? AS DATE), ?, ?, ?, ?, ?, ?, FALSE)
                """;

        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, triaje.getCodigo());
            ps.setString(2, triaje.getCodigoPaciente());
            ps.setString(3, triaje.getNombrePaciente());
            ps.setString(4, triaje.getFecha());
            ps.setDouble(5, triaje.getPeso());
            ps.setDouble(6, triaje.getTalla());
            ps.setDouble(7, triaje.getTemperatura());
            ps.setString(8, triaje.getPresionArterial());
            ps.setInt(9, triaje.getFrecuenciaCardiaca());
            ps.setString(10, triaje.getNivelPrioridad());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar triaje en la base de datos.", e);
        }

        return triaje;
    }

    @Override
    public RegistroTriaje atenderSiguiente() {
        Cola<RegistroTriaje> cola = cargarColaDesdeBD();
        if (cola.estaVacia()) return null;
        RegistroTriaje siguiente = cola.dequeue();

        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("UPDATE triaje SET atendido = TRUE WHERE codigo = ?")) {
            ps.setString(1, siguiente.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado de atención del triaje.", e);
        }

        return siguiente;
    }

    @Override
    public RegistroTriaje verSiguiente() {
        Cola<RegistroTriaje> cola = cargarColaDesdeBD();
        if (cola.estaVacia()) return null;
        return cola.peek();
    }

    @Override
    public RegistroTriaje[] listarHistorial() {
        List<RegistroTriaje> historial = new ArrayList<>();
        String sql = """
                SELECT codigo, codigo_paciente, nombre_paciente, fecha::text AS fecha, peso, talla,
                       temperatura, presion_arterial, frecuencia_cardiaca, nivel_prioridad
                FROM triaje
                ORDER BY codigo ASC
                """;

        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                historial.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar historial de triajes.", e);
        }

        return historial.toArray(new RegistroTriaje[0]);
    }

    @Override
    public RegistroTriaje[] listarEnEspera() {
        Cola<RegistroTriaje> cola = cargarColaDesdeBD();
        return cola.toArray(new RegistroTriaje[0]);
    }

    @Override
    public int totalEnEspera() {
        Cola<RegistroTriaje> cola = cargarColaDesdeBD();
        return cola.tamanio();
    }

    @Override
    public void eliminarPorPaciente(String codigoPaciente) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM triaje WHERE codigo_paciente = ?")) {
            ps.setString(1, codigoPaciente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar triaje por código de paciente.", e);
        }
    }

    private Cola<RegistroTriaje> cargarColaDesdeBD() {
        Cola<RegistroTriaje> cola;
        if (usarPrioridad) {
            cola = new ColaPrioridad<>(comparadorPrioridad);
        } else {
            cola = new ColaEnlazada<>();
        }

        String sql = """
                SELECT codigo, codigo_paciente, nombre_paciente, fecha::text AS fecha, peso, talla,
                       temperatura, presion_arterial, frecuencia_cardiaca, nivel_prioridad
                FROM triaje
                WHERE atendido = FALSE
                ORDER BY codigo ASC
                """;

        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cola.enqueue(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar la cola de triaje desde la base de datos.", e);
        }

        return cola;
    }

    private RegistroTriaje map(ResultSet rs) throws SQLException {
        return new RegistroTriaje(
                rs.getString("codigo"),
                rs.getString("codigo_paciente"),
                rs.getString("nombre_paciente"),
                rs.getString("fecha"),
                rs.getDouble("peso"),
                rs.getDouble("talla"),
                rs.getDouble("temperatura"),
                rs.getString("presion_arterial"),
                rs.getInt("frecuencia_cardiaca"),
                rs.getString("nivel_prioridad")
        );
    }

    private synchronized String obtenerSiguienteCodigo() {
        int max = 0;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT codigo FROM triaje");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String cod = rs.getString(1);
                if (cod != null && cod.startsWith("TR")) {
                    try {
                        int num = Integer.parseInt(cod.substring(2));
                        if (num > max) {
                            max = num;
                        }
                    } catch (NumberFormatException e) {
                        // Ignorar códigos que no sigan el formato esperado
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al generar el código correlativo de triaje.", e);
        }
        return String.format("TR%04d", max + 1);
    }
}
