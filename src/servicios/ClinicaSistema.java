package servicios;

import config.AppConfig;
import config.PersistenceMode;
import java.util.ArrayList;
import java.util.List;
import modelos.Cita;
import modelos.ConsultaClinica;
import modelos.Consultorio;
import modelos.Especialidad;
import modelos.EstadoCita;
import modelos.Medico;
import modelos.Medicamento;
import modelos.Paciente;
import modelos.RegistroTriaje;
import persistencia.ClinicaMemoria;
import persistencia.ClinicaPostgres;
import persistencia.MemoriaHistorialDAO;
import persistencia.MemoriaMedicamentoDAO;
import persistencia.PostgresMedicamentoDAO;
import dao.MedicamentoDAO;
import dao.TriajeDAO;
import persistencia.MemoriaTriajeDAO;
import persistencia.PostgresTriajeDAO;
import utilidades.OrdenamientosBusqueda;

public class ClinicaSistema {
    private final PacienteServicio pacienteServicio;
    private final MedicoServicio medicoServicio;
    private final CitaServicio citaServicio;
    private final ConsultorioServicio consultorioServicio;
    private final MedicamentoDAO medicamentoDAO;
    private final TriajeDAO triajeDAO;
    // Gestor de historiales medicos
    private final MemoriaHistorialDAO historialDAO;
    private final String modoPersistencia;

    public ClinicaSistema() {
        PersistenceMode mode = AppConfig.get().getPersistenceMode();
        this.modoPersistencia = mode.name();
        if (mode == PersistenceMode.POSTGRES) {
            ClinicaPostgres postgres = new ClinicaPostgres();
            this.pacienteServicio = new PacienteServicio(postgres.getPacienteDAO());
            this.medicoServicio = new MedicoServicio(postgres.getMedicoDAO());
            this.citaServicio = new CitaServicio(postgres.getCitaDAO());
            this.consultorioServicio = new ConsultorioServicio(postgres.getConsultorioDAO());
            this.triajeDAO = new PostgresTriajeDAO();
            this.medicamentoDAO = new PostgresMedicamentoDAO();
        } else {
            ClinicaMemoria memoria = new ClinicaMemoria();
            this.pacienteServicio = new PacienteServicio(memoria.getPacienteDAO());
            this.medicoServicio = new MedicoServicio(memoria.getMedicoDAO());
            this.citaServicio = new CitaServicio(memoria.getCitaDAO());
            this.consultorioServicio = new ConsultorioServicio(memoria.getConsultorioDAO());
            this.triajeDAO = new MemoriaTriajeDAO();
            this.medicamentoDAO = new MemoriaMedicamentoDAO();
        }
        this.historialDAO = new MemoriaHistorialDAO();
    }

