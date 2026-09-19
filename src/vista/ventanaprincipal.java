package vista;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Cita;
import modelos.ConsultaClinica;
import modelos.Consultorio;
import modelos.Especialidad;
import modelos.EstadoCita;
import modelos.Medico;
import modelos.Medicamento;
import modelos.Paciente;
import modelos.RegistroTriaje;
import net.miginfocom.swing.MigLayout;
import servicios.ClinicaSistema;
import utilidades.ProjectPaths;

public class ventanaprincipal extends JFrame {
    private static final DecimalFormat FORMATO = new DecimalFormat("0.00");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private final ClinicaSistema sistema;
    private final temaaplicacion tema;
    private final CardLayout tarjetas = new CardLayout();
    private JPanel contenedorTarjetas;
    private panelencabezado encabezado;
    private final estructuras.PilaEnlazada<String> historialNavegacion = new estructuras.PilaEnlazada<>();
    private String moduloActual = "Dashboard";
    private JButton btnAtras;
    private botonlateral btnDashboard;
    private botonlateral btnPacientes;
    private botonlateral btnMedicos;
    private botonlateral btnConsultorios;
    private botonlateral btnCitas;
    private botonlateral btnTriaje;
    private botonlateral btnMedicamentos;
    private botonlateral btnHistorial;
    private JLabel estadoUsuario;
    private JLabel estadoFecha;
    private JLabel estadoPacientes;
    private JLabel estadoBase;
    private JLabel estadoCita;
    private JLabel lblTotalPacientes;
    private JLabel lblTotalMedicos;
    private JLabel lblTotalConsultorios;
    private JLabel lblTotalCitas;
    private JTable tablaPacientes;
    private JTable tablaMedicos;
    private JTable tablaConsultorios;
    private JTable tablaCitas;
    private DefaultTableModel modeloPacientes;
    private DefaultTableModel modeloMedicos;
    private DefaultTableModel modeloConsultorios;
    private DefaultTableModel modeloCitas;
    private JTextField txtPacienteNombres;
    private JTextField txtPacienteApellidos;
    private JTextField txtPacienteTelefono;
    private JTextField txtPacienteDni;
    private JTextField txtPacienteEdad;
    private JTextField txtPacienteGenero;
    private JTextField txtPacienteSangre;
    private JTextField txtPacienteAlergias;
    private JTextField txtPacienteCorreo;
    private JCheckBox chkPacienteSis;
    private JTextField txtPacienteNumeroSis;
    private JTextField txtBuscarDni;
    private JTextField txtMedicoNombres;
    private JTextField txtMedicoApellidos;
    private JTextField txtMedicoTelefono;
    private JTextField txtMedicoCmp;
    private JTextField txtMedicoCorreo;
    private JTextField txtMedicoTurno;
    private JComboBox<Especialidad> comboEspecialidad;
    private JTextField txtBuscarCmp;
    private JTextField txtConsultorioNombre;
    private JTextField txtConsultorioPiso;
    private JComboBox<Especialidad> comboConsultorioEspecialidad;
    private JCheckBox chkConsultorioDisponible;
    private JTextField txtCitaPaciente;
    private JTextField txtCitaMedico;
    private JTextField txtCitaConsultorio;
    private DatePicker txtCitaFecha;
    private TimePicker txtCitaHora;
    private JTextField txtCitaMotivo;
    private JTextField txtCitaCosto;
    private JTextField txtCodigoCitaEstado;
    private JComboBox<EstadoCita> comboEstadoCita;
    private String codigoPacienteSeleccionado;
    private String codigoMedicoSeleccionado;
    private String codigoConsultorioSeleccionado;
    private String codigoCitaSeleccionada;
    // Triaje (LinkedList)
    private JTextField txtTriajePaciente;
    private JTextField txtTriajeFecha;
    private JTextField txtTriajePeso;
    private JTextField txtTriajeTalla;
    private JTextField txtTriajeTemperatura;
    private JTextField txtTriajePresion;
    private JTextField txtTriajeFrecuencia;
    private JComboBox<String> comboPrioridad;
    private DefaultTableModel modeloTriajes;
    private DefaultTableModel modeloEspera;
    private javax.swing.DefaultListModel<String> modeloListaEspera;
    private javax.swing.JList<String> listaEspera;
    // Medicamentos (ArrayList CRUD)
    private JTextField txtMedNombre;
    private JTextField txtMedCategoria;
    private JTextField txtMedDescripcion;
    private JTextField txtMedPrecio;
    private JTextField txtMedStock;
    private JTextField txtBuscarMed;
    private DefaultTableModel modeloMedicamentos;
    private JTable tablaMedicamentos;
    private String codigoMedSeleccionado;
    private JLabel lblMedImagen;
    private JLabel lblMedTipoPresentacion;
    // Mapa consultorios (Matriz 2D)
    private JPanel panelMapa;
    // Historial Clinico (Lista Doblemente Enlazada)
    private JTextField txtHistorialCodPaciente;
    private JTextField txtHistorialCodMedico;
    private JTextField txtHistorialFecha;
    private JTextField txtHistorialDiagnostico;
    private JTextField txtHistorialMedicamentos;
    private JTextField txtHistorialObservaciones;
    private JLabel lblFichaFecha;
    private JLabel lblFichaMedico;
    private JLabel lblFichaEspecialidad;
    private JLabel lblFichaDiagnostico;
    private JLabel lblFichaMedicamentos;
    private JLabel lblFichaObservaciones;
    private JLabel lblFichaCodigo;
    private JButton btnHistAnterior;
    private JButton btnHistSiguiente;
    private String codigoPacienteHistorial;

    public ventanaprincipal(ClinicaSistema sistema) {
        this.sistema = sistema;
        this.tema = temaaplicacion.actual();
        configurarVentana();
        inicializarComponentes();
        refrescarTodo();
    }

