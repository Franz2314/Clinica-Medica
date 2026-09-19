package persistencia;

import dao.PacienteDAO;
import estructuras.ArbolBinarioBusqueda;
import estructuras.ListaEnlazada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Paciente;

public class PostgresPacienteDAO implements PacienteDAO {
    @Override
    public void guardar(Paciente paciente) {
        String sql = """
                INSERT INTO pacientes (codigo, nombres, apellidos, telefono, dni, edad, genero,
                                       tipo_sangre, alergias, correo, tiene_sis, numero_sis)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, paciente.getCodigo());
            ps.setString(2, paciente.getNombres());
            ps.setString(3, paciente.getApellidos());
            ps.setString(4, paciente.getTelefono());
            ps.setString(5, paciente.getDni());
            ps.setInt(6, paciente.getEdad());
            ps.setString(7, paciente.getGenero());
            ps.setString(8, paciente.getTipoSangre());
            ps.setString(9, paciente.getAlergias());
            ps.setString(10, paciente.getCorreo());
            ps.setBoolean(11, paciente.isTieneSis());
            ps.setString(12, paciente.isTieneSis() ? paciente.getNumeroSis() : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo guardar el paciente.", e);
        }
    }

    @Override
    public boolean actualizar(Paciente paciente) {
        String sql = """
                UPDATE pacientes
                   SET nombres = ?, apellidos = ?, telefono = ?, dni = ?, edad = ?, genero = ?,
                       tipo_sangre = ?, alergias = ?, correo = ?, tiene_sis = ?, numero_sis = ?
                 WHERE codigo = ?
                """;
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, paciente.getNombres());
            ps.setString(2, paciente.getApellidos());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getDni());
            ps.setInt(5, paciente.getEdad());
            ps.setString(6, paciente.getGenero());
            ps.setString(7, paciente.getTipoSangre());
            ps.setString(8, paciente.getAlergias());
            ps.setString(9, paciente.getCorreo());
            ps.setBoolean(10, paciente.isTieneSis());
            ps.setString(11, paciente.isTieneSis() ? paciente.getNumeroSis() : null);
            ps.setString(12, paciente.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo actualizar el paciente.", e);
        }
    }

    @Override
    public boolean eliminar(String codigo) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM pacientes WHERE codigo = ?")) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo eliminar el paciente.", e);
        }
    }

    @Override
    public Paciente buscarPorCodigo(String codigo) {
        return buscarUno("SELECT * FROM pacientes WHERE codigo = ?", codigo);
    }

    @Override
    public Paciente buscarPorDni(String dni) {
        return buscarUno("SELECT * FROM pacientes WHERE dni = ?", dni);
    }

    @Override
    public Paciente[] listarTodos() {
        List<Paciente> datos = new ArrayList<>();
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM pacientes");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                datos.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudieron listar los pacientes.", e);
        }
        return datos.toArray(Paciente[]::new);
    }

    @Override
    public int total() {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("SELECT COUNT(*) FROM pacientes");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo contar pacientes.", e);
        }
    }

    private Paciente buscarUno(String sql, String valor) {
        try (Connection cn = ConexionPostgres.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo buscar el paciente.", e);
        }
    }

    private Paciente map(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getString("codigo"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("telefono"),
                rs.getString("dni"),
                rs.getInt("edad"),
                rs.getString("genero"),
                rs.getString("tipo_sangre"),
                rs.getString("alergias"),
                rs.getString("correo"),
                rs.getBoolean("tiene_sis"),
                rs.getString("numero_sis")
        );
    }

    @Override
    public Paciente[] obtenerInorden() {
        ArbolBinarioBusqueda<String, Paciente> arbol = construirArbolLocal();
        return convertirToArray(arbol.recorridoInorden());
    }

    @Override
    public Paciente[] obtenerPreorden() {
        ArbolBinarioBusqueda<String, Paciente> arbol = construirArbolLocal();
        return convertirToArray(arbol.recorridoPreorden());
    }

    @Override
    public Paciente[] obtenerPostorden() {
        ArbolBinarioBusqueda<String, Paciente> arbol = construirArbolLocal();
        return convertirToArray(arbol.recorridoPostorden());
    }

    private ArbolBinarioBusqueda<String, Paciente> construirArbolLocal() {
        ArbolBinarioBusqueda<String, Paciente> arbol = new ArbolBinarioBusqueda<>();
        for (Paciente p : listarTodos()) {
            if (p.getDni() != null) {
                arbol.insertar(p.getDni(), p);
            }
        }
        return arbol;
    }

    private Paciente[] convertirToArray(ListaEnlazada<Paciente> lista) {
        Paciente[] arr = new Paciente[lista.tamanio()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = lista.obtener(i);
        }
        return arr;
    }
}