    public void cargarDatosIniciales() {
        boolean datosYaExisten = pacienteServicio.totalPacientes() > 0 || medicoServicio.totalMedicos() > 0
                || consultorioServicio.listarConsultorios().length > 0 || citaServicio.totalCitas() > 0;

        List<Paciente> pacientesRegistrados = new ArrayList<>();

        if (!datosYaExisten) {
            String[][] pacientes = {
                {"Ana", "Torres", "987654321", "76543210", "29", "F", "O+", "Penicilina", "ana.torres@ate.com", "true", "SIS000001"},
                {"Luis", "Rojas", "912345678", "75443322", "41", "M", "A+", "Ninguna", "luis.rojas@ate.com", "false", ""},
                {"Carla", "Mendoza", "955321478", "70112233", "35", "F", "B+", "Mariscos", "carla.mendoza@ate.com", "true", "SIS000003"},
                {"Diego", "Castro", "934567812", "74321987", "27", "M", "O-", "Ninguna", "diego.castro@ate.com", "false", ""},
                {"Rosa", "Chavez", "923456781", "73456129", "33", "F", "AB+", "Aspirina", "rosa.chavez@ate.com", "true", "SIS000005"},
                {"Miguel", "Flores", "945612378", "72893451", "46", "M", "B-", "Polen", "miguel.flores@ate.com", "false", ""},
                {"Patricia", "Lopez", "956712348", "71984562", "38", "F", "A-", "Ninguna", "patricia.lopez@ate.com", "true", "SIS000007"},
                {"Javier", "Ortiz", "967812345", "70875643", "31", "M", "O+", "Ibuprofeno", "javier.ortiz@ate.com", "false", ""},
                {"Sofia", "Navarro", "978123456", "70654328", "25", "F", "B+", "Ninguna", "sofia.navarro@ate.com", "true", "SIS000009"},
                {"Ricardo", "Salas", "981234567", "70219834", "52", "M", "A+", "Polvo", "ricardo.salas@ate.com", "false", ""},
                {"Elena", "Paredes", "989456123", "70198765", "44", "F", "O+", "Mariscos", "elena.paredes@ate.com", "true", "SIS000011"},
                {"Marco", "Vega", "922334455", "71234567", "36", "M", "AB-", "Ninguna", "marco.vega@ate.com", "false", ""},
                {"Lucia", "Herrera", "933445566", "72345678", "30", "F", "A+", "Penicilina", "lucia.herrera@ate.com", "true", "SIS000013"},
                {"Oscar", "Quispe", "944556677", "73456789", "48", "M", "B+", "Ninguna", "oscar.quispe@ate.com", "false", ""},
                {"Daniela", "Roman", "955667788", "74567890", "26", "F", "O-", "Lactosa", "daniela.roman@ate.com", "true", "SIS000015"},
                {"Fernando", "Aguilar", "966778899", "75678901", "39", "M", "A-", "Ninguna", "fernando.aguilar@ate.com", "false", ""},
                {"Miriam", "Cruz", "977889900", "76789012", "42", "F", "B-", "Aspirina", "miriam.cruz@ate.com", "true", "SIS000017"},
                {"Raul", "Valdez", "988990011", "77890123", "34", "M", "O+", "Polen", "raul.valdez@ate.com", "false", ""},
                {"Camila", "Benites", "911223344", "78901234", "28", "F", "AB+", "Ninguna", "camila.benites@ate.com", "true", "SIS000019"},
                {"Pedro", "Sanchez", "922113355", "79012345", "50", "M", "A+", "Penicilina", "pedro.sanchez@ate.com", "false", ""}
            };

            for (String[] dato : pacientes) {
                pacientesRegistrados.add(registrarPaciente(
                        dato[0], dato[1], dato[2], dato[3], Integer.parseInt(dato[4]), dato[5], dato[6], dato[7], dato[8],
                        Boolean.parseBoolean(dato[9]), dato[10]
                ));
            }

            Object[][] medicos = {
                {"Carlos", "Perez", "901111111", "CMP12345", Especialidad.CARDIOLOGIA, "cperez@ate.com", "Mañana"},
                {"Maria", "Soto", "902222222", "CMP54321", Especialidad.PEDIATRIA, "msoto@ate.com", "Tarde"},
                {"Julio", "Diaz", "903333333", "CMP88888", Especialidad.DERMATOLOGIA, "jdiaz@ate.com", "Mañana"},
                {"Andrea", "Ramos", "904444444", "CMP11223", Especialidad.MEDICINA_GENERAL, "aramos@ate.com", "Tarde"},
                {"Roberto", "Leiva", "905555555", "CMP22334", Especialidad.TRAUMATOLOGIA, "rleiva@ate.com", "Mañana"},
                {"Veronica", "Gomez", "906666666", "CMP33445", Especialidad.MEDICINA_GENERAL, "vgomez@ate.com", "Tarde"},
                {"Alonso", "Ruiz", "907777777", "CMP44556", Especialidad.GINECOLOGIA, "aruiz@ate.com", "Mañana"},
                {"Pamela", "Silva", "908888888", "CMP55667", Especialidad.CARDIOLOGIA, "psilva@ate.com", "Tarde"},
                {"Jorge", "Molina", "909999999", "CMP66778", Especialidad.PEDIATRIA, "jmolina@ate.com", "Mañana"},
                {"Carmen", "Delgado", "910101010", "CMP77889", Especialidad.DERMATOLOGIA, "cdelgado@ate.com", "Tarde"},
                {"Mauricio", "Fuentes", "911212121", "CMP88990", Especialidad.MEDICINA_GENERAL, "mfuentes@ate.com", "Mañana"},
                {"Natalia", "Campos", "912323232", "CMP99001", Especialidad.TRAUMATOLOGIA, "ncampos@ate.com", "Tarde"},
                {"Hector", "Loayza", "913434343", "CMP10987", Especialidad.MEDICINA_GENERAL, "hloayza@ate.com", "Mañana"},
                {"Paola", "Carrasco", "914545454", "CMP11876", Especialidad.GINECOLOGIA, "pcarrasco@ate.com", "Tarde"},
                {"Martin", "Cabrera", "915656565", "CMP12765", Especialidad.CARDIOLOGIA, "mcabrera@ate.com", "Mañana"},
                {"Renata", "Palacios", "916767676", "CMP13654", Especialidad.PEDIATRIA, "rpalacios@ate.com", "Tarde"},
                {"Ivan", "Mendez", "917878787", "CMP14543", Especialidad.DERMATOLOGIA, "imendez@ate.com", "Mañana"},
                {"Fabiola", "Reyes", "918989898", "CMP15432", Especialidad.MEDICINA_GENERAL, "freyes@ate.com", "Tarde"},
                {"Cristian", "Zapata", "919090909", "CMP16321", Especialidad.TRAUMATOLOGIA, "czapata@ate.com", "Mañana"},
                {"Melissa", "Villar", "920202020", "CMP17210", Especialidad.GINECOLOGIA, "mvillar@ate.com", "Tarde"}
            };

            List<Medico> medicosRegistrados = new ArrayList<>();
            for (Object[] dato : medicos) {
                medicosRegistrados.add(registrarMedico(
                        (String) dato[0], (String) dato[1], (String) dato[2], (String) dato[3],
                        (Especialidad) dato[4], (String) dato[5], (String) dato[6]
                ));
            }

            Object[][] consultorios = {
                {"Consultorio San Gabriel",    1, Especialidad.CARDIOLOGIA},
                {"Consultorio Infantil Norte", 1, Especialidad.PEDIATRIA},
                {"Consultorio Medicina Uno",   1, Especialidad.MEDICINA_GENERAL},
                {"Consultorio Trauma Norte",   1, Especialidad.TRAUMATOLOGIA},
                {"Consultorio Cardio Sur",     2, Especialidad.CARDIOLOGIA},
                {"Consultorio Infantil Sol",   2, Especialidad.PEDIATRIA},
                {"Consultorio Trauma Este",    2, Especialidad.TRAUMATOLOGIA},
                {"Consultorio Medicina Dos",   2, Especialidad.MEDICINA_GENERAL},
                {"Consultorio Derma Centro",   3, Especialidad.DERMATOLOGIA},
                {"Consultorio Gine Centro",    3, Especialidad.GINECOLOGIA},
                {"Consultorio Cardio Luz",     3, Especialidad.CARDIOLOGIA},
                {"Consultorio Medicina Tres",  3, Especialidad.MEDICINA_GENERAL}
            };

            List<Consultorio> consultoriosRegistrados = new ArrayList<>();
            for (Object[] dato : consultorios) {
                consultoriosRegistrados.add(registrarConsultorio(
                        (String) dato[0], (Integer) dato[1], (Especialidad) dato[2]
                ));
            }

            String[] horas = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30"};
            String[] motivos = {
                "Control general", "Consulta preventiva", "Dolor recurrente", "Revision anual", "Chequeo medico",
                "Control de sintomas", "Evaluacion medica", "Consulta especializada", "Seguimiento clinico", "Revision de resultados",
                "Dolor articular", "Alergia leve", "Molestia respiratoria", "Control pediatrico", "Dolor de cabeza",
                "Consulta ginecologica", "Revision dermatologica", "Control cardiaco", "Dolor muscular", "Consulta neurologica"
            };
            double[] costos = {90, 95, 100, 105, 110, 115, 120, 125, 130, 135, 140, 145, 150, 155, 160, 165, 170, 175, 180, 185};
            String[] fechas = {"2026-04-20", "2026-04-21", "2026-04-22", "2026-04-23", "2026-04-24",
                "2026-04-25", "2026-04-26", "2026-04-27", "2026-04-28", "2026-04-29",
                "2026-04-30", "2026-05-01", "2026-05-02", "2026-05-03", "2026-05-04",
                "2026-05-05", "2026-05-06", "2026-05-07", "2026-05-08", "2026-05-09"};

            for (int i = 0; i < 12; i++) {
                programarCita(
                        pacientesRegistrados.get(i).getCodigo(),
                        medicosRegistrados.get(i).getCodigo(),
                        consultoriosRegistrados.get(i).getCodigo(),
                        fechas[i],
                        horas[i],
                        motivos[i],
                        costos[i]
                );
            }
        } else {
            for (Paciente p : pacienteServicio.obtenerPacientesComoArreglo()) {
                pacientesRegistrados.add(p);
            }
        }

        // Registrar 10 pacientes en el triaje inicial (Fechas posteriores al 24 de junio de 2026)
        if (triajeDAO.totalEnEspera() == 0 && pacientesRegistrados.size() > 0) {
            String[] priorities = {"NORMAL", "PRIORITARIO", "URGENTE"};
            int cantidadATriaje = Math.min(10, pacientesRegistrados.size());
            for (int i = 0; i < cantidadATriaje; i++) {
                String fechaTriaje;
                int dia = 25 + i;
                if (dia > 30) {
                    fechaTriaje = String.format("2026-07-%02d", dia - 30);
                } else {
                    fechaTriaje = String.format("2026-06-%02d", dia);
                }
                
                String prio = priorities[i % 3];
                registrarTriaje(
                    pacientesRegistrados.get(i).getCodigo(),
                    fechaTriaje,
                    62.5 + (i * 1.2),
                    1.62 + (i * 0.01),
                    36.4 + ((i % 5) * 0.2),
                    "120/80",
                    72 + (i % 8),
                    prio
                );
            }
        }
    }