    private void configurarVentana() {
        setTitle("CORE-SALUD - Sistema de Gestion Integral");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 840);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        panelfondo fondo = new panelfondo(ProjectPaths.resolveProject("recursos", "FONDO.jpg"), true, tema.chartBackground, tema.fondoVelo);
        fondo.setLayout(new BorderLayout(12, 12));
        JPanel base = new JPanel(new BorderLayout(12, 12));
        base.setOpaque(false);
        base.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        encabezado = new panelencabezado(tema);
        JPanel superior = new JPanel(new BorderLayout());
        superior.setOpaque(false);
        superior.setPreferredSize(new Dimension(100, 138));
        superior.add(encabezado, BorderLayout.CENTER);
        base.add(superior, BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout(18, 0));
        centro.setOpaque(false);
        centro.add(crearMenuLateral(), BorderLayout.WEST);
        contenedorTarjetas = new JPanel(tarjetas);
        contenedorTarjetas.setOpaque(false);
        contenedorTarjetas.add(crearPanelDashboard(), "Dashboard");
        contenedorTarjetas.add(crearPanelPacientes(), "Pacientes");
        contenedorTarjetas.add(crearPanelMedicos(), "Medicos");
        contenedorTarjetas.add(crearPanelConsultorios(), "Consultorios");
        contenedorTarjetas.add(crearPanelCitas(), "Citas");
        contenedorTarjetas.add(crearPanelTriaje(), "Triaje");
        contenedorTarjetas.add(crearPanelMedicamentos(), "Medicamentos");
        contenedorTarjetas.add(crearPanelHistorial(), "Historial");
        centro.add(contenedorTarjetas, BorderLayout.CENTER);
        base.add(centro, BorderLayout.CENTER);
        base.add(crearBarraEstado(), BorderLayout.SOUTH);
        fondo.add(base, BorderLayout.CENTER);
        setContentPane(fondo);
        activarModulo("Dashboard", btnDashboard, tema.colorPacientes, tema.colorCitas.brighter());
    }

    private JComponent crearMenuLateral() {
        panelredondeado menu = new panelredondeado(28);
        menu.setFillColor(new Color(235, 244, 251, 235));
        menu.setBorderColor(new Color(207, 221, 234));
        menu.setLayout(new BorderLayout(0, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        menu.setPreferredSize(new Dimension(248, 100));
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        JLabel logo = new JLabel("CM", SwingConstants.CENTER);
        logo.setForeground(tema.bannerTitulo);
        logo.setFont(fuentesinterfaz.title(24));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitulo = new JLabel("Clinica digital", SwingConstants.CENTER);
        subtitulo.setForeground(tema.menuTitulo);
        subtitulo.setFont(fuentesinterfaz.body(12));
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoPanel.add(Box.createVerticalStrut(8));
        logoPanel.add(logo);
        logoPanel.add(Box.createVerticalStrut(4));
        logoPanel.add(subtitulo);
        logoPanel.add(Box.createVerticalStrut(10));
        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        JLabel titulo = new JLabel("Modulos");
        titulo.setForeground(tema.bannerTitulo);
        titulo.setFont(fuentesinterfaz.heading(15));
        titulo.setBorder(BorderFactory.createEmptyBorder(8, 4, 8, 4));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        botones.add(titulo);

        btnAtras = new JButton("Volver");
        btnAtras.setFont(fuentesinterfaz.body(13));
        btnAtras.setForeground(new Color(33, 150, 243));
        btnAtras.setBackground(new Color(255, 255, 255, 180));
        btnAtras.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 1, true),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        btnAtras.setFocusPainted(false);
        btnAtras.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAtras.setEnabled(false);
        btnAtras.addActionListener(e -> navegarAtras());
        botones.add(btnAtras);
        botones.add(Box.createVerticalStrut(10));

        btnDashboard = crearBotonModulo("DB", "Dashboard", tema.colorPacientes, "Dashboard");
        btnPacientes = crearBotonModulo("PA", "Pacientes", tema.colorPacientes, "Pacientes");
        btnMedicos = crearBotonModulo("ME", "Medicos", tema.colorMedicos, "Medicos");
        btnConsultorios = crearBotonModulo("CO", "Consultorios", tema.colorConsultorios, "Consultorios");
        btnCitas = crearBotonModulo("CI", "Citas", tema.colorCitas, "Citas");
        btnTriaje = crearBotonModulo("TR", "Triaje", new Color(41, 182, 246), "Triaje");
        btnMedicamentos = crearBotonModulo("FM", "Medicamentos", new Color(102, 187, 106), "Medicamentos");
        btnHistorial = crearBotonModulo("HC", "Historial Clinico", new Color(156, 39, 176), "Historial");
        botones.add(btnDashboard); botones.add(btnPacientes); botones.add(btnMedicos); botones.add(btnConsultorios); botones.add(btnCitas); botones.add(btnTriaje); botones.add(btnMedicamentos); botones.add(btnHistorial);
        menu.add(logoPanel, BorderLayout.NORTH);
        menu.add(botones, BorderLayout.CENTER);
        return menu;
    }

    private botonlateral crearBotonModulo(String icono, String texto, Color color, String tarjeta) {
        botonlateral boton = new botonlateral(icono, texto, color, tema);
        boton.setMargin(new Insets(10, 14, 10, 14));
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.addActionListener(e -> activarModulo(tarjeta, boton, color, color.brighter()));
        return boton;
    }

    private JComponent crearBarraEstado() {
        panelvidrio barra = new panelvidrio(tema.barraEstado);
        barra.setLayout(new FlowLayout(FlowLayout.LEFT, 14, 8));
        barra.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        estadoUsuario = crearEtiquetaEstado("USR  Administrador");
        estadoFecha = crearEtiquetaEstado("FEC  " + LocalDate.now());
        estadoPacientes = crearEtiquetaEstado("PAC  0");
        estadoBase = crearEtiquetaEstado("DB  " + sistema.getModoPersistencia());
        estadoCita = crearEtiquetaEstado("CIT  Sin datos");
        barra.add(estadoUsuario); barra.add(crearSeparadorEstado()); barra.add(estadoFecha); barra.add(crearSeparadorEstado()); barra.add(estadoPacientes); barra.add(crearSeparadorEstado()); barra.add(estadoBase); barra.add(Box.createHorizontalStrut(24)); barra.add(estadoCita);
        return barra;
    }

    private JLabel crearEtiquetaEstado(String texto) { JLabel label = new JLabel(texto); label.setFont(fuentesinterfaz.body(13)); label.setForeground(tema.estadoTexto); return label; }
    private JLabel crearSeparadorEstado() { JLabel label = new JLabel("•"); label.setFont(fuentesinterfaz.heading(14)); label.setForeground(tema.estadoSeparador); return label; }

    private void activarModulo(String tarjeta, botonlateral activo, Color principal, Color secundario) {
        if (!tarjeta.equals(moduloActual)) {
            historialNavegacion.push(moduloActual);
            moduloActual = tarjeta;
            actualizarEstadoBotonAtras();
        }
        activarModuloInterno(tarjeta, activo, principal, secundario);
    }

    private void activarModuloInterno(String tarjeta, botonlateral activo, Color principal, Color secundario) {
        tarjetas.show(contenedorTarjetas, tarjeta);
        btnDashboard.setActive(false); btnPacientes.setActive(false); btnMedicos.setActive(false);
        btnConsultorios.setActive(false); btnCitas.setActive(false);
        btnTriaje.setActive(false); btnMedicamentos.setActive(false); btnHistorial.setActive(false);
        activo.setActive(true);
        encabezado.actualizarModulo("Modulo de " + tarjeta, principal, secundario);
        if ("Triaje".equals(tarjeta)) refrescarTriaje();
        if ("Medicamentos".equals(tarjeta)) refrescarMedicamentos("");
        if ("Consultorios".equals(tarjeta)) refrescarConsultoriosMapa();
    }

    private void navegarAtras() {
        if (!historialNavegacion.estaVacia()) {
            String anterior = historialNavegacion.pop();
            moduloActual = anterior;
            actualizarEstadoBotonAtras();
            navegarAModulo(anterior);
        }
    }

    private void actualizarEstadoBotonAtras() {
        if (btnAtras != null) {
            btnAtras.setEnabled(!historialNavegacion.estaVacia());
        }
    }

    private void navegarAModulo(String tarjeta) {
        botonlateral boton = null;
        Color color = null;
        switch (tarjeta) {
            case "Dashboard":
                boton = btnDashboard;
                color = tema.colorPacientes;
                break;
            case "Pacientes":
                boton = btnPacientes;
                color = tema.colorPacientes;
                break;
            case "Medicos":
                boton = btnMedicos;
                color = tema.colorMedicos;
                break;
            case "Consultorios":
                boton = btnConsultorios;
                color = tema.colorConsultorios;
                break;
            case "Citas":
                boton = btnCitas;
                color = tema.colorCitas;
                break;
            case "Triaje":
                boton = btnTriaje;
                color = new Color(41, 182, 246);
                break;
            case "Medicamentos":
                boton = btnMedicamentos;
                color = new Color(102, 187, 106);
                break;
            case "Historial":
                boton = btnHistorial;
                color = new Color(156, 39, 176);
                break;
        }
        if (boton != null && color != null) {
            activarModuloInterno(tarjeta, boton, color, color.brighter());
        }
    }

    private JComponent crearPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JPanel resumen = new JPanel(new GridLayout(2, 2, 16, 16));
        resumen.setOpaque(false);
        lblTotalPacientes = crearEtiquetaValor("0");
        lblTotalMedicos = crearEtiquetaValor("0");
        lblTotalConsultorios = crearEtiquetaValor("0");
        lblTotalCitas = crearEtiquetaValor("0");
        resumen.add(new tarjetaresumen("PA", "Pacientes registrados", lblTotalPacientes, tema.colorPacientes, tema, "pacientes.png"));
        resumen.add(new tarjetaresumen("ME", "Medicos activos", lblTotalMedicos, tema.colorMedicos, tema, "medicos.png"));
        resumen.add(new tarjetaresumen("CO", "Consultorios operativos", lblTotalConsultorios, tema.colorConsultorios, tema, "consultorios.png"));
        resumen.add(new tarjetaresumen("CI", "Citas programadas", lblTotalCitas, tema.colorCitas, tema, "citas.png"));
        panel.add(resumen, BorderLayout.CENTER);
        return panel;
    }

    private JLabel crearEtiquetaValor(String texto) { JLabel label = new JLabel(texto, SwingConstants.CENTER); label.setFont(fuentesinterfaz.title(30)); return label; }

    private JComponent crearPanelPacientes() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setOpaque(false);
        panel.add(crearFormularioPacientes(), BorderLayout.WEST);
        panel.add(crearListadoPacientes(), BorderLayout.CENTER);
        return panel;
    }

    private JComponent crearFormularioPacientes() {
        panelredondeado panel = crearCajaBlanca(24, "Registro avanzado de pacientes");
        JPanel form = new JPanel(new MigLayout("fillx, insets 6 10 10 10, gapy 4", "[grow,fill]", ""));
        form.setOpaque(false);

        txtPacienteNombres = crearCampoTextoSeguro();
        txtPacienteApellidos = crearCampoTextoSeguro();
        txtPacienteTelefono = crearCampoTextoSeguro();
        txtPacienteDni = crearCampoTextoSeguro();
        txtPacienteEdad = crearCampoTextoSeguro();
        txtPacienteGenero = crearCampoTextoSeguro();
        txtPacienteSangre = crearCampoTextoSeguro();
        txtPacienteAlergias = crearCampoTextoSeguro();
        txtPacienteCorreo = crearCampoTextoSeguro();
        txtPacienteNumeroSis = crearCampoTextoSeguro();
        txtPacienteNumeroSis.setEnabled(false);

        chkPacienteSis = new JCheckBox("Paciente afiliado al SIS");
        chkPacienteSis.setOpaque(false);
        chkPacienteSis.setFont(fuentesinterfaz.heading(13));
        chkPacienteSis.setForeground(tema.tablaTexto);
        chkPacienteSis.addActionListener(e -> {
            txtPacienteNumeroSis.setEnabled(chkPacienteSis.isSelected());
            if (!chkPacienteSis.isSelected()) {
                txtPacienteNumeroSis.setText("");
            }
        });

        agregarCampoSeguro(form, "Nombres", txtPacienteNombres);
        agregarCampoSeguro(form, "Apellidos", txtPacienteApellidos);
        agregarCampoSeguro(form, "Telefono", txtPacienteTelefono);
        agregarCampoSeguro(form, "DNI", txtPacienteDni);
        agregarCampoSeguro(form, "Edad", txtPacienteEdad);
        agregarCampoSeguro(form, "Genero", txtPacienteGenero);
        agregarCampoSeguro(form, "Tipo de sangre", txtPacienteSangre);
        agregarCampoSeguro(form, "Alergias", txtPacienteAlergias);
        agregarCampoSeguro(form, "Correo", txtPacienteCorreo);
        form.add(chkPacienteSis, "growx, wrap");
        agregarCampoSeguro(form, "Numero SIS", txtPacienteNumeroSis);

        JButton btnGuardar = crearBotonPrimario("Guardar paciente", e -> registrarPaciente());
        JButton btnActualizar = crearBotonSecundario("Actualizar paciente", e -> actualizarPacienteSeleccionado());
        JButton btnEliminar = crearBotonSecundario("Eliminar paciente", e -> eliminarPacienteSeleccionado());
        JButton btnLimpiar = crearBotonSecundario("Limpiar", e -> limpiarCamposPaciente());
        form.add(btnGuardar, "growx, h 46!, wrap");
        form.add(btnActualizar, "growx, h 46!, wrap");
        form.add(btnEliminar, "growx, h 46!, wrap");
        form.add(btnLimpiar, "growx, h 46!, wrap");

        panel.add(form, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(500, 920));

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setPreferredSize(new Dimension(500, 100));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(24);
        scroll.getVerticalScrollBar().setBlockIncrement(120);
        scroll.setOpaque(true);
        scroll.setBackground(panel.getBackground());
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(panel.getBackground());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(16, 0));
        return scroll;
    }

    private JComponent crearListadoPacientes() {
        JPanel base = new JPanel(new BorderLayout(0, 16)); base.setOpaque(false);
        panelredondeado algoritmos = crearCajaBlanca(24, "Busquedas y algoritmos");
        JPanel top = new JPanel(new BorderLayout(8, 10));
        top.setOpaque(false);
        txtBuscarDni = new JTextField();
        top.add(crearCampoHorizontal("DNI", txtBuscarDni), BorderLayout.NORTH);
        JPanel gridBotones = new JPanel(new java.awt.GridLayout(2, 2, 10, 8));
        gridBotones.setOpaque(false);
        gridBotones.add(crearBotonPrimario("Busqueda lineal", e -> buscarPacientePorDniLineal()));
        gridBotones.add(crearBotonPrimario("Busqueda binaria", e -> buscarPacientePorDniBinaria()));
        gridBotones.add(crearBotonSecundario("Ordenar por DNI", e -> ordenarPacientesPorDni()));
        gridBotones.add(crearBotonSecundario("Recorridos Árbol", e -> mostrarRecorridosArbol()));
        top.add(gridBotones, BorderLayout.CENTER);
        algoritmos.add(top, BorderLayout.CENTER);
        modeloPacientes = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "DNI", "Edad", "Genero", "Sangre", "SIS", "Nro SIS"}, 0) { @Override public boolean isCellEditable(int row, int column) { return false; } };
        tablaPacientes = crearTabla(modeloPacientes);
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarPacienteDesdeTabla(); });
        base.add(algoritmos, BorderLayout.NORTH); base.add(crearScrollTabla(tablaPacientes), BorderLayout.CENTER);
        return base;
    }

    private JComponent crearPanelMedicos() {
        JPanel panel = new JPanel(new BorderLayout(16, 16)); panel.setOpaque(false); panel.add(crearFormularioMedicos(), BorderLayout.WEST); panel.add(crearListadoMedicos(), BorderLayout.CENTER); return panel;
    }

    private JComponent crearFormularioMedicos() {
        panelredondeado panel = crearCajaBlanca(24, "Registro de medicos");
        JPanel form = new JPanel(new GridLayout(0, 1, 0, 8)); form.setOpaque(false);
        txtMedicoNombres = new JTextField(); txtMedicoApellidos = new JTextField(); txtMedicoTelefono = new JTextField(); txtMedicoCmp = new JTextField(); txtMedicoCorreo = new JTextField(); txtMedicoTurno = new JTextField();
        // Opciones de especialidad medica
        Especialidad[] arregloEspecialidades = {
            Especialidad.CARDIOLOGIA, Especialidad.PEDIATRIA,
            Especialidad.MEDICINA_GENERAL, Especialidad.ODONTOLOGIA
        };
        comboEspecialidad = new JComboBox<>(arregloEspecialidades);
        agregarCampo(form, "Nombres", txtMedicoNombres); agregarCampo(form, "Apellidos", txtMedicoApellidos); agregarCampo(form, "Telefono", txtMedicoTelefono); agregarCampo(form, "CMP", txtMedicoCmp); agregarCampo(form, "Correo", txtMedicoCorreo); agregarCampo(form, "Turno", txtMedicoTurno); agregarCampo(form, "Especialidad", comboEspecialidad);
        JPanel acciones = crearFilaBotones(crearBotonPrimario("Guardar medico", e -> registrarMedico()), crearBotonSecundario("Actualizar medico", e -> actualizarMedicoSeleccionado()), crearBotonSecundario("Eliminar medico", e -> eliminarMedicoSeleccionado()), crearBotonSecundario("Limpiar", e -> limpiarCamposMedico()));
        JPanel cont = new JPanel(new BorderLayout(0, 14)); cont.setOpaque(false); cont.add(form, BorderLayout.CENTER); cont.add(acciones, BorderLayout.SOUTH); panel.add(cont, BorderLayout.CENTER); panel.setPreferredSize(new Dimension(450, 100));
        return panel;
    }

    private JComponent crearListadoMedicos() {
        JPanel base = new JPanel(new BorderLayout(0, 16)); base.setOpaque(false);
        panelredondeado algoritmos = crearCajaBlanca(24, "Busquedas y algoritmos");
        JPanel top = new JPanel(new BorderLayout(8, 10)); top.setOpaque(false); txtBuscarCmp = new JTextField(); top.add(crearCampoHorizontal("CMP", txtBuscarCmp), BorderLayout.NORTH); top.add(crearFilaBotones(crearBotonPrimario("Busqueda lineal", e -> buscarMedicoPorCmpLineal()), crearBotonPrimario("Busqueda binaria", e -> buscarMedicoPorCmpBinaria()), crearBotonSecundario("Ordenar por CMP", e -> ordenarMedicosPorCmp())), BorderLayout.CENTER); algoritmos.add(top, BorderLayout.CENTER);
        modeloMedicos = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "CMP", "Especialidad", "Turno", "Correo"}, 0) { @Override public boolean isCellEditable(int row, int column) { return false; } };
        tablaMedicos = crearTabla(modeloMedicos);
        tablaMedicos.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarMedicoDesdeTabla(); });
        base.add(algoritmos, BorderLayout.NORTH); base.add(crearScrollTabla(tablaMedicos), BorderLayout.CENTER);
        return base;
    }

    private JComponent crearPanelConsultorios() {
        JPanel panel = new JPanel(new BorderLayout(16, 16)); panel.setOpaque(false); panel.add(crearFormularioConsultorios(), BorderLayout.WEST); panel.add(crearListadoConsultorios(), BorderLayout.CENTER); return panel;
    }

    private JComponent crearFormularioConsultorios() {
        panelredondeado panel = crearCajaBlanca(24, "Gestion de consultorios");
        JPanel form = new JPanel(new GridLayout(0, 1, 0, 8)); form.setOpaque(false);
        txtConsultorioNombre = new JTextField(); txtConsultorioPiso = new JTextField(); comboConsultorioEspecialidad = new JComboBox<>(sistema.listarEspecialidades());
        chkConsultorioDisponible = new JCheckBox("Disponible / Habilitado");
        chkConsultorioDisponible.setOpaque(false);
        chkConsultorioDisponible.setFont(fuentesinterfaz.body(14));
        chkConsultorioDisponible.setSelected(true);
        agregarCampo(form, "Nombre", txtConsultorioNombre); agregarCampo(form, "Piso", txtConsultorioPiso); agregarCampo(form, "Especialidad", comboConsultorioEspecialidad);
        form.add(chkConsultorioDisponible);
        JPanel acciones = crearFilaBotones(crearBotonPrimario("Registrar consultorio", e -> registrarConsultorio()), crearBotonSecundario("Actualizar consultorio", e -> actualizarConsultorioSeleccionado()), crearBotonSecundario("Eliminar consultorio", e -> eliminarConsultorioSeleccionado()), crearBotonSecundario("Limpiar", e -> limpiarCamposConsultorio()));
        JPanel cont = new JPanel(new BorderLayout(0, 12)); cont.setOpaque(false); cont.add(form, BorderLayout.CENTER); cont.add(acciones, BorderLayout.SOUTH); panel.add(cont, BorderLayout.CENTER); panel.setPreferredSize(new Dimension(430, 100));
        return panel;
    }

    private JComponent crearListadoConsultorios() {
        JPanel base = new JPanel(new BorderLayout(0, 16));
        base.setOpaque(false);
        modeloConsultorios = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Piso", "Especialidad", "Disponible"}, 0) { @Override public boolean isCellEditable(int row, int column) { return false; } };
        tablaConsultorios = crearTabla(modeloConsultorios);
        tablaConsultorios.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarConsultorioDesdeTabla(); });
        // Panel del mapa de consultorios
        panelredondeado mapaContainer = crearCajaBlanca(20, "Mapa de Consultorios  [VERDE] Libre  [ROJO] Ocupado");
        panelMapa = new JPanel(new GridLayout(3, 5, 6, 6));
        panelMapa.setOpaque(false);
        panelMapa.setPreferredSize(new Dimension(100, 120));
        mapaContainer.add(panelMapa, BorderLayout.CENTER);
        base.add(crearScrollTabla(tablaConsultorios), BorderLayout.CENTER);
        base.add(mapaContainer, BorderLayout.SOUTH);
        return base;
    }

    private JComponent crearPanelCitas() {
        JPanel panel = new JPanel(new BorderLayout(16, 16)); panel.setOpaque(false); panel.add(crearFormularioCitas(), BorderLayout.WEST); panel.add(crearListadoCitas(), BorderLayout.CENTER); return panel;
    }

    private JComponent crearFormularioCitas() {
        panelredondeado panel = crearCajaBlanca(24, "Programacion de citas");
        JPanel form = new JPanel(new GridLayout(0, 1, 0, 8));
        form.setOpaque(false);

        txtCitaPaciente = new JTextField();
        txtCitaMedico = new JTextField();
        txtCitaConsultorio = new JTextField();
        txtCitaFecha = new DatePicker();
        txtCitaHora = new TimePicker();
        txtCitaFecha.setDate(LocalDate.now());
        txtCitaHora.setTime(LocalTime.of(9, 0));
        txtCitaMotivo = new JTextField();
        txtCitaCosto = new JTextField("150.00");
        txtCodigoCitaEstado = new JTextField();
        comboEstadoCita = new JComboBox<>(sistema.listarEstadosCita());

        agregarCampo(form, "Codigo paciente", txtCitaPaciente);
        agregarCampo(form, "Codigo medico", txtCitaMedico);
        agregarCampo(form, "Codigo consultorio", txtCitaConsultorio);
        agregarCampo(form, "Fecha", txtCitaFecha);
        agregarCampo(form, "Hora", txtCitaHora);
        agregarCampo(form, "Motivo", txtCitaMotivo);
        agregarCampo(form, "Costo", txtCitaCosto);
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel ayuda = crearFilaBotones(crearBotonSecundario("Sugerir consultorio", e -> autocompletarConsultorio()));
        ayuda.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelredondeado estadoPanel = crearCajaBlanca(20, "Estado de cita");
        JPanel estadoCont = new JPanel(new GridLayout(0, 1, 0, 8));
        estadoCont.setOpaque(false);
        agregarCampo(estadoCont, "Codigo cita", txtCodigoCitaEstado);
        agregarCampo(estadoCont, "Estado", comboEstadoCita);
        estadoCont.add(crearFilaBotones(crearBotonPrimario("Actualizar estado", e -> cambiarEstadoCita())));
        estadoPanel.add(estadoCont, BorderLayout.CENTER);
        estadoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        estadoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, estadoPanel.getPreferredSize().height));

        JPanel acciones = new JPanel(new GridLayout(2, 2, 12, 12));
        acciones.setOpaque(false);
        acciones.add(crearBotonPrimario("Registrar cita", e -> registrarCita()));
        acciones.add(crearBotonSecundario("Actualizar cita", e -> actualizarCitaSeleccionada()));
        acciones.add(crearBotonSecundario("Eliminar cita", e -> eliminarCitaSeleccionada()));
        acciones.add(crearBotonSecundario("Limpiar", e -> limpiarCamposCita()));
        acciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        acciones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.add(form);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(ayuda);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(estadoPanel);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(acciones);

        panel.add(contenido, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(430, 1400));

        scrollredondeado scroll = new scrollredondeado(panel, 24, new Color(255, 255, 255, 0), null);
        scroll.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(28);
        scroll.getVerticalScrollBar().setBlockIncrement(120);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(24);
        scroll.getVerticalScrollBar().setBlockIncrement(120);
        scroll.setPreferredSize(new Dimension(430, 100));
        return scroll;
    }

    private JComponent crearListadoCitas() {
        modeloCitas = new DefaultTableModel(new Object[]{"Codigo", "Paciente", "Tipo", "Medico", "Consultorio", "Fecha", "Hora", "Costo", "Estado"}, 0) { @Override public boolean isCellEditable(int row, int column) { return false; } };
        tablaCitas = crearTabla(modeloCitas);
        tablaCitas.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarCitaDesdeTabla(); });
        return crearScrollTabla(tablaCitas);
    }

    private panelredondeado crearCajaBlanca(int arc, String titulo) {
        panelredondeado panel = new panelredondeado(arc);
        panel.setFillColor(new Color(255, 255, 255, 235));
        panel.setBorderColor(new Color(207, 221, 234));
        panel.setLayout(new BorderLayout(0, 10));
        TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), titulo);
        borde.setTitleColor(tema.bannerTitulo);
        borde.setTitleFont(fuentesinterfaz.heading(14));
        panel.setBorder(BorderFactory.createCompoundBorder(borde, BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        return panel;
    }

    private JTextField crearCampoTextoSeguro() {
        JTextField campo = new JTextField(18);
        campo.setUI(new javax.swing.plaf.basic.BasicTextFieldUI());
        campo.setOpaque(true);
        campo.setEditable(true);
        campo.setEnabled(true);
        campo.setFocusable(true);
        campo.setBackground(Color.WHITE);
        campo.setForeground(new Color(31, 58, 92));
        campo.setDisabledTextColor(new Color(31, 58, 92));
        campo.setCaretColor(new Color(15, 91, 150));
        campo.setSelectionColor(new Color(205, 228, 248));
        campo.setSelectedTextColor(new Color(31, 58, 92));
        campo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(196, 214, 232), 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        campo.setPreferredSize(new Dimension(100, 30));
        return campo;
    }

    private void agregarCampoSeguro(JPanel panel, String etiqueta, JTextField campo) {
        JLabel label = new JLabel(etiqueta);
        label.setFont(fuentesinterfaz.heading(13));
        label.setForeground(tema.tablaTexto);
        panel.add(label, "growx, wrap");
        panel.add(campo, "growx, h 30!, wrap");
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent comp) {
        JLabel label = new JLabel(etiqueta); label.setFont(fuentesinterfaz.heading(13)); label.setForeground(tema.tablaTexto); panel.add(label); estilizarCampo(comp); panel.add(comp);
    }

    private JComponent crearCampoHorizontal(String etiqueta, JComponent comp) {
        JPanel fila = new JPanel(new BorderLayout(8, 0)); fila.setOpaque(false); JLabel label = new JLabel(etiqueta + ":"); label.setFont(fuentesinterfaz.heading(13)); label.setForeground(tema.tablaTexto); label.setPreferredSize(new Dimension(90, 36)); estilizarCampo(comp); fila.add(label, BorderLayout.WEST); fila.add(comp, BorderLayout.CENTER); return fila;
    }

    private void estilizarCampo(JComponent comp) {
        java.awt.Font inputFont = new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16);
        comp.setFont(inputFont);
        if (comp instanceof JComboBox<?> combo) {
            combo.setBackground(Color.WHITE);
            combo.setForeground(new Color(31, 58, 92));
            combo.setOpaque(true);
            combo.setFocusable(true);
            combo.setBorder(BorderFactory.createLineBorder(new Color(196, 214, 232), 1, true));
            combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
                    label.setFont(inputFont);
                    label.setForeground(new Color(31, 58, 92));
                    return label;
                }
            });
            comp.setPreferredSize(new Dimension(100, 58));
            return;
        }
        comp.setOpaque(true);
        comp.setBackground(Color.WHITE);
        comp.setForeground(new Color(31, 58, 92));
        comp.setFocusable(true);
        if (comp instanceof JTextField textField) {
            textField.setEditable(true);
            textField.setEnabled(true);
            textField.setDisabledTextColor(new Color(31, 58, 92));
            textField.setCaretColor(new Color(15, 91, 150));
            textField.setSelectionColor(new Color(205, 228, 248));
            textField.setSelectedTextColor(new Color(31, 58, 92));
            textField.setHorizontalAlignment(JTextField.LEFT);
        }
        comp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(196, 214, 232), 1, true), BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        comp.setPreferredSize(new Dimension(100, 34));
    }

    private JPanel crearFilaBotones(JButton... botones) { JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); fila.setOpaque(false); Arrays.stream(botones).forEach(fila::add); return fila; }

    private JButton crearBotonPrimario(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto); boton.setFont(fuentesinterfaz.heading(14)); boton.setForeground(Color.WHITE); boton.setBackground(tema.bannerTitulo); boton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18)); boton.addActionListener(accion); return boton;
    }

    private JButton crearBotonSecundario(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto); boton.setFont(fuentesinterfaz.heading(14)); boton.setForeground(tema.bannerTitulo); boton.setBackground(new Color(236, 244, 252)); boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(196, 214, 232), 1, true), BorderFactory.createEmptyBorder(10, 18, 10, 18))); boton.addActionListener(accion); return boton;
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo); tabla.setAutoCreateRowSorter(true); tabla.setFont(fuentesinterfaz.body(14)); tabla.setRowHeight(34); tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); tabla.setGridColor(new Color(220, 230, 239)); tabla.getTableHeader().setFont(fuentesinterfaz.heading(14)); tabla.getTableHeader().setBackground(tema.tablaHeaderFondo); tabla.getTableHeader().setForeground(tema.tablaHeaderTexto); tabla.setSelectionBackground(tema.tablaSeleccion); tabla.setSelectionForeground(tema.tablaTexto); DefaultTableCellRenderer centrado = new DefaultTableCellRenderer(); centrado.setHorizontalAlignment(SwingConstants.CENTER); for (int i = 0; i < tabla.getColumnCount(); i++) { tabla.getColumnModel().getColumn(i).setCellRenderer(centrado); } return tabla;
    }

    private JComponent crearScrollTabla(JTable tabla) { return new scrollredondeado(tabla, 24, tema.tablaFilaPar, tema.barraBorde); }

    private void refrescarTodo() { refrescarDashboard(); refrescarPacientes(); refrescarMedicos(); refrescarConsultoriosMapa(); refrescarCitas(); refrescarTriaje(); }
    private void refrescarDashboard() { Paciente[] pacientes = sistema.listarPacientes(); Medico[] medicos = sistema.listarMedicos(); Consultorio[] consultorios = sistema.listarConsultorios(); Cita[] citas = sistema.listarCitas(); lblTotalPacientes.setText(String.valueOf(pacientes.length)); lblTotalMedicos.setText(String.valueOf(medicos.length)); lblTotalConsultorios.setText(String.valueOf(consultorios.length)); lblTotalCitas.setText(String.valueOf(citas.length)); estadoFecha.setText("FEC  " + LocalDate.now()); estadoPacientes.setText("PAC  " + pacientes.length); estadoCita.setText(citas.length == 0 ? "CIT  Sin datos" : "CIT  " + citas[0].getCodigo()); }
    private void refrescarPacientes() { modeloPacientes.setRowCount(0); for (Paciente p : sistema.listarPacientes()) { modeloPacientes.addRow(new Object[]{p.getCodigo(), p.getNombreCompleto(), p.getDni(), p.getEdad(), p.getGenero(), p.getTipoSangre(), p.isTieneSis() ? "SI" : "NO", p.isTieneSis() ? p.getNumeroSis() : "-"}); } }
    private void refrescarMedicos() { modeloMedicos.setRowCount(0); for (Medico m : sistema.listarMedicos()) { modeloMedicos.addRow(new Object[]{m.getCodigo(), m.getNombreCompleto(), m.getCmp(), m.getEspecialidad(), m.getTurno(), m.getCorreo()}); } }
    private void refrescarConsultorios() { modeloConsultorios.setRowCount(0); for (Consultorio c : sistema.listarConsultorios()) { modeloConsultorios.addRow(new Object[]{c.getCodigo(), c.getNombre(), c.getPiso(), c.getEspecialidad(), c.isDisponible() ? "Si" : "No"}); } }
    private void refrescarCitas() { modeloCitas.setRowCount(0); for (Cita c : sistema.listarCitas()) { modeloCitas.addRow(new Object[]{c.getCodigo(), c.getPaciente().getNombreCompleto(), c.getPaciente().isTieneSis() ? "SIS" : "PARTICULAR", c.getMedico().getNombreCompleto(), c.getConsultorio() == null ? "-" : c.getConsultorio().getNombre(), c.getFecha(), c.getHora(), FORMATO.format(c.getCostoConsulta()), c.getEstado()}); } }

    private void registrarPaciente() {
        try {
            Paciente p = sistema.registrarPaciente(
                txtPacienteNombres.getText().trim(),
                txtPacienteApellidos.getText().trim(),
                txtPacienteTelefono.getText().trim(),
                txtPacienteDni.getText().trim(),
                intentarParsearEntero(txtPacienteEdad.getText().trim(), "Edad"),
                txtPacienteGenero.getText().trim(),
                txtPacienteSangre.getText().trim(),
                txtPacienteAlergias.getText().trim(),
                txtPacienteCorreo.getText().trim(),
                chkPacienteSis.isSelected(),
                txtPacienteNumeroSis.getText().trim()
            );
            limpiarCamposPaciente();
            refrescarTodo();
            mostrarMensaje("Paciente registrado con codigo " + p.getCodigo());
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void actualizarPacienteSeleccionado() {
        if (codigoPacienteSeleccionado == null) {
            mostrarError("Selecciona un paciente.");
            return;
        }
        try {
            boolean ok = sistema.actualizarPaciente(
                codigoPacienteSeleccionado,
                txtPacienteNombres.getText().trim(),
                txtPacienteApellidos.getText().trim(),
                txtPacienteTelefono.getText().trim(),
                txtPacienteDni.getText().trim(),
                intentarParsearEntero(txtPacienteEdad.getText().trim(), "Edad"),
                txtPacienteGenero.getText().trim(),
                txtPacienteSangre.getText().trim(),
                txtPacienteAlergias.getText().trim(),
                txtPacienteCorreo.getText().trim(),
                chkPacienteSis.isSelected(),
                txtPacienteNumeroSis.getText().trim()
            );
            if (!ok) {
                mostrarError("No se pudo actualizar el paciente.");
                return;
            }
            limpiarCamposPaciente();
            refrescarTodo();
            mostrarMensaje("Paciente actualizado.");
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void eliminarPacienteSeleccionado() {
        if (codigoPacienteSeleccionado == null) {
            mostrarError("Selecciona un paciente.");
            return;
        }
        try {
            if (sistema.eliminarPaciente(codigoPacienteSeleccionado)) {
                limpiarCamposPaciente();
                refrescarTodo();
                mostrarMensaje("Paciente eliminado.");
            } else {
                mostrarError("No se pudo eliminar el paciente.");
            }
        } catch (Exception ex) {
            mostrarError("No se pudo eliminar el paciente. " + ex.getMessage());
        }
    }
    private void registrarMedico() {
        try {
            Medico m = sistema.registrarMedico(
                txtMedicoNombres.getText().trim(),
                txtMedicoApellidos.getText().trim(),
                txtMedicoTelefono.getText().trim(),
                txtMedicoCmp.getText().trim(),
                (Especialidad) comboEspecialidad.getSelectedItem(),
                txtMedicoCorreo.getText().trim(),
                txtMedicoTurno.getText().trim()
            );
            limpiarCamposMedico();
            refrescarTodo();
            mostrarMensaje("Medico registrado con codigo " + m.getCodigo());
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void actualizarMedicoSeleccionado() {
        if (codigoMedicoSeleccionado == null) {
            mostrarError("Selecciona un medico.");
            return;
        }
        try {
            boolean ok = sistema.actualizarMedico(
                codigoMedicoSeleccionado,
                txtMedicoNombres.getText().trim(),
                txtMedicoApellidos.getText().trim(),
                txtMedicoTelefono.getText().trim(),
                txtMedicoCmp.getText().trim(),
                (Especialidad) comboEspecialidad.getSelectedItem(),
                txtMedicoCorreo.getText().trim(),
                txtMedicoTurno.getText().trim()
            );
            if (!ok) {
                mostrarError("No se pudo actualizar el medico.");
                return;
            }
            limpiarCamposMedico();
            refrescarTodo();
            mostrarMensaje("Medico actualizado.");
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void eliminarMedicoSeleccionado() {
        if (codigoMedicoSeleccionado == null) {
            mostrarError("Selecciona un medico.");
            return;
        }
        try {
            if (sistema.eliminarMedico(codigoMedicoSeleccionado)) {
                limpiarCamposMedico();
                refrescarTodo();
                mostrarMensaje("Medico eliminado.");
            } else {
                mostrarError("No se pudo eliminar el medico.");
            }
        } catch (Exception ex) {
            mostrarError("No se pudo eliminar el medico. " + ex.getMessage());
        }
    }
    private void registrarConsultorio() {
        if (sistema.listarConsultorios().length >= 15) {
            mostrarError("Capacidad maxima alcanzada. El hospital solo admite 15 consultorios.");
            return;
        }
        try {
            Consultorio c = sistema.registrarConsultorio(
                txtConsultorioNombre.getText().trim(),
                intentarParsearEntero(txtConsultorioPiso.getText().trim(), "Piso"),
                (Especialidad) comboConsultorioEspecialidad.getSelectedItem()
            );
            limpiarCamposConsultorio();
            refrescarTodo();
            mostrarMensaje("Consultorio registrado con codigo " + c.getCodigo());
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void actualizarConsultorioSeleccionado() {
        if (codigoConsultorioSeleccionado == null) {
            mostrarError("Selecciona un consultorio.");
            return;
        }
        try {
            boolean ok = sistema.actualizarConsultorio(
                codigoConsultorioSeleccionado,
                txtConsultorioNombre.getText().trim(),
                intentarParsearEntero(txtConsultorioPiso.getText().trim(), "Piso"),
                (Especialidad) comboConsultorioEspecialidad.getSelectedItem(),
                chkConsultorioDisponible.isSelected()
            );
            if (!ok) {
                mostrarError("No se pudo actualizar el consultorio.");
                return;
            }
            limpiarCamposConsultorio();
            refrescarTodo();
            mostrarMensaje("Consultorio actualizado.");
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void eliminarConsultorioSeleccionado() {
        if (codigoConsultorioSeleccionado == null) {
            mostrarError("Selecciona un consultorio.");
            return;
        }
        try {
            if (sistema.eliminarConsultorio(codigoConsultorioSeleccionado)) {
                limpiarCamposConsultorio();
                refrescarTodo();
                mostrarMensaje("Consultorio eliminado.");
            } else {
                mostrarError("No se pudo eliminar el consultorio.");
            }
        } catch (Exception ex) {
            mostrarError("No se pudo eliminar el consultorio. " + ex.getMessage());
        }
    }
    private void registrarCita() {
        try {
            Cita c = sistema.programarCita(
                txtCitaPaciente.getText().trim(),
                txtCitaMedico.getText().trim(),
                txtCitaConsultorio.getText().trim(),
                obtenerFecha(txtCitaFecha),
                obtenerHora(txtCitaHora),
                txtCitaMotivo.getText().trim(),
                intentarParsearDouble(txtCitaCosto.getText().trim(), "Costo")
            );
            if (c == null) {
                mostrarError("Verifica los codigos de paciente, medico y consultorio.");
                return;
            }
            limpiarCamposCita();
            refrescarTodo();
            mostrarMensaje("Cita registrada con codigo " + c.getCodigo());
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void actualizarCitaSeleccionada() {
        if (codigoCitaSeleccionada == null) {
            mostrarError("Selecciona una cita.");
            return;
        }
        try {
            boolean ok = sistema.actualizarCita(
                codigoCitaSeleccionada,
                txtCitaPaciente.getText().trim(),
                txtCitaMedico.getText().trim(),
                txtCitaConsultorio.getText().trim(),
                obtenerFecha(txtCitaFecha),
                obtenerHora(txtCitaHora),
                txtCitaMotivo.getText().trim(),
                intentarParsearDouble(txtCitaCosto.getText().trim(), "Costo")
            );
            if (!ok) {
                mostrarError("No se pudo actualizar la cita.");
                return;
            }
            limpiarCamposCita();
            refrescarTodo();
            mostrarMensaje("Cita actualizada.");
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }
    private void eliminarCitaSeleccionada() {
        if (codigoCitaSeleccionada == null) {
            mostrarError("Selecciona una cita.");
            return;
        }
        try {
            if (sistema.eliminarCita(codigoCitaSeleccionada)) {
                limpiarCamposCita();
                refrescarTodo();
                mostrarMensaje("Cita eliminada.");
            } else {
                mostrarError("No se pudo eliminar la cita.");
            }
        } catch (Exception ex) {
            mostrarError("No se pudo eliminar la cita. " + ex.getMessage());
        }
    }
    private void cambiarEstadoCita() { if (sistema.cambiarEstadoCita(txtCodigoCitaEstado.getText().trim(), (EstadoCita) comboEstadoCita.getSelectedItem())) { refrescarTodo(); mostrarMensaje("Estado de cita actualizado."); } else { mostrarError("No se encontro la cita."); } }
    private void autocompletarConsultorio() { Consultorio[] candidatos = sistema.listarConsultoriosDisponiblesParaMedico(txtCitaMedico.getText().trim()); if (candidatos.length == 0) { mostrarError("No hay consultorios compatibles para ese medico."); return; } txtCitaConsultorio.setText(candidatos[0].getCodigo()); }
    private void buscarPacientePorDniLineal() { Paciente p = sistema.buscarPacientePorDniLineal(txtBuscarDni.getText().trim()); mostrarResultadoBusquedaPaciente(p, "busqueda lineal por DNI"); }
    private void buscarPacientePorDniBinaria() { Paciente p = sistema.buscarPacientePorDniBinaria(txtBuscarDni.getText().trim()); mostrarResultadoBusquedaPaciente(p, "busqueda binaria por DNI"); }
    private void ordenarPacientesPorDni() { modeloPacientes.setRowCount(0); for (Paciente p : sistema.listarPacientesOrdenadosPorDni()) { modeloPacientes.addRow(new Object[]{p.getCodigo(), p.getNombreCompleto(), p.getDni(), p.getEdad(), p.getGenero(), p.getTipoSangre(), p.isTieneSis() ? "SI" : "NO", p.isTieneSis() ? p.getNumeroSis() : "-"}); } mostrarMensaje("Pacientes ordenados por DNI usando insercion."); }
    private void mostrarRecorridosArbol() {
        try {
            modelos.Paciente[] in = sistema.obtenerPacientesInorden();
            modelos.Paciente[] pre = sistema.obtenerPacientesPreorden();
            modelos.Paciente[] post = sistema.obtenerPacientesPostorden();

            javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Recorridos del Árbol Binario", true);
            dialog.setSize(750, 480);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new BorderLayout(12, 12));
            dialog.getContentPane().setBackground(new Color(245, 248, 252));

            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(tema.bannerTitulo);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
            JLabel titleLabel = new JLabel("Visualizador de Recorridos del Árbol Binario");
            titleLabel.setFont(fuentesinterfaz.heading(15));
            titleLabel.setForeground(Color.WHITE);
            headerPanel.add(titleLabel, BorderLayout.WEST);
            dialog.add(headerPanel, BorderLayout.NORTH);

            String[] columnNames = {"Código", "DNI", "Paciente", "Edad", "Género", "Sangre", "SIS"};
            DefaultTableModel dialogModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable dialogTable = crearTabla(dialogModel);
            JComponent scrollPane = crearScrollTabla(dialogTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

            JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
            selectorPanel.setOpaque(false);

            JButton btnInorden = new JButton("Inorden");
            JButton btnPreorden = new JButton("Preorden");
            JButton btnPostorden = new JButton("Postorden");

            applyDialogButtonStyle(btnInorden, true);
            applyDialogButtonStyle(btnPreorden, false);
            applyDialogButtonStyle(btnPostorden, false);

            Runnable loadInorden = () -> {
                dialogModel.setRowCount(0);
                for (modelos.Paciente p : in) {
                    dialogModel.addRow(new Object[]{
                        p.getCodigo(), p.getDni(), p.getNombreCompleto(), p.getEdad(), p.getGenero(), p.getTipoSangre(), p.isTieneSis() ? "SI" : "NO"
                    });
                }
            };

            Runnable loadPreorden = () -> {
                dialogModel.setRowCount(0);
                for (modelos.Paciente p : pre) {
                    dialogModel.addRow(new Object[]{
                        p.getCodigo(), p.getDni(), p.getNombreCompleto(), p.getEdad(), p.getGenero(), p.getTipoSangre(), p.isTieneSis() ? "SI" : "NO"
                    });
                }
            };

            Runnable loadPostorden = () -> {
                dialogModel.setRowCount(0);
                for (modelos.Paciente p : post) {
                    dialogModel.addRow(new Object[]{
                        p.getCodigo(), p.getDni(), p.getNombreCompleto(), p.getEdad(), p.getGenero(), p.getTipoSangre(), p.isTieneSis() ? "SI" : "NO"
                    });
                }
            };

            loadInorden.run();

            btnInorden.addActionListener(e -> {
                loadInorden.run();
                applyDialogButtonStyle(btnInorden, true);
                applyDialogButtonStyle(btnPreorden, false);
                applyDialogButtonStyle(btnPostorden, false);
            });

            btnPreorden.addActionListener(e -> {
                loadPreorden.run();
                applyDialogButtonStyle(btnInorden, false);
                applyDialogButtonStyle(btnPreorden, true);
                applyDialogButtonStyle(btnPostorden, false);
            });

            btnPostorden.addActionListener(e -> {
                loadPostorden.run();
                applyDialogButtonStyle(btnInorden, false);
                applyDialogButtonStyle(btnPreorden, false);
                applyDialogButtonStyle(btnPostorden, true);
            });

            selectorPanel.add(btnInorden);
            selectorPanel.add(btnPreorden);
            selectorPanel.add(btnPostorden);

            JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
            centerPanel.setOpaque(false);
            centerPanel.add(selectorPanel, BorderLayout.NORTH);
            centerPanel.add(scrollPane, BorderLayout.CENTER);
            dialog.add(centerPanel, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 12));
            bottomPanel.setOpaque(false);
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.setFont(fuentesinterfaz.heading(13));
            btnCerrar.setForeground(Color.WHITE);
            btnCerrar.setBackground(new Color(100, 110, 120));
            btnCerrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 110, 120), 1, true),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
            ));
            btnCerrar.addActionListener(e -> dialog.dispose());
            bottomPanel.add(btnCerrar);
            dialog.add(bottomPanel, BorderLayout.SOUTH);

            dialog.setVisible(true);
        } catch (Exception ex) {
            mostrarError("Error al obtener recorridos: " + ex.getMessage());
        }
    }

    private void applyDialogButtonStyle(JButton button, boolean active) {
        button.setFont(fuentesinterfaz.heading(13));
        if (active) {
            button.setForeground(Color.WHITE);
            button.setBackground(tema.bannerTitulo);
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(tema.bannerTitulo, 1, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
            ));
        } else {
            button.setForeground(tema.bannerTitulo);
            button.setBackground(new Color(236, 244, 252));
            button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(196, 214, 232), 1, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
            ));
        }
    }
    private void buscarMedicoPorCmpLineal() { Medico m = sistema.buscarMedicoPorCmpLineal(txtBuscarCmp.getText().trim()); mostrarResultadoBusquedaMedico(m, "busqueda lineal por CMP"); }
    private void buscarMedicoPorCmpBinaria() { Medico m = sistema.buscarMedicoPorCmpBinaria(txtBuscarCmp.getText().trim()); mostrarResultadoBusquedaMedico(m, "busqueda binaria por CMP"); }
    private void ordenarMedicosPorCmp() { modeloMedicos.setRowCount(0); for (Medico m : sistema.listarMedicosOrdenadosPorCmp()) { modeloMedicos.addRow(new Object[]{m.getCodigo(), m.getNombreCompleto(), m.getCmp(), m.getEspecialidad(), m.getTurno(), m.getCorreo()}); } mostrarMensaje("Medicos ordenados por CMP usando seleccion."); }
    private void mostrarResultadoBusquedaPaciente(Paciente paciente, String algoritmo) { if (paciente == null) { mostrarError("No se encontro un paciente con ese criterio."); return; } refrescarPacientes(); seleccionarFila(tablaPacientes, modeloPacientes, paciente.getCodigo()); mostrarMensaje("Paciente encontrado con " + algoritmo + ": " + paciente.getNombreCompleto()); }
    private void mostrarResultadoBusquedaMedico(Medico medico, String algoritmo) { if (medico == null) { mostrarError("No se encontro un medico con ese criterio."); return; } refrescarMedicos(); seleccionarFila(tablaMedicos, modeloMedicos, medico.getCodigo()); mostrarMensaje("Medico encontrado con " + algoritmo + ": " + medico.getNombreCompleto()); }
    private void seleccionarFila(JTable tabla, DefaultTableModel modelo, String codigo) { for (int i = 0; i < modelo.getRowCount(); i++) { if (codigo.equals(modelo.getValueAt(i, 0))) { int vistaFila = tabla.getRowSorter() != null ? tabla.convertRowIndexToView(i) : i; if (vistaFila >= 0) { tabla.setRowSelectionInterval(vistaFila, vistaFila); tabla.scrollRectToVisible(tabla.getCellRect(vistaFila, 0, true)); } break; } } }
    private void cargarPacienteDesdeTabla() { String codigo = obtenerCodigoSeleccionado(tablaPacientes); if (codigo == null) return; Paciente p = sistema.buscarPacientePorCodigo(codigo); if (p == null) return; codigoPacienteSeleccionado = p.getCodigo(); txtPacienteNombres.setText(p.getNombres()); txtPacienteApellidos.setText(p.getApellidos()); txtPacienteTelefono.setText(p.getTelefono()); txtPacienteDni.setText(p.getDni()); txtPacienteEdad.setText(String.valueOf(p.getEdad())); txtPacienteGenero.setText(p.getGenero()); txtPacienteSangre.setText(p.getTipoSangre()); txtPacienteAlergias.setText(p.getAlergias()); txtPacienteCorreo.setText(p.getCorreo()); chkPacienteSis.setSelected(p.isTieneSis()); txtPacienteNumeroSis.setEnabled(p.isTieneSis()); txtPacienteNumeroSis.setText(p.getNumeroSis() == null ? "" : p.getNumeroSis()); }
    private void cargarMedicoDesdeTabla() { String codigo = obtenerCodigoSeleccionado(tablaMedicos); if (codigo == null) return; Medico m = sistema.buscarMedicoPorCodigo(codigo); if (m == null) return; codigoMedicoSeleccionado = m.getCodigo(); txtMedicoNombres.setText(m.getNombres()); txtMedicoApellidos.setText(m.getApellidos()); txtMedicoTelefono.setText(m.getTelefono()); txtMedicoCmp.setText(m.getCmp()); txtMedicoCorreo.setText(m.getCorreo()); txtMedicoTurno.setText(m.getTurno()); comboEspecialidad.setSelectedItem(m.getEspecialidad()); }
    private void cargarConsultorioDesdeTabla() { String codigo = obtenerCodigoSeleccionado(tablaConsultorios); if (codigo == null) return; Consultorio c = sistema.buscarConsultorioPorCodigo(codigo); if (c == null) return; codigoConsultorioSeleccionado = c.getCodigo(); txtConsultorioNombre.setText(c.getNombre()); txtConsultorioPiso.setText(String.valueOf(c.getPiso())); comboConsultorioEspecialidad.setSelectedItem(c.getEspecialidad()); chkConsultorioDisponible.setSelected(c.isDisponible()); }
    private void cargarCitaDesdeTabla() { String codigo = obtenerCodigoSeleccionado(tablaCitas); if (codigo == null) return; Cita c = sistema.buscarCitaPorCodigo(codigo); if (c == null) return; codigoCitaSeleccionada = c.getCodigo(); txtCitaPaciente.setText(c.getPaciente().getCodigo()); txtCitaMedico.setText(c.getMedico().getCodigo()); txtCitaConsultorio.setText(c.getConsultorio().getCodigo()); txtCitaFecha.setDate(LocalDate.parse(c.getFecha())); txtCitaHora.setTime(LocalTime.parse(c.getHora())); txtCitaMotivo.setText(c.getMotivo()); txtCitaCosto.setText(FORMATO.format(c.getCostoConsulta())); txtCodigoCitaEstado.setText(c.getCodigo()); comboEstadoCita.setSelectedItem(c.getEstado()); }
    private void limpiarCamposPaciente() { codigoPacienteSeleccionado = null; txtPacienteNombres.setText(""); txtPacienteApellidos.setText(""); txtPacienteTelefono.setText(""); txtPacienteDni.setText(""); txtPacienteEdad.setText(""); txtPacienteGenero.setText(""); txtPacienteSangre.setText(""); txtPacienteAlergias.setText(""); txtPacienteCorreo.setText(""); chkPacienteSis.setSelected(false); txtPacienteNumeroSis.setText(""); txtPacienteNumeroSis.setEnabled(false); if (tablaPacientes != null) tablaPacientes.clearSelection(); }
    private void limpiarCamposMedico() { codigoMedicoSeleccionado = null; txtMedicoNombres.setText(""); txtMedicoApellidos.setText(""); txtMedicoTelefono.setText(""); txtMedicoCmp.setText(""); txtMedicoCorreo.setText(""); txtMedicoTurno.setText(""); comboEspecialidad.setSelectedIndex(0); if (tablaMedicos != null) tablaMedicos.clearSelection(); }
    private void limpiarCamposConsultorio() { codigoConsultorioSeleccionado = null; txtConsultorioNombre.setText(""); txtConsultorioPiso.setText(""); comboConsultorioEspecialidad.setSelectedIndex(0); if (chkConsultorioDisponible != null) chkConsultorioDisponible.setSelected(true); if (tablaConsultorios != null) tablaConsultorios.clearSelection(); }
    private void limpiarCamposCita() { codigoCitaSeleccionada = null; txtCitaPaciente.setText(""); txtCitaMedico.setText(""); txtCitaConsultorio.setText(""); txtCitaFecha.setDate(LocalDate.now()); txtCitaHora.setTime(LocalTime.of(9, 0)); txtCitaMotivo.setText(""); txtCitaCosto.setText("150.00"); txtCodigoCitaEstado.setText(""); comboEstadoCita.setSelectedIndex(0); if (tablaCitas != null) tablaCitas.clearSelection(); }
    private String obtenerCodigoSeleccionado(JTable tabla) { int fila = tabla.getSelectedRow(); if (fila < 0) return null; Object valor = tabla.getValueAt(fila, 0); return valor == null ? null : valor.toString(); }
    private String obtenerFecha(DatePicker picker) { LocalDate fecha = picker.getDate(); return (fecha == null ? LocalDate.now() : fecha).format(FORMATO_FECHA); }
    private String obtenerHora(TimePicker picker) { LocalTime hora = picker.getTime(); return (hora == null ? LocalTime.of(9, 0) : hora).format(FORMATO_HORA); }
    private int intentarParsearEntero(String texto, String nombreCampo) {
        if (texto == null || texto.isBlank()) {
            throw new RuntimeException("El campo '" + nombreCampo + "' no puede estar vacio.");
        }
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El campo '" + nombreCampo + "' debe ser un numero entero valido.");
        }
    }
    private double intentarParsearDouble(String texto, String nombreCampo) {
        if (texto == null || texto.isBlank()) {
            throw new RuntimeException("El campo '" + nombreCampo + "' no puede estar vacio.");
        }
        try {
            return Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            throw new RuntimeException("El campo '" + nombreCampo + "' debe ser un numero decimal valido.");
        }
    }
    private void mostrarMensaje(String mensaje) { JOptionPane.showMessageDialog(this, mensaje, "CORE-SALUD", JOptionPane.INFORMATION_MESSAGE); }
    private void mostrarError(String mensaje) { JOptionPane.showMessageDialog(this, mensaje, "CORE-SALUD", JOptionPane.WARNING_MESSAGE); }

    // ====== MAPA DE CONSULTORIOS ======
    private void refrescarConsultoriosMapa() {
        refrescarConsultorios();
        if (panelMapa == null) return;
        panelMapa.removeAll();
        Consultorio[][] consultoriosMatriz = new Consultorio[3][5];
        int[] col = {0, 0, 0};
        for (Consultorio c : sistema.listarConsultorios()) {
            int fila = c.getPiso() - 1;
            if (fila >= 0 && fila < 3 && col[fila] < 5) {
                consultoriosMatriz[fila][col[fila]] = c;
                col[fila]++;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                JButton btn = new JButton("P" + (i + 1) + "-C" + (j + 1));
                btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
                if (j < col[i]) {
                    Consultorio c = consultoriosMatriz[i][j];
                    if (c != null) {
                        btn.setBackground(c.isDisponible() ? new Color(56, 175, 105) : new Color(220, 76, 70));
                        btn.setToolTipText("<html><b>" + c.getNombre() + "</b> (" + c.getCodigo() + ")<br>Especialidad: " + c.getEspecialidad() + "<br>Estado: " + (c.isDisponible() ? "Disponible" : "Ocupado") + "</html>");
                    } else {
                        btn.setBackground(new Color(200, 210, 220));
                        btn.setEnabled(false);
                    }
                } else {
                    btn.setBackground(new Color(200, 210, 220));
                    btn.setEnabled(false);
                }
                panelMapa.add(btn);
            }
        }
        panelMapa.revalidate();
        panelMapa.repaint();
    }

    // ====== PANEL DE TRIAJE ======
    private JComponent crearPanelTriaje() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setOpaque(false);
        panelredondeado form = crearCajaBlanca(24, "Registro de Triaje");
        JPanel campos = new JPanel(new GridLayout(0, 1, 0, 8));
        campos.setOpaque(false);
        txtTriajePaciente = new JTextField();
        txtTriajeFecha = new JTextField(LocalDate.now().toString());
        txtTriajePeso = new JTextField(); txtTriajeTalla = new JTextField();
        txtTriajeTemperatura = new JTextField(); txtTriajePresion = new JTextField();
        txtTriajeFrecuencia = new JTextField();
        comboPrioridad = new JComboBox<>(new String[]{"NORMAL", "PRIORITARIO", "URGENTE"});
        agregarCampo(campos, "Cod. Paciente", txtTriajePaciente);
        agregarCampo(campos, "Fecha", txtTriajeFecha);
        agregarCampo(campos, "Peso (kg)", txtTriajePeso);
        agregarCampo(campos, "Talla (m)", txtTriajeTalla);
        agregarCampo(campos, "Temperatura", txtTriajeTemperatura);
        agregarCampo(campos, "Presion arterial", txtTriajePresion);
        agregarCampo(campos, "Frec. cardiaca", txtTriajeFrecuencia);
        agregarCampo(campos, "Prioridad", comboPrioridad);
        JPanel acc = crearFilaBotones(
            crearBotonPrimario("Encolar paciente", e -> registrarTriaje()),
            crearBotonSecundario("Atender siguiente", e -> atenderSiguienteTriaje())
        );
        JPanel cont = new JPanel(new BorderLayout(0, 12)); cont.setOpaque(false);
        cont.add(campos, BorderLayout.CENTER); cont.add(acc, BorderLayout.SOUTH);
        form.add(cont, BorderLayout.CENTER);
        form.setPreferredSize(new Dimension(430, 100));
        JPanel derecha = new JPanel(new BorderLayout(0, 12));
        derecha.setOpaque(false);
        modeloTriajes = new DefaultTableModel(new Object[]{"Codigo", "Paciente", "Fecha", "Temp.", "Presion", "FC", "Prioridad"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tablaTriajes = crearTabla(modeloTriajes);
        
        panelredondeado esperaPanel = crearCajaBlanca(20, "Cola de Espera  [Cola Enlazada FIFO]");
        
        // Control de tipo de cola (FIFO vs Prioridad)
        JPanel ctrlCola = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        ctrlCola.setOpaque(false);
        JLabel lblModo = new JLabel("Estructura de la cola:");
        lblModo.setFont(fuentesinterfaz.body(12));
        lblModo.setForeground(tema.estadoTexto);
        
        String[] opcionesCola = {"Cola Enlazada FIFO Propia", "Cola con Prioridad Propia"};
        JComboBox<String> comboModoCola = new JComboBox<>(opcionesCola);
        comboModoCola.setFont(fuentesinterfaz.body(12));
        comboModoCola.addActionListener(e -> {
            boolean usarPrioridad = comboModoCola.getSelectedIndex() == 1;
            sistema.setModoPrioridadTriaje(usarPrioridad);
            refrescarTriaje();
            
            // Actualizar dinámicamente el título del borde
            javax.swing.border.Border border = esperaPanel.getBorder();
            if (border instanceof javax.swing.border.CompoundBorder) {
                javax.swing.border.Border outside = ((javax.swing.border.CompoundBorder) border).getOutsideBorder();
                if (outside instanceof javax.swing.border.TitledBorder) {
                    String queueName = usarPrioridad ? "Cola con Prioridad Propia" : "Cola Enlazada FIFO Propia";
                    ((javax.swing.border.TitledBorder) outside).setTitle("Cola de Espera  [" + queueName + "]");
                    esperaPanel.repaint();
                }
            }
        });
        ctrlCola.add(lblModo);
        ctrlCola.add(comboModoCola);
        esperaPanel.add(ctrlCola, BorderLayout.NORTH);

        modeloListaEspera = new javax.swing.DefaultListModel<>();
        listaEspera = new javax.swing.JList<>(modeloListaEspera);
        listaEspera.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        listaEspera.setBackground(new Color(245, 250, 255));
        JScrollPane scrollEspera = new JScrollPane(listaEspera);
        scrollEspera.setBorder(BorderFactory.createEmptyBorder());
        scrollEspera.setPreferredSize(new Dimension(100, 130));
        esperaPanel.add(scrollEspera, BorderLayout.CENTER);
        
        derecha.add(crearScrollTabla(tablaTriajes), BorderLayout.CENTER);
        derecha.add(esperaPanel, BorderLayout.SOUTH);
        panel.add(form, BorderLayout.WEST);
        panel.add(derecha, BorderLayout.CENTER);
        return panel;
    }

    private void registrarTriaje() {
        try {
            modelos.RegistroTriaje t = sistema.registrarTriaje(
                txtTriajePaciente.getText().trim(), txtTriajeFecha.getText().trim(),
                intentarParsearDouble(txtTriajePeso.getText().trim(), "Peso"),
                intentarParsearDouble(txtTriajeTalla.getText().trim(), "Talla"),
                intentarParsearDouble(txtTriajeTemperatura.getText().trim(), "Temperatura"),
                txtTriajePresion.getText().trim(),
                intentarParsearEntero(txtTriajeFrecuencia.getText().trim(), "Frecuencia Cardiaca"),
                (String) comboPrioridad.getSelectedItem()
            );
            if (t == null) { mostrarError("No existe un paciente con ese codigo."); return; }
            refrescarTriaje();
            mostrarMensaje("Paciente encolado: " + t.getCodigo());
        } catch (Exception ex) { mostrarError(ex.getMessage()); }
    }

    private void atenderSiguienteTriaje() {
        modelos.RegistroTriaje t = sistema.atenderSiguienteTriaje();
        if (t == null) { mostrarError("No hay pacientes en la cola de espera."); return; }
        refrescarTriaje();
        mostrarMensaje("Atendiendo: " + t.getNombrePaciente());
    }

    private void refrescarTriaje() {
        if (modeloTriajes == null) return;
        modeloTriajes.setRowCount(0);
        for (modelos.RegistroTriaje t : sistema.listarHistorialTriaje()) {
            modeloTriajes.addRow(new Object[]{
                t.getCodigo(), t.getNombrePaciente(), t.getFecha(),
                t.getTemperatura(), t.getPresionArterial(), t.getFrecuenciaCardiaca(), t.getNivelPrioridad()
            });
        }
        if (modeloListaEspera != null) {
            modeloListaEspera.clear();
            for (modelos.RegistroTriaje t : sistema.listarEnEspera()) {
                modeloListaEspera.addElement("[" + t.getNivelPrioridad() + "] " + t.getNombrePaciente());
            }
        }
    }

    // ====== PANEL DE MEDICAMENTOS ======
    private JComponent crearPanelMedicamentos() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setOpaque(false);
        panelredondeado form = crearCajaBlanca(24, "Farmacia");
        JPanel campos = new JPanel(new GridLayout(0, 1, 0, 8));
        campos.setOpaque(false);
        txtMedNombre = new JTextField(); txtMedCategoria = new JTextField();
        txtMedDescripcion = new JTextField(); txtMedPrecio = new JTextField();
        txtMedStock = new JTextField();
        agregarCampo(campos, "Nombre", txtMedNombre);
        agregarCampo(campos, "Categoria", txtMedCategoria);
        agregarCampo(campos, "Descripcion", txtMedDescripcion);
        agregarCampo(campos, "Precio (S/)", txtMedPrecio);
        agregarCampo(campos, "Stock", txtMedStock);
        JPanel acc = crearFilaBotones(
            crearBotonPrimario("Registrar", e -> registrarMedicamento()),
            crearBotonSecundario("Actualizar", e -> actualizarMedicamento()),
            crearBotonSecundario("Eliminar", e -> eliminarMedicamento()),
            crearBotonSecundario("Limpiar", e -> limpiarCamposMed())
        );

        // Panel de Vista Previa de Presentacion de Medicamentos
        JPanel previewPanel = new JPanel(new BorderLayout(8, 8));
        previewPanel.setOpaque(false);
        previewPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(200, 220, 240), 1, true), "Presentación", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, fuentesinterfaz.heading(12), tema.bannerTitulo),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));

        lblMedImagen = new JLabel();
        lblMedImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblMedImagen.setPreferredSize(new Dimension(100, 100));

        lblMedTipoPresentacion = new JLabel("Selecciona un medicamento", SwingConstants.CENTER);
        lblMedTipoPresentacion.setFont(fuentesinterfaz.heading(13));
        lblMedTipoPresentacion.setForeground(tema.bannerTitulo);

        previewPanel.add(lblMedImagen, BorderLayout.CENTER);
        previewPanel.add(lblMedTipoPresentacion, BorderLayout.SOUTH);

        JPanel cont = new JPanel(new BorderLayout(0, 12)); cont.setOpaque(false);
        cont.add(campos, BorderLayout.NORTH);
        cont.add(previewPanel, BorderLayout.CENTER);
        cont.add(acc, BorderLayout.SOUTH);
        form.add(cont, BorderLayout.CENTER);
        form.setPreferredSize(new Dimension(430, 680));
        JScrollPane scrollForm = new JScrollPane(form);
        scrollForm.setPreferredSize(new Dimension(430, 100));
        scrollForm.setBorder(BorderFactory.createEmptyBorder());
        scrollForm.setOpaque(false);
        scrollForm.getViewport().setOpaque(false);
        scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollForm.getVerticalScrollBar().setUnitIncrement(18);
        scrollForm.getVerticalScrollBar().setPreferredSize(new Dimension(16, 0));

        JPanel derecha = new JPanel(new BorderLayout(0, 12));
        derecha.setOpaque(false);
        panelredondeado busquedaPanel = crearCajaBlanca(20, "Busqueda de medicamentos");
        txtBuscarMed = new JTextField();
        JButton btnBuscar = crearBotonPrimario("Buscar", e -> refrescarMedicamentos(txtBuscarMed.getText().trim()));
        JPanel filaBuscar = new JPanel(new BorderLayout(8, 0)); filaBuscar.setOpaque(false);
        filaBuscar.add(crearCampoHorizontal("Texto", txtBuscarMed), BorderLayout.CENTER);
        filaBuscar.add(btnBuscar, BorderLayout.EAST);
        busquedaPanel.add(filaBuscar, BorderLayout.CENTER);
        modeloMedicamentos = new DefaultTableModel(new Object[]{"Codigo", "Nombre", "Categoria", "Precio", "Stock"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaMedicamentos = crearTabla(modeloMedicamentos);
        tablaMedicamentos.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarMedicamentoDesdeTabla(); });
        derecha.add(busquedaPanel, BorderLayout.NORTH);
        derecha.add(crearScrollTabla(tablaMedicamentos), BorderLayout.CENTER);
        panel.add(scrollForm, BorderLayout.WEST);
        panel.add(derecha, BorderLayout.CENTER);
        return panel;
    }

    private void registrarMedicamento() {
        try {
            modelos.Medicamento m = sistema.registrarMedicamento(
                txtMedNombre.getText().trim(), txtMedCategoria.getText().trim(),
                txtMedDescripcion.getText().trim(),
                Double.parseDouble(txtMedPrecio.getText().trim()),
                Integer.parseInt(txtMedStock.getText().trim()));
            limpiarCamposMed(); refrescarMedicamentos("");
            mostrarMensaje("Medicamento registrado: " + m.getCodigo());
        } catch (NumberFormatException ex) { mostrarError("Precio y stock deben ser numericos."); }
    }

    private void actualizarMedicamento() {
        if (codigoMedSeleccionado == null) { mostrarError("Selecciona un medicamento."); return; }
        try {
            boolean ok = sistema.actualizarMedicamento(codigoMedSeleccionado,
                txtMedNombre.getText().trim(), txtMedCategoria.getText().trim(),
                txtMedDescripcion.getText().trim(),
                Double.parseDouble(txtMedPrecio.getText().trim()),
                Integer.parseInt(txtMedStock.getText().trim()));
            if (!ok) { mostrarError("No se pudo actualizar."); return; }
            limpiarCamposMed(); refrescarMedicamentos(""); mostrarMensaje("Medicamento actualizado.");
        } catch (NumberFormatException ex) { mostrarError("Precio y stock deben ser numericos."); }
    }

    private void eliminarMedicamento() {
        if (codigoMedSeleccionado == null) { mostrarError("Selecciona un medicamento."); return; }
        if (sistema.eliminarMedicamento(codigoMedSeleccionado)) {
            limpiarCamposMed(); refrescarMedicamentos(""); mostrarMensaje("Medicamento eliminado.");
        } else { mostrarError("No se pudo eliminar."); }
    }

    private void cargarMedicamentoDesdeTabla() {
        int fila = tablaMedicamentos.getSelectedRow();
        if (fila < 0) return;
        codigoMedSeleccionado = tablaMedicamentos.getValueAt(fila, 0).toString();
        modelos.Medicamento m = sistema.buscarMedicamentoPorCodigo(codigoMedSeleccionado);
        if (m == null) return;
        txtMedNombre.setText(m.getNombre()); txtMedCategoria.setText(m.getCategoria());
        txtMedDescripcion.setText(m.getDescripcion());
        txtMedPrecio.setText(String.valueOf(m.getPrecio()));
        txtMedStock.setText(String.valueOf(m.getStock()));
        actualizarImagenMedicamento(m);
    }

    private void actualizarImagenMedicamento(modelos.Medicamento m) {
        if (m == null) {
            lblMedImagen.setIcon(null);
            lblMedTipoPresentacion.setText("Selecciona un medicamento");
            return;
        }

        String desc = m.getDescripcion().toLowerCase();
        String cat = m.getCategoria().toLowerCase();
        String nom = m.getNombre().toLowerCase();

        String filename = "generico.png";
        String presentacionText = "Genérico / Otros";

        if (desc.contains("jarabe") || desc.contains("suspension") || nom.contains("jarabe") || cat.contains("jarabe")) {
            filename = "jarabe.png";
            presentacionText = "Jarabe / Líquido Oral";
        } else if (desc.contains("crema") || desc.contains("pomada") || desc.contains("gel") || desc.contains("ungüento") || cat.contains("crema") || cat.contains("gel")) {
            filename = "crema.png";
            presentacionText = "Crema / Tópico";
        } else if (desc.contains("ampolla") || desc.contains("inyectable") || desc.contains("vial") || cat.contains("inyect")) {
            filename = "inyectable.png";
            presentacionText = "Inyectable / Ampolla";
        } else if (desc.contains("tableta") || desc.contains("pastilla") || desc.contains("capsula") || desc.contains("comprimido") || cat.contains("tableta") || cat.contains("capsula") || cat.contains("pastilla")) {
            filename = "tableta.png";
            presentacionText = "Tableta / Cápsula";
        } else if (desc.contains("inhalador") || desc.contains("spray") || nom.contains("spray") || nom.contains("inhalador") || cat.contains("inhalador")) {
            filename = "inhalador.png";
            presentacionText = "Inhalador / Spray";
        }

        try {
            String path = "/recursos/" + filename;
            java.net.URL imgUrl = getClass().getResource(path);
            if (imgUrl != null) {
                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgUrl);
                java.awt.Image img = icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                lblMedImagen.setIcon(new javax.swing.ImageIcon(img));
            } else {
                java.io.File file = new java.io.File("recursos/" + filename);
                if (file.exists()) {
                    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(file.getAbsolutePath());
                    java.awt.Image img = icon.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
                    lblMedImagen.setIcon(new javax.swing.ImageIcon(img));
                } else {
                    lblMedImagen.setIcon(null);
                }
            }
        } catch (Exception e) {
            lblMedImagen.setIcon(null);
        }

        lblMedTipoPresentacion.setText(presentacionText);
    }

    private void refrescarMedicamentos(String filtro) {
        if (modeloMedicamentos == null) return;
        modeloMedicamentos.setRowCount(0);
        for (modelos.Medicamento m : sistema.listarMedicamentos(filtro)) {
            modeloMedicamentos.addRow(new Object[]{
                m.getCodigo(), m.getNombre(), m.getCategoria(),
                "S/ " + FORMATO.format(m.getPrecio()), m.getStock()
            });
        }
    }

    private void limpiarCamposMed() {
        codigoMedSeleccionado = null;
        if (txtMedNombre != null) { txtMedNombre.setText(""); txtMedCategoria.setText(""); txtMedDescripcion.setText(""); txtMedPrecio.setText(""); txtMedStock.setText(""); }
        if (tablaMedicamentos != null) tablaMedicamentos.clearSelection();
        if (lblMedImagen != null) { lblMedImagen.setIcon(null); lblMedTipoPresentacion.setText("Selecciona un medicamento"); }
    }

    // ====== PANEL DEL HISTORIAL CLINICO ======

    /**
     * Construye el panel de Historial Clinico del Paciente.
     * Muestra una ficha con la consulta actual y botones Anterior/Siguiente
     * que permiten navegar por el historial.
     */
    private JComponent crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setOpaque(false);

        // ---- Formulario de busqueda y registro ----
        panelredondeado formPanel = crearCajaBlanca(24, "Historial Clinico");
        JPanel form = new JPanel(new MigLayout("fillx, insets 6 10 10 10, gapy 4", "[grow,fill]", ""));
        form.setOpaque(false);

        txtHistorialCodPaciente = crearCampoTextoSeguro();
        txtHistorialCodMedico   = crearCampoTextoSeguro();
        txtHistorialFecha       = crearCampoTextoSeguro();
        txtHistorialFecha.setText(java.time.LocalDate.now().toString());
        txtHistorialDiagnostico    = crearCampoTextoSeguro();
        txtHistorialMedicamentos   = crearCampoTextoSeguro();
        txtHistorialObservaciones  = crearCampoTextoSeguro();

        agregarCampoSeguro(form, "Cod. Paciente", txtHistorialCodPaciente);
        agregarCampoSeguro(form, "Cod. Medico",   txtHistorialCodMedico);
        agregarCampoSeguro(form, "Fecha",          txtHistorialFecha);
        agregarCampoSeguro(form, "Diagnostico",    txtHistorialDiagnostico);
        agregarCampoSeguro(form, "Medicamentos recetados", txtHistorialMedicamentos);
        agregarCampoSeguro(form, "Observaciones",  txtHistorialObservaciones);

        // Boton guardar nueva consulta
        JButton btnGuardarConsulta = crearBotonPrimario("Guardar consulta", e -> guardarConsultaHistorial());
        form.add(btnGuardarConsulta, "growx, h 46!, wrap");

        // Separador visual
        JLabel lblSep = new JLabel("-- Buscar historial por codigo de paciente --");
        lblSep.setFont(fuentesinterfaz.body(12));
        lblSep.setForeground(new Color(120, 140, 170));
        form.add(lblSep, "growx, wrap");

        // Campo de busqueda
        JTextField txtBuscarPac = crearCampoTextoSeguro();
        agregarCampoSeguro(form, "Cod. Paciente (buscar)", txtBuscarPac);
        JButton btnBuscarHist = crearBotonPrimario("Abrir historial", e -> abrirHistorialPaciente(txtBuscarPac.getText().trim()));
        form.add(btnBuscarHist, "growx, h 46!, wrap");

        formPanel.add(form, BorderLayout.CENTER);
        formPanel.setPreferredSize(new Dimension(440, 780));

        JScrollPane scrollForm = new JScrollPane(formPanel);
        scrollForm.setPreferredSize(new Dimension(440, 100));
        scrollForm.setBorder(BorderFactory.createEmptyBorder());
        scrollForm.setOpaque(false);
        scrollForm.getViewport().setOpaque(false);
        scrollForm.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollForm.getVerticalScrollBar().setUnitIncrement(18);
        scrollForm.getVerticalScrollBar().setPreferredSize(new Dimension(16, 0));

        // ---- Panel de la Ficha Clinica (lado derecho) ----
        panelredondeado fichaPanel = crearCajaBlanca(24, "Perfil Clinico del Paciente");
        fichaPanel.setLayout(new BorderLayout(0, 14));

        // Cabecera de la ficha con codigo de consulta
        JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 8));
        cabecera.setOpaque(false);
        lblFichaCodigo = new JLabel("Sin historial cargado");
        lblFichaCodigo.setFont(fuentesinterfaz.heading(15));
        lblFichaCodigo.setForeground(new Color(96, 50, 140));
        cabecera.add(lblFichaCodigo);
        fichaPanel.add(cabecera, BorderLayout.NORTH);

        // Contenido central de la ficha
        JPanel contenidoFicha = new JPanel(new MigLayout("fillx, insets 10 14 10 14, gapy 10", "[160!][grow,fill]", ""));
        contenidoFicha.setOpaque(false);

        lblFichaFecha        = crearEtiquetaFicha("-");
        lblFichaMedico       = crearEtiquetaFicha("-");
        lblFichaEspecialidad = crearEtiquetaFicha("-");
        lblFichaDiagnostico  = crearEtiquetaFicha("-");
        lblFichaMedicamentos = crearEtiquetaFicha("-");
        lblFichaObservaciones= crearEtiquetaFicha("-");

        agregarFilaFicha(contenidoFicha, "Fecha:",           lblFichaFecha);
        agregarFilaFicha(contenidoFicha, "Medico tratante:", lblFichaMedico);
        agregarFilaFicha(contenidoFicha, "Especialidad:",    lblFichaEspecialidad);
        agregarFilaFicha(contenidoFicha, "Diagnostico:",     lblFichaDiagnostico);
        agregarFilaFicha(contenidoFicha, "Medicamentos:",    lblFichaMedicamentos);
        agregarFilaFicha(contenidoFicha, "Observaciones:",   lblFichaObservaciones);

        fichaPanel.add(contenidoFicha, BorderLayout.CENTER);

        // Botones de navegacion Anterior / Siguiente
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navPanel.setOpaque(false);
        btnHistAnterior = new JButton("<< Consulta Anterior");
        btnHistAnterior.setFont(fuentesinterfaz.heading(14));
        btnHistAnterior.setBackground(new Color(103, 58, 183));
        btnHistAnterior.setForeground(Color.WHITE);
        btnHistAnterior.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnHistAnterior.setFocusPainted(false);
        btnHistAnterior.setEnabled(false);
        btnHistAnterior.addActionListener(e -> navegarHistorialAnterior());

        btnHistSiguiente = new JButton("Siguiente Consulta >>");
        btnHistSiguiente.setFont(fuentesinterfaz.heading(14));
        btnHistSiguiente.setBackground(new Color(103, 58, 183));
        btnHistSiguiente.setForeground(Color.WHITE);
        btnHistSiguiente.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnHistSiguiente.setFocusPainted(false);
        btnHistSiguiente.setEnabled(false);
        btnHistSiguiente.addActionListener(e -> navegarHistorialSiguiente());

        navPanel.add(btnHistAnterior);
        navPanel.add(btnHistSiguiente);
        fichaPanel.add(navPanel, BorderLayout.SOUTH);

        panel.add(scrollForm, BorderLayout.WEST);
        panel.add(fichaPanel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea una etiqueta de valor para la ficha clinica.
     */
    private JLabel crearEtiquetaFicha(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(fuentesinterfaz.body(14));
        label.setForeground(new Color(40, 60, 90));
        return label;
    }

    /**
     * Agrega una fila etiqueta-valor al panel de la ficha clinica.
     */
    private void agregarFilaFicha(JPanel panel, String etiqueta, JLabel valor) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(fuentesinterfaz.heading(13));
        lbl.setForeground(new Color(96, 50, 140));
        panel.add(lbl, "right");
        panel.add(valor, "wrap");
    }

    /**
     * Guarda una nueva consulta en el historial del paciente.
     * La consulta se inserta al FINAL de la Lista Doblemente Enlazada.
     */
    private void guardarConsultaHistorial() {
        String codPac = txtHistorialCodPaciente.getText().trim();
        String codMed = txtHistorialCodMedico.getText().trim();
        String fecha  = txtHistorialFecha.getText().trim();
        String diag   = txtHistorialDiagnostico.getText().trim();
        String meds   = txtHistorialMedicamentos.getText().trim();
        String obs    = txtHistorialObservaciones.getText().trim();

        if (codPac.isEmpty() || codMed.isEmpty() || diag.isEmpty()) {
            mostrarError("Cod. Paciente, Cod. Medico y Diagnostico son obligatorios.");
            return;
        }
        ConsultaClinica consulta = sistema.registrarConsultaHistorial(codPac, fecha, codMed, diag, meds, obs);
        if (consulta == null) {
            mostrarError("No se encontro el paciente o medico con esos codigos.");
            return;
        }
        // Limpiar campos del formulario
        txtHistorialDiagnostico.setText("");
        txtHistorialMedicamentos.setText("");
        txtHistorialObservaciones.setText("");
        // Mostrar la consulta recien guardada en la ficha
        codigoPacienteHistorial = codPac;
        actualizarFichaClinica(consulta);
        mostrarMensaje("Consulta guardada: " + consulta.getCodigo());
    }

    /**
     * Carga y muestra la consulta MAS RECIENTE del paciente buscado.
     * Posiciona el cursor de la lista al final.
     */
    private void abrirHistorialPaciente(String codigoPaciente) {
        if (codigoPaciente.isEmpty()) {
            mostrarError("Ingresa el codigo del paciente.");
            return;
        }
        if (!sistema.pacienteTieneHistorial(codigoPaciente)) {
            mostrarError("El paciente no tiene consultas registradas en el historial.");
            return;
        }
        codigoPacienteHistorial = codigoPaciente;
        ConsultaClinica consulta = sistema.obtenerUltimaConsulta(codigoPaciente);
        actualizarFichaClinica(consulta);
    }

    /**
     * Navega a la consulta ANTERIOR en la Lista Doblemente Enlazada
     * y actualiza la ficha clinica con los nuevos datos.
     */
    private void navegarHistorialAnterior() {
        if (codigoPacienteHistorial == null) return;
        ConsultaClinica consulta = sistema.irAConsultaAnterior(codigoPacienteHistorial);
        if (consulta == null) {
            mostrarError("Ya estas en la primera consulta del historial.");
            return;
        }
        actualizarFichaClinica(consulta);
    }

    /**
     * Navega a la consulta SIGUIENTE en la Lista Doblemente Enlazada
     * y actualiza la ficha clinica con los nuevos datos.
     */
    private void navegarHistorialSiguiente() {
        if (codigoPacienteHistorial == null) return;
        ConsultaClinica consulta = sistema.irAConsultaSiguiente(codigoPacienteHistorial);
        if (consulta == null) {
            mostrarError("Ya estas en la ultima consulta del historial.");
            return;
        }
        actualizarFichaClinica(consulta);
    }

    /**
     * Actualiza todos los campos de la ficha clinica con los datos
     * de la ConsultaClinica recibida, y habilita/deshabilita los botones
     * de navegacion segun la posicion del cursor en la lista doble.
     */
    private void actualizarFichaClinica(ConsultaClinica consulta) {
        if (consulta == null) {
            lblFichaCodigo.setText("Sin datos");
            lblFichaFecha.setText("-"); lblFichaMedico.setText("-");
            lblFichaEspecialidad.setText("-"); lblFichaDiagnostico.setText("-");
            lblFichaMedicamentos.setText("-"); lblFichaObservaciones.setText("-");
            btnHistAnterior.setEnabled(false); btnHistSiguiente.setEnabled(false);
            return;
        }
        int total = sistema.totalConsultasHistorial(codigoPacienteHistorial);
        lblFichaCodigo.setText(
            "Cod: " + consulta.getCodigo() +
            "  |  Paciente: " + consulta.getNombrePaciente() +
            "  |  Total consultas: " + total);
        lblFichaFecha.setText(consulta.getFecha());
        lblFichaMedico.setText(consulta.getNombreMedico());
        lblFichaEspecialidad.setText(consulta.getEspecialidad());
        lblFichaDiagnostico.setText(consulta.getDiagnostico());
        lblFichaMedicamentos.setText(
            consulta.getMedicamentosRecetados().isEmpty() ? "Ninguno" : consulta.getMedicamentosRecetados());
        lblFichaObservaciones.setText(
            consulta.getObservaciones().isEmpty() ? "-" : consulta.getObservaciones());
        // Actualizar botones de navegacion segun posicion en la lista
        btnHistAnterior.setEnabled(sistema.hayConsultaAnterior(codigoPacienteHistorial));
        btnHistSiguiente.setEnabled(sistema.hayConsultaSiguiente(codigoPacienteHistorial));
    }
}

