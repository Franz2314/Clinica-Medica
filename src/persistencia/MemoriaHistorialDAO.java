package persistencia;

import estructuras.ListaDoblementeEnlazada;
import java.util.HashMap;
import java.util.Map;
import modelos.ConsultaClinica;

/**
 * Gestor de almacenamiento en memoria para el historial clinico.
 * Guarda las consultas de cada paciente utilizando listas doblemente enlazadas.
 */
public class MemoriaHistorialDAO {

    private final Map<String, ListaDoblementeEnlazada<ConsultaClinica>> historialPorPaciente;

    public MemoriaHistorialDAO() {
        historialPorPaciente = new HashMap<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        String[][] datosIniciales = {
            {"PAC001", "Ana Torres",      "2026-02-10", "Dr. Carlos Perez",    "Cardiologia",
                "Hipertension leve",       "Losartan 50mg, Amlodipino 5mg",    "Control en 30 dias"},
            {"PAC001", "Ana Torres",      "2026-03-15", "Dr. Carlos Perez",    "Cardiologia",
                "Hipertension controlada", "Losartan 50mg",                    "Reduccion de dosis"},
            {"PAC001", "Ana Torres",      "2026-04-20", "Dr. Carlos Perez",    "Cardiologia",
                "Presion normalizada",     "Losartan 25mg",                    "Continuar tratamiento"},

            {"PAC002", "Luis Rojas",      "2026-01-08", "Dra. Maria Soto",     "Pediatria",
                "Resfriado comun",         "Paracetamol 500mg, Vitamina C",    "Reposo 3 dias"},
            {"PAC002", "Luis Rojas",      "2026-03-22", "Dra. Maria Soto",     "Pediatria",
                "Faringitis aguda",        "Amoxicilina 500mg, Ibuprofeno",    "Antibiotico 7 dias"},
            {"PAC002", "Luis Rojas",      "2026-05-01", "Dra. Maria Soto",     "Pediatria",
                "Control rutinario",       "Multivitaminicos",                 "Paciente en buen estado"},

            {"PAC003", "Carla Mendoza",   "2026-02-14", "Dr. Julio Diaz",      "Dermatologia",
                "Dermatitis alergica",     "Hidrocortisona crema, Loratadina", "Evitar alergenos"},
            {"PAC003", "Carla Mendoza",   "2026-04-05", "Dr. Julio Diaz",      "Dermatologia",
                "Mejoria significativa",   "Loratadina 10mg",                  "Mantenimiento"},

            {"PAC004", "Diego Castro",    "2026-03-10", "Dra. Andrea Ramos",   "Medicina General",
                "Gastritis aguda",         "Omeprazol 20mg, Antiacidos",       "Dieta blanda 1 semana"},
            {"PAC004", "Diego Castro",    "2026-05-05", "Dra. Andrea Ramos",   "Medicina General",
                "Gastritis en remision",   "Omeprazol 20mg",                   "Continuar dieta"},

            {"PAC005", "Rosa Chavez",     "2026-01-20", "Dr. Roberto Leiva",   "Traumatologia",
                "Lumbalgia mecanica",      "Ibuprofeno 400mg, Miorrelajante",  "Fisioterapia 2x semana"},
            {"PAC005", "Rosa Chavez",     "2026-03-18", "Dr. Roberto Leiva",   "Traumatologia",
                "Mejoria parcial",         "Paracetamol 500mg",               "Continuar fisioterapia"},
            {"PAC005", "Rosa Chavez",     "2026-05-10", "Dr. Roberto Leiva",   "Traumatologia",
                "Alta medica",             "Ninguno",                          "Recuperacion completa"},
        };

        for (String[] d : datosIniciales) {
            registrarConsulta(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7]);
        }
    }

    // Registra una nueva consulta en el historial del paciente
    public ConsultaClinica registrarConsulta(String codigoPaciente, String nombrePaciente,
            String fecha, String nombreMedico, String especialidad,
            String diagnostico, String medicamentos, String observaciones) {

        ListaDoblementeEnlazada<ConsultaClinica> lista =
                historialPorPaciente.computeIfAbsent(codigoPaciente,
                        k -> new ListaDoblementeEnlazada<>());

        ConsultaClinica consulta = new ConsultaClinica(
                codigoPaciente, nombrePaciente, fecha,
                nombreMedico, especialidad,
                diagnostico, medicamentos, observaciones);
        lista.agregarAlFinal(consulta);
        return consulta;
    }

    // Retorna la consulta mas reciente registrada
    public ConsultaClinica obtenerUltimaConsulta(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        if (lista == null || lista.estaVacia()) return null;
        lista.irAlFinal();
        return lista.obtenerActual();
    }

    // Navega y retorna la consulta anterior
    public ConsultaClinica irAConsultaAnterior(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        if (lista == null) return null;
        return lista.anterior();
    }

    // Navega y retorna la consulta siguiente
    public ConsultaClinica irAConsultaSiguiente(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        if (lista == null) return null;
        return lista.siguiente();
    }

    // Verifica si hay una consulta previa en el cursor
    public boolean hayConsultaAnterior(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        return lista != null && lista.hayAnterior();
    }

    // Verifica si hay una consulta posterior en el cursor
    public boolean hayConsultaSiguiente(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        return lista != null && lista.haySiguiente();
    }

    // Retorna la cantidad total de consultas del paciente
    public int totalConsultas(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        return (lista == null) ? 0 : lista.tamanio();
    }

    // Verifica si el paciente posee historial registrado
    public boolean tieneHistorial(String codigoPaciente) {
        ListaDoblementeEnlazada<ConsultaClinica> lista = historialPorPaciente.get(codigoPaciente);
        return lista != null && !lista.estaVacia();
    }

    public void eliminarHistorial(String codigoPaciente) {
        historialPorPaciente.remove(codigoPaciente);
    }
}