    public Paciente registrarPaciente(String nombres, String apellidos, String telefono, String dni, int edad, String genero,
            String tipoSangre, String alergias, String correo, boolean tieneSis, String numeroSis) {
        return pacienteServicio.registrarPaciente(nombres, apellidos, telefono, dni, edad, genero, tipoSangre, alergias, correo, tieneSis, numeroSis);
    }

    public Medico registrarMedico(String nombres, String apellidos, String telefono, String cmp, Especialidad especialidad,
            String correo, String turno) {
        return medicoServicio.registrarMedico(nombres, apellidos, telefono, cmp, especialidad, correo, turno);
    }

    public Consultorio registrarConsultorio(String nombre, int piso, Especialidad especialidad) {
        return consultorioServicio.registrarConsultorio(nombre, piso, especialidad);
    }

    public Cita programarCita(String codigoPaciente, String codigoMedico, String codigoConsultorio, String fecha, String hora,
            String motivo, double costoConsulta) {
        Paciente paciente = pacienteServicio.buscarPorCodigo(codigoPaciente);
        Medico medico = medicoServicio.buscarPorCodigo(codigoMedico);
        Consultorio consultorio = consultorioServicio.buscarPorCodigo(codigoConsultorio);
        if (paciente == null || medico == null || consultorio == null) {
            return null;
        }
        if (!consultorioServicio.esCompatible(codigoConsultorio, medico.getEspecialidad())) {
            return null;
        }
        if (citaServicio.existeCruce(codigoMedico, codigoConsultorio, fecha, hora, null)) {
            throw new RuntimeException("Ya existe una cita programada para ese medico o consultorio en la misma fecha y hora.");
        }
        return citaServicio.programarCita(paciente, medico, consultorio, fecha, hora, motivo, costoConsulta);
    }

    public boolean actualizarPaciente(String codigo, String nombres, String apellidos, String telefono, String dni, int edad,
            String genero, String tipoSangre, String alergias, String correo, boolean tieneSis, String numeroSis) {
        return pacienteServicio.actualizarPaciente(codigo, nombres, apellidos, telefono, dni, edad, genero, tipoSangre, alergias, correo, tieneSis, numeroSis);
    }

    public boolean eliminarPaciente(String codigo) {
        boolean ok = pacienteServicio.eliminarPaciente(codigo);
        if (ok) {
            for (Cita c : citaServicio.listarOrdenadasPorFecha()) {
                if (c.getPaciente().getCodigo().equalsIgnoreCase(codigo)) {
                    citaServicio.eliminarCita(c.getCodigo());
                }
            }
            triajeDAO.eliminarPorPaciente(codigo);
            historialDAO.eliminarHistorial(codigo);
        }
        return ok;
    }

    public boolean actualizarMedico(String codigo, String nombres, String apellidos, String telefono, String cmp,
            Especialidad especialidad, String correo, String turno) {
        return medicoServicio.actualizarMedico(codigo, nombres, apellidos, telefono, cmp, especialidad, correo, turno);
    }

    public boolean eliminarMedico(String codigo) {
        boolean ok = medicoServicio.eliminarMedico(codigo);
        if (ok) {
            for (Cita c : citaServicio.listarOrdenadasPorFecha()) {
                if (c.getMedico().getCodigo().equalsIgnoreCase(codigo)) {
                    citaServicio.eliminarCita(c.getCodigo());
                }
            }
        }
        return ok;
    }

    public boolean actualizarConsultorio(String codigo, String nombre, int piso, Especialidad especialidad, boolean disponible) {
        return consultorioServicio.actualizarConsultorio(codigo, nombre, piso, especialidad, disponible);
    }

    public boolean eliminarConsultorio(String codigo) {
        boolean ok = consultorioServicio.eliminarConsultorio(codigo);
        if (ok) {
            for (Cita c : citaServicio.listarOrdenadasPorFecha()) {
                if (c.getConsultorio() != null && c.getConsultorio().getCodigo().equalsIgnoreCase(codigo)) {
                    citaServicio.eliminarCita(c.getCodigo());
                }
            }
        }
        return ok;
    }

    public boolean actualizarCita(String codigo, String codigoPaciente, String codigoMedico, String codigoConsultorio,
            String fecha, String hora, String motivo, double costoConsulta) {
        Cita cita = citaServicio.buscarPorCodigo(codigo);
        Paciente paciente = pacienteServicio.buscarPorCodigo(codigoPaciente);
        Medico medico = medicoServicio.buscarPorCodigo(codigoMedico);
        Consultorio consultorio = consultorioServicio.buscarPorCodigo(codigoConsultorio);
        if (cita == null || paciente == null || medico == null || consultorio == null) {
            return false;
        }
        if (!consultorioServicio.esCompatible(codigoConsultorio, medico.getEspecialidad())) {
            return false;
        }
        if (citaServicio.existeCruce(codigoMedico, codigoConsultorio, fecha, hora, codigo)) {
            throw new RuntimeException("Ya existe una cita programada para ese medico o consultorio en la misma fecha y hora.");
        }
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setConsultorio(consultorio);
        cita.setFecha(fecha);
        cita.setHora(hora);
        cita.setMotivo(motivo);
        cita.setCostoConsulta(costoConsulta);
        return citaServicio.actualizarCita(cita);
    }

    public boolean eliminarCita(String codigo) {
        return citaServicio.eliminarCita(codigo);
    }

    public boolean cambiarEstadoCita(String codigoCita, EstadoCita estado) {
        return citaServicio.cambiarEstado(codigoCita, estado);
    }

    public Paciente buscarPacientePorDniLineal(String dni) {
        return pacienteServicio.buscarPorDniLineal(dni);
    }

    public Paciente buscarPacientePorDniBinaria(String dni) {
        return pacienteServicio.buscarPorDniBinaria(dni);
    }

    public Paciente buscarPacientePorCodigo(String codigo) {
        return pacienteServicio.buscarPorCodigo(codigo);
    }

    public Medico buscarMedicoPorCodigo(String codigo) {
        return medicoServicio.buscarPorCodigo(codigo);
    }

    public Medico buscarMedicoPorCmpLineal(String cmp) {
        return medicoServicio.buscarPorCmpLineal(cmp);
    }

    public Medico buscarMedicoPorCmpBinaria(String cmp) {
        return medicoServicio.buscarPorCmpBinaria(cmp);
    }

    public Cita buscarCitaPorCodigo(String codigo) {
        return citaServicio.buscarPorCodigo(codigo);
    }

    public Consultorio buscarConsultorioPorCodigo(String codigo) {
        return consultorioServicio.buscarPorCodigo(codigo);
    }

    public Consultorio[] listarConsultoriosDisponiblesParaMedico(String codigoMedico) {
        Medico medico = medicoServicio.buscarPorCodigo(codigoMedico);
        if (medico == null) {
            return new Consultorio[0];
        }
        return consultorioServicio.listarDisponiblesPorEspecialidad(medico.getEspecialidad());
    }

    public Paciente[] listarPacientes() {
        return pacienteServicio.listarOrdenadosPorNombre();
    }

    public Paciente[] listarPacientesOrdenadosPorDni() {
        return pacienteServicio.listarOrdenadosPorDni();
    }

    public Paciente[] obtenerPacientesInorden() {
        return pacienteServicio.obtenerInorden();
    }

    public Paciente[] obtenerPacientesPreorden() {
        return pacienteServicio.obtenerPreorden();
    }

    public Paciente[] obtenerPacientesPostorden() {
        return pacienteServicio.obtenerPostorden();
    }

    public Medico[] listarMedicos() {
        return medicoServicio.listarOrdenadosPorNombre();
    }

    public Medico[] listarMedicosOrdenadosPorCmp() {
        return medicoServicio.listarOrdenadosPorCmp();
    }

    public Consultorio[] listarConsultorios() {
        Consultorio[] datos = consultorioServicio.listarConsultorios();
        OrdenamientosBusqueda.ordenarConsultoriosPorPiso(datos);
        return datos;
    }

    public Cita[] listarCitas() {
        return citaServicio.listarOrdenadasPorFecha();
    }

    public Especialidad[] listarEspecialidades() {
        return Especialidad.values();
    }

    public EstadoCita[] listarEstadosCita() {
        return EstadoCita.values();
    }

    public String getModoPersistencia() {
        return modoPersistencia;
    }

    // ==================== MEDICAMENTOS (ArrayList CRUD) ====================
    public List<Medicamento> listarMedicamentos(String filtro) {
        return medicamentoDAO.buscarPorTexto(filtro);
    }

    public Medicamento registrarMedicamento(String nombre, String categoria, String descripcion, double precio, int stock) {
        return medicamentoDAO.registrar(nombre, categoria, descripcion, precio, stock);
    }

    public boolean actualizarMedicamento(String codigo, String nombre, String categoria, String descripcion, double precio, int stock) {
        return medicamentoDAO.actualizar(codigo, nombre, categoria, descripcion, precio, stock);
    }

    public boolean eliminarMedicamento(String codigo) {
        return medicamentoDAO.eliminar(codigo);
    }

    public Medicamento buscarMedicamentoPorCodigo(String codigo) {
        return medicamentoDAO.buscarPorCodigo(codigo);
    }

    // ==================== TRIAJE (LinkedList FIFO) ====================
    public RegistroTriaje registrarTriaje(String codigoPaciente, String fecha,
            double peso, double talla, double temperatura,
            String presion, int frecuencia, String prioridad) {
        Paciente paciente = pacienteServicio.buscarPorCodigo(codigoPaciente);
        if (paciente == null) return null;
        return triajeDAO.registrar(paciente, fecha, peso, talla, temperatura, presion, frecuencia, prioridad);
    }

    public RegistroTriaje atenderSiguienteTriaje() {
        return triajeDAO.atenderSiguiente();
    }

    public RegistroTriaje verSiguienteTriaje() {
        return triajeDAO.verSiguiente();
    }

    public RegistroTriaje[] listarHistorialTriaje() {
        return triajeDAO.listarHistorial();
    }

    public RegistroTriaje[] listarEnEspera() {
        return triajeDAO.listarEnEspera();
    }

    public void setModoPrioridadTriaje(boolean usarPrioridad) {
        triajeDAO.setModoPrioridad(usarPrioridad);
    }

    public boolean isModoPrioridadTriaje() {
        return triajeDAO.isModoPrioridad();
    }

    // ==================== HISTORIAL CLINICO (Lista Doblemente Enlazada) ====================

    /**
     * Registra una nueva consulta clinica en el historial del paciente.
     * La consulta se agrega al FINAL de la ListaDoblementeEnlazada del paciente.
     *
     * @param codigoPaciente   Codigo del paciente
     * @param fecha            Fecha de la consulta
     * @param codigoMedico     Codigo del medico tratante
     * @param diagnostico      Texto del diagnostico
     * @param medicamentos     Medicamentos recetados
     * @param observaciones    Observaciones adicionales
     * @return La ConsultaClinica registrada, o null si el paciente no existe
     */
    public ConsultaClinica registrarConsultaHistorial(String codigoPaciente, String fecha,
            String codigoMedico, String diagnostico, String medicamentos, String observaciones) {
        Paciente paciente = pacienteServicio.buscarPorCodigo(codigoPaciente);
        Medico medico = medicoServicio.buscarPorCodigo(codigoMedico);
        if (paciente == null || medico == null) return null;
        return historialDAO.registrarConsulta(
                codigoPaciente, paciente.getNombreCompleto(), fecha,
                "Dr(a). " + medico.getNombreCompleto(),
                medico.getEspecialidad().toString(),
                diagnostico, medicamentos, observaciones);
    }

    /**
     * Obtiene la consulta MAS RECIENTE del paciente y posiciona el cursor al final.
     *
     * @param codigoPaciente Codigo del paciente
     * @return Ultima ConsultaClinica, o null si no tiene historial
     */
    public ConsultaClinica obtenerUltimaConsulta(String codigoPaciente) {
        return historialDAO.obtenerUltimaConsulta(codigoPaciente);
    }

    /**
     * Navega a la consulta ANTERIOR en el historial (lista doble hacia atras).
     *
     * @param codigoPaciente Codigo del paciente
     * @return Consulta anterior, o null si ya esta en la primera
     */
    public ConsultaClinica irAConsultaAnterior(String codigoPaciente) {
        return historialDAO.irAConsultaAnterior(codigoPaciente);
    }

    /**
     * Navega a la consulta SIGUIENTE en el historial (lista doble hacia adelante).
     *
     * @param codigoPaciente Codigo del paciente
     * @return Consulta siguiente, o null si ya esta en la ultima
     */
    public ConsultaClinica irAConsultaSiguiente(String codigoPaciente) {
        return historialDAO.irAConsultaSiguiente(codigoPaciente);
    }

    /** @return true si hay consulta anterior al cursor actual del paciente */
    public boolean hayConsultaAnterior(String codigoPaciente) {
        return historialDAO.hayConsultaAnterior(codigoPaciente);
    }

    /** @return true si hay consulta siguiente al cursor actual del paciente */
    public boolean hayConsultaSiguiente(String codigoPaciente) {
        return historialDAO.hayConsultaSiguiente(codigoPaciente);
    }

    /** @return cantidad total de consultas en el historial del paciente */
    public int totalConsultasHistorial(String codigoPaciente) {
        return historialDAO.totalConsultas(codigoPaciente);
    }

    /** @return true si el paciente tiene al menos una consulta registrada */
    public boolean pacienteTieneHistorial(String codigoPaciente) {
        return historialDAO.tieneHistorial(codigoPaciente);
    }
}
