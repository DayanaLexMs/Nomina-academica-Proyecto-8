package vista;

import controlador.NominaControlador;
import modelo.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class VentanaPrincipal extends javax.swing.JFrame {

    // ══════════════════════════════════════════════════════
    //  COLORES INSTITUCIONALES
    // ══════════════════════════════════════════════════════
    private static final Color AZUL_OSCURO   = new Color(13, 71, 161);
    private static final Color AZUL_MEDIO    = new Color(25, 118, 210);
    private static final Color AZUL_CLARO    = new Color(227, 242, 253);
    private static final Color VERDE_ACCION  = new Color(27, 94, 32);
    private static final Color VERDE_BTN     = new Color(46, 125, 50);
    private static final Color ROJO_BTN      = new Color(183, 28, 28);
    private static final Color GRIS_PANEL    = new Color(245, 245, 245);
    private static final Color BLANCO        = Color.WHITE;
    private static final Color TEXTO_HEADER  = Color.WHITE;

    private NominaControlador controlador;
    private NumberFormat formatoPesos;

    // ══════════════════════════════════════════════════════
    //  COMPONENTES
    // ══════════════════════════════════════════════════════
    private JTabbedPane tabbedPane;

    // --- Tab Empleados ---
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTablaEmpleados;
    private JButton btnVerDetalle, btnEliminar, btnActualizar;

    // --- Tab Registrar Profesor ---
    private JComboBox<String> cmbTipoProfesor;
    private JTextField txtIdProf, txtNombresProf, txtApellidosProf;
    private JTextField txtFechaProf, txtSalarioProf, txtPerfilProf;
    private JTextField txtExtraProf;
    private JLabel lblExtraProf;
    private JCheckBox chkDedicacion;
    private JButton btnRegistrarProfesor;

    // --- Tab Registrar Administrativo ---
    private JTextField txtIdAdm, txtNombresAdm, txtApellidosAdm;
    private JTextField txtFechaAdm, txtCargoAdm, txtSalarioAdm;
    private JTextField txtAreaAdm, txtEscalafonAdm;
    private JButton btnRegistrarAdm;

    // --- Tab Asignaturas ---
    private JComboBox<String> cmbProfAsig;
    private JTextField txtCodAsig, txtNomAsig, txtCredAsig, txtHorasAsig;
    private JButton btnAgregarAsig;
    private JList<String> listaAsig;
    private DefaultListModel<String> modeloListaAsig;

    // --- Tab Proyectos ---
    private JComboBox<String> cmbProfProy;
    private JTextField txtCodProy, txtNomProy;
    private JComboBox<String> cmbTipoProy;
    private JButton btnAgregarProy;
    private JComboBox<String> cmbProyProducto;
    private JTextField txtTituloProducto;
    private JComboBox<String> cmbTipoProducto;
    private JTextField txtPuntosProducto;
    private JButton btnAgregarProducto;
    private JList<String> listaProyectos;
    private DefaultListModel<String> modeloListaProyectos;

    // --- Tab Nómina ---
    private JTable tablaNomina;
    private DefaultTableModel modeloTablaNomina;
    private JLabel lblTotalNomina;
    private JButton btnGenerarNomina;

    // ══════════════════════════════════════════════════════

    public VentanaPrincipal(NominaControlador controlador) {
        this.controlador = controlador;
        this.formatoPesos = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        initComponents();
        actualizarTablaEmpleados();
    }

    private void initComponents() {
        setTitle("Universidad del Norte Tecnológico — Sistema de Nómina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ── Header ──
        JPanel header = crearHeader();
        add(header, BorderLayout.NORTH);

        // ── Tabs ──
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.setBackground(AZUL_CLARO);

        tabbedPane.addTab("👥  Empleados",       crearTabEmpleados());
        tabbedPane.addTab("🎓  Reg. Profesor",   crearTabRegistrarProfesor());
        tabbedPane.addTab("🏢  Reg. Administrativo", crearTabRegistrarAdministrativo());
        tabbedPane.addTab("📚  Asignaturas",     crearTabAsignaturas());
        tabbedPane.addTab("🔬  Proyectos",       crearTabProyectos());
        tabbedPane.addTab("💰  Nómina",          crearTabNomina());

        add(tabbedPane, BorderLayout.CENTER);
        add(crearFooter(), BorderLayout.SOUTH);
    }

    // ══════════════════════════════════════════════════════
    //  HEADER
    // ══════════════════════════════════════════════════════
    private JPanel crearHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AZUL_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel titulo = new JLabel("🏛  Universidad del Norte Tecnológico");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(BLANCO);

        JLabel subtitulo = new JLabel("Sistema de Gestión de Personal y Nómina");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(187, 222, 251));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        p.add(textos, BorderLayout.WEST);
        return p;
    }

    // ══════════════════════════════════════════════════════
    //  TAB 1 — EMPLEADOS
    // ══════════════════════════════════════════════════════
    private JPanel crearTabEmpleados() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        panel.add(crearSubtitulo("Lista de Empleados Registrados"), BorderLayout.NORTH);

        // Tabla
        String[] cols = {"ID", "Nombre Completo", "Tipo", "Cargo", "Fecha Ingreso", "Antigüedad", "Salario Base"};
        modeloTablaEmpleados = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaEmpleados = crearTablaEstilizada(modeloTablaEmpleados);

        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBorder(crearBordeSombra());
        panel.add(scroll, BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        botones.setBackground(GRIS_PANEL);

        btnActualizar   = crearBoton("🔄 Actualizar",    AZUL_MEDIO);
        btnVerDetalle   = crearBoton("🔍 Ver Detalle",   VERDE_BTN);
        btnEliminar     = crearBoton("🗑 Eliminar",       ROJO_BTN);

        botones.add(btnActualizar);
        botones.add(btnVerDetalle);
        botones.add(btnEliminar);
        panel.add(botones, BorderLayout.SOUTH);

        // ── Listeners ──
        btnActualizar.addActionListener(e -> actualizarTablaEmpleados());

        btnVerDetalle.addActionListener(e -> {
            int fila = tablaEmpleados.getSelectedRow();
            if (fila < 0) { mostrarError("Seleccione un empleado."); return; }
            String id = (String) modeloTablaEmpleados.getValueAt(fila, 0);
            Empleado emp = controlador.buscarEmpleado(id);
            if (emp != null) mostrarDetalleEmpleado(emp);
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaEmpleados.getSelectedRow();
            if (fila < 0) { mostrarError("Seleccione un empleado."); return; }
            String id = (String) modeloTablaEmpleados.getValueAt(fila, 0);
            int resp = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar al empleado con ID " + id + "?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                controlador.eliminarEmpleado(id);
                actualizarTablaEmpleados();
                actualizarCombosProfesores();
            }
        });

        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  TAB 2 — REGISTRAR PROFESOR
    // ══════════════════════════════════════════════════════
    private JPanel crearTabRegistrarProfesor() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(crearSubtitulo("Registrar Nuevo Profesor"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BLANCO);
        form.setBorder(crearBordeSombra());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 12, 8, 12);
        g.fill   = GridBagConstraints.HORIZONTAL;

        // Tipo Profesor
        cmbTipoProfesor = new JComboBox<>(new String[]{"Asociado", "Catedrático", "Ocasional"});
        cmbTipoProfesor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        agregarCampoForm(form, g, "Tipo de Profesor:", cmbTipoProfesor, 0);

        txtIdProf      = new JTextField(20); agregarCampoForm(form, g, "Identificación:", txtIdProf, 1);
        txtNombresProf = new JTextField(20); agregarCampoForm(form, g, "Nombres:", txtNombresProf, 2);
        txtApellidosProf = new JTextField(20); agregarCampoForm(form, g, "Apellidos:", txtApellidosProf, 3);
        txtFechaProf   = new JTextField("2020-01-15", 20); agregarCampoForm(form, g, "Fecha Ingreso (YYYY-MM-DD):", txtFechaProf, 4);
        txtSalarioProf = new JTextField(20); agregarCampoForm(form, g, "Salario Base ($):", txtSalarioProf, 5);
        txtPerfilProf  = new JTextField(20); agregarCampoForm(form, g, "Perfil Académico:", txtPerfilProf, 6);

        lblExtraProf   = new JLabel("Dedicación Exclusiva:");
        lblExtraProf.setFont(new Font("Segoe UI", Font.BOLD, 13));
        chkDedicacion  = new JCheckBox();
        txtExtraProf   = new JTextField(20);
        txtExtraProf.setVisible(false);

        agregarCampoForm(form, g, "", lblExtraProf, 7);
        // Fila especial para el campo extra
        g.gridx = 0; g.gridy = 7;
        form.add(lblExtraProf, g);
        g.gridx = 1; g.gridy = 7;
        JPanel panelExtra = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelExtra.setBackground(BLANCO);
        panelExtra.add(chkDedicacion);
        panelExtra.add(txtExtraProf);
        form.add(panelExtra, g);

        // Cambio de tipo actualiza campo extra
        cmbTipoProfesor.addActionListener(e -> actualizarCampoExtra());
        actualizarCampoExtra();

        btnRegistrarProfesor = crearBoton("✅ Registrar Profesor", VERDE_BTN);
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        form.add(btnRegistrarProfesor, g);

        panel.add(form, BorderLayout.CENTER);

        btnRegistrarProfesor.addActionListener(e -> registrarProfesor());

        return panel;
    }

    private void actualizarCampoExtra() {
        String tipo = (String) cmbTipoProfesor.getSelectedItem();
        switch (tipo) {
            case "Asociado":
                lblExtraProf.setText("Dedicación Exclusiva:");
                chkDedicacion.setVisible(true);
                txtExtraProf.setVisible(false);
                break;
            case "Catedrático":
                lblExtraProf.setText("Valor por Hora ($):");
                chkDedicacion.setVisible(false);
                txtExtraProf.setVisible(true);
                txtExtraProf.setToolTipText("Valor en pesos por hora dictada");
                break;
            case "Ocasional":
                lblExtraProf.setText("Horas de Contrato:");
                chkDedicacion.setVisible(false);
                txtExtraProf.setVisible(true);
                txtExtraProf.setToolTipText("Número total de horas contratadas");
                break;
        }
    }

    // ══════════════════════════════════════════════════════
    //  TAB 3 — REGISTRAR ADMINISTRATIVO
    // ══════════════════════════════════════════════════════
    private JPanel crearTabRegistrarAdministrativo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(crearSubtitulo("Registrar Nuevo Administrativo"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BLANCO);
        form.setBorder(crearBordeSombra());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 12, 8, 12);
        g.fill   = GridBagConstraints.HORIZONTAL;

        txtIdAdm        = new JTextField(20); agregarCampoForm(form, g, "Identificación:", txtIdAdm, 0);
        txtNombresAdm   = new JTextField(20); agregarCampoForm(form, g, "Nombres:", txtNombresAdm, 1);
        txtApellidosAdm = new JTextField(20); agregarCampoForm(form, g, "Apellidos:", txtApellidosAdm, 2);
        txtFechaAdm     = new JTextField("2020-01-15", 20); agregarCampoForm(form, g, "Fecha Ingreso (YYYY-MM-DD):", txtFechaAdm, 3);
        txtCargoAdm     = new JTextField(20); agregarCampoForm(form, g, "Cargo:", txtCargoAdm, 4);
        txtSalarioAdm   = new JTextField(20); agregarCampoForm(form, g, "Salario Base ($):", txtSalarioAdm, 5);
        txtAreaAdm      = new JTextField(20); agregarCampoForm(form, g, "Área / Dependencia:", txtAreaAdm, 6);
        txtEscalafonAdm = new JTextField(20); agregarCampoForm(form, g, "Nivel Escalafón:", txtEscalafonAdm, 7);

        btnRegistrarAdm = crearBoton("✅ Registrar Administrativo", VERDE_BTN);
        g.gridx = 0; g.gridy = 8; g.gridwidth = 2;
        g.anchor = GridBagConstraints.CENTER;
        form.add(btnRegistrarAdm, g);

        panel.add(form, BorderLayout.CENTER);

        btnRegistrarAdm.addActionListener(e -> registrarAdministrativo());

        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  TAB 4 — ASIGNATURAS
    // ══════════════════════════════════════════════════════
    private JPanel crearTabAsignaturas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(crearSubtitulo("Gestión de Asignaturas"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new GridLayout(1, 2, 15, 0));
        contenido.setBackground(GRIS_PANEL);

        // ── Formulario ──
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BLANCO);
        form.setBorder(crearBordeSombra());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 12, 8, 12);
        g.fill   = GridBagConstraints.HORIZONTAL;

        cmbProfAsig = new JComboBox<>(); cmbProfAsig.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        agregarCampoForm(form, g, "Profesor:", cmbProfAsig, 0);

        txtCodAsig  = new JTextField(15); agregarCampoForm(form, g, "Código:", txtCodAsig, 1);
        txtNomAsig  = new JTextField(15); agregarCampoForm(form, g, "Nombre:", txtNomAsig, 2);
        txtCredAsig = new JTextField(15); agregarCampoForm(form, g, "Créditos:", txtCredAsig, 3);
        txtHorasAsig= new JTextField(15); agregarCampoForm(form, g, "Horas:", txtHorasAsig, 4);

        btnAgregarAsig = crearBoton("➕ Agregar Asignatura", VERDE_BTN);
        g.gridx = 0; g.gridy = 5; g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        form.add(btnAgregarAsig, g);
        contenido.add(form);

        // ── Lista ──
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.setBackground(BLANCO);
        panelLista.setBorder(crearBordeSombra());
        JLabel lblLista = new JLabel("  📋 Asignaturas del Profesor Seleccionado");
        lblLista.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLista.setForeground(AZUL_OSCURO);
        panelLista.add(lblLista, BorderLayout.NORTH);
        modeloListaAsig = new DefaultListModel<>();
        listaAsig = new JList<>(modeloListaAsig);
        listaAsig.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelLista.add(new JScrollPane(listaAsig), BorderLayout.CENTER);
        contenido.add(panelLista);

        panel.add(contenido, BorderLayout.CENTER);

        cmbProfAsig.addActionListener(e -> actualizarListaAsignaturas());
        btnAgregarAsig.addActionListener(e -> agregarAsignatura());

        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  TAB 5 — PROYECTOS
    // ══════════════════════════════════════════════════════
    private JPanel crearTabProyectos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(crearSubtitulo("Gestión de Proyectos y Productos Académicos"), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new GridLayout(1, 2, 15, 0));
        contenido.setBackground(GRIS_PANEL);

        // ── Panel izquierdo: registrar proyecto ──
        JPanel izq = new JPanel(new BorderLayout(5, 5));
        izq.setBackground(BLANCO);
        izq.setBorder(crearBordeSombra());

        JPanel formProy = new JPanel(new GridBagLayout());
        formProy.setBackground(BLANCO);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(7, 10, 7, 10);
        g.fill   = GridBagConstraints.HORIZONTAL;

        JLabel lbl1 = new JLabel("── Agregar Proyecto ──");
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl1.setForeground(AZUL_OSCURO);
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; formProy.add(lbl1, g); g.gridwidth = 1;

        cmbProfProy = new JComboBox<>(); cmbProfProy.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        agregarCampoForm(formProy, g, "Profesor:", cmbProfProy, 1);
        txtCodProy  = new JTextField(15); agregarCampoForm(formProy, g, "Código:", txtCodProy, 2);
        txtNomProy  = new JTextField(15); agregarCampoForm(formProy, g, "Nombre:", txtNomProy, 3);
        cmbTipoProy = new JComboBox<>(new String[]{"INVESTIGACION", "EXTENSION"});
        agregarCampoForm(formProy, g, "Tipo:", cmbTipoProy, 4);

        btnAgregarProy = crearBoton("➕ Agregar Proyecto", AZUL_MEDIO);
        g.gridx = 0; g.gridy = 5; g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        formProy.add(btnAgregarProy, g); g.gridwidth = 1;

        JSeparator sep = new JSeparator();
        g.gridx = 0; g.gridy = 6; g.gridwidth = 2; formProy.add(sep, g); g.gridwidth = 1;

        JLabel lbl2 = new JLabel("── Agregar Producto Académico ──");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl2.setForeground(AZUL_OSCURO);
        g.gridx = 0; g.gridy = 7; g.gridwidth = 2; formProy.add(lbl2, g); g.gridwidth = 1;

        cmbProyProducto = new JComboBox<>(); cmbProyProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        agregarCampoForm(formProy, g, "Proyecto:", cmbProyProducto, 8);
        txtTituloProducto = new JTextField(15); agregarCampoForm(formProy, g, "Título:", txtTituloProducto, 9);
        cmbTipoProducto = new JComboBox<>(new String[]{
                "ARTICULO", "LIBRO", "SOFTWARE", "INFORME_TECNICO", "PONENCIA"});
        agregarCampoForm(formProy, g, "Tipo:", cmbTipoProducto, 10);
        txtPuntosProducto = new JTextField(15); agregarCampoForm(formProy, g, "Puntos:", txtPuntosProducto, 11);

        btnAgregarProducto = crearBoton("➕ Agregar Producto", VERDE_BTN);
        g.gridx = 0; g.gridy = 12; g.gridwidth = 2; g.anchor = GridBagConstraints.CENTER;
        formProy.add(btnAgregarProducto, g);

        izq.add(new JScrollPane(formProy), BorderLayout.CENTER);
        contenido.add(izq);

        // ── Panel derecho: lista de proyectos ──
        JPanel der = new JPanel(new BorderLayout(5, 5));
        der.setBackground(BLANCO);
        der.setBorder(crearBordeSombra());
        JLabel lblLista = new JLabel("  🔬 Proyectos y Productos");
        lblLista.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblLista.setForeground(AZUL_OSCURO);
        der.add(lblLista, BorderLayout.NORTH);
        modeloListaProyectos = new DefaultListModel<>();
        listaProyectos = new JList<>(modeloListaProyectos);
        listaProyectos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        der.add(new JScrollPane(listaProyectos), BorderLayout.CENTER);
        contenido.add(der);

        panel.add(contenido, BorderLayout.CENTER);

        cmbProfProy.addActionListener(e -> {
            actualizarListaProyectos();
            actualizarComboProyectosProducto();
        });
        btnAgregarProy.addActionListener(e -> agregarProyecto());
        btnAgregarProducto.addActionListener(e -> agregarProducto());

        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  TAB 6 — NÓMINA
    // ══════════════════════════════════════════════════════
    private JPanel crearTabNomina() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(GRIS_PANEL);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(crearSubtitulo("Liquidación de Nómina Mensual"), BorderLayout.NORTH);

        String[] cols = {"ID", "Nombre Completo", "Tipo", "Salario Base",
                         "Bonificación / Extra", "TOTAL A PAGAR"};
        modeloTablaNomina = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaNomina = crearTablaEstilizada(modeloTablaNomina);

        // Colorear fila total
        tablaNomina.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (col == 5) {
                    c.setBackground(new Color(200, 230, 201));
                    c.setForeground(VERDE_ACCION);
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    c.setBackground(sel ? new Color(187, 222, 251) : BLANCO);
                    c.setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tablaNomina);
        scroll.setBorder(crearBordeSombra());
        panel.add(scroll, BorderLayout.CENTER);

        // Panel inferior
        JPanel sur = new JPanel(new BorderLayout());
        sur.setBackground(GRIS_PANEL);
        sur.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnGenerarNomina = crearBoton("💰 Generar / Actualizar Nómina", AZUL_OSCURO);
        btnGenerarNomina.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblTotalNomina = new JLabel("  Total Nómina: $ 0", SwingConstants.RIGHT);
        lblTotalNomina.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalNomina.setForeground(AZUL_OSCURO);

        sur.add(btnGenerarNomina, BorderLayout.WEST);
        sur.add(lblTotalNomina, BorderLayout.EAST);
        panel.add(sur, BorderLayout.SOUTH);

        btnGenerarNomina.addActionListener(e -> generarNomina());

        return panel;
    }

    // ══════════════════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════════════════
    private JPanel crearFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(AZUL_OSCURO);
        JLabel l = new JLabel("© 2025 Universidad del Norte Tecnológico — Sistema de Nómina v1.0");
        l.setForeground(new Color(187, 222, 251));
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        p.add(l);
        return p;
    }

    // ══════════════════════════════════════════════════════
    //  LÓGICA DE ACCIONES
    // ══════════════════════════════════════════════════════

    private void registrarProfesor() {
        try {
            String tipo   = (String) cmbTipoProfesor.getSelectedItem();
            String id     = txtIdProf.getText().trim();
            String nom    = txtNombresProf.getText().trim();
            String ape    = txtApellidosProf.getText().trim();
            String fecha  = txtFechaProf.getText().trim();
            double sal    = Double.parseDouble(txtSalarioProf.getText().trim());
            String perfil = txtPerfilProf.getText().trim();

            if (id.isEmpty() || nom.isEmpty() || ape.isEmpty() || perfil.isEmpty()) {
                mostrarError("Complete todos los campos obligatorios.");
                return;
            }
            if (controlador.buscarEmpleado(id) != null) {
                mostrarError("Ya existe un empleado con esa identificación.");
                return;
            }

            LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE);

            switch (tipo) {
                case "Asociado":
                    controlador.registrarProfesorAsociado(id, nom, ape, fechaDate,
                            sal, perfil, chkDedicacion.isSelected());
                    break;
                case "Catedrático":
                    double valHora = Double.parseDouble(txtExtraProf.getText().trim());
                    controlador.registrarProfesorCatedratico(id, nom, ape, fechaDate,
                            sal, perfil, valHora);
                    break;
                case "Ocasional":
                    int horas = Integer.parseInt(txtExtraProf.getText().trim());
                    controlador.registrarProfesorOcasional(id, nom, ape, fechaDate,
                            sal, perfil, horas);
                    break;
            }

            mostrarExito("Profesor registrado exitosamente.");
            limpiarFormProfesor();
            actualizarTablaEmpleados();
            actualizarCombosProfesores();

        } catch (Exception ex) {
            mostrarError("Error al registrar: " + ex.getMessage());
        }
    }

    private void registrarAdministrativo() {
        try {
            String id    = txtIdAdm.getText().trim();
            String nom   = txtNombresAdm.getText().trim();
            String ape   = txtApellidosAdm.getText().trim();
            String fecha = txtFechaAdm.getText().trim();
            String cargo = txtCargoAdm.getText().trim();
            double sal   = Double.parseDouble(txtSalarioAdm.getText().trim());
            String area  = txtAreaAdm.getText().trim();
            String esc   = txtEscalafonAdm.getText().trim();

            if (id.isEmpty() || nom.isEmpty() || ape.isEmpty() || cargo.isEmpty()) {
                mostrarError("Complete todos los campos obligatorios.");
                return;
            }
            if (controlador.buscarEmpleado(id) != null) {
                mostrarError("Ya existe un empleado con esa identificación.");
                return;
            }

            LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE);
            controlador.registrarAdministrativo(id, nom, ape, fechaDate, cargo, sal, area, esc);

            mostrarExito("Administrativo registrado exitosamente.");
            limpiarFormAdm();
            actualizarTablaEmpleados();

        } catch (Exception ex) {
            mostrarError("Error al registrar: " + ex.getMessage());
        }
    }

    private void agregarAsignatura() {
        try {
            String idProf = obtenerIdDesdeComboProfesores(cmbProfAsig);
            if (idProf == null) { mostrarError("Seleccione un profesor."); return; }

            String cod  = txtCodAsig.getText().trim();
            String nom  = txtNomAsig.getText().trim();
            int cred    = Integer.parseInt(txtCredAsig.getText().trim());
            int horas   = Integer.parseInt(txtHorasAsig.getText().trim());

            if (cod.isEmpty() || nom.isEmpty()) { mostrarError("Complete todos los campos."); return; }

            boolean ok = controlador.agregarAsignaturaAProfesor(idProf, cod, nom, cred, horas);
            if (ok) {
                mostrarExito("Asignatura agregada correctamente.");
                txtCodAsig.setText(""); txtNomAsig.setText("");
                txtCredAsig.setText(""); txtHorasAsig.setText("");
                actualizarListaAsignaturas();
            }
        } catch (NumberFormatException ex) {
            mostrarError("Créditos y horas deben ser números enteros.");
        }
    }

    private void agregarProyecto() {
        try {
            String idProf = obtenerIdDesdeComboProfesores(cmbProfProy);
            if (idProf == null) { mostrarError("Seleccione un profesor."); return; }

            String cod  = txtCodProy.getText().trim();
            String nom  = txtNomProy.getText().trim();
            String tipo = (String) cmbTipoProy.getSelectedItem();

            if (cod.isEmpty() || nom.isEmpty()) { mostrarError("Complete todos los campos."); return; }

            Proyecto.TipoProyecto tp = "INVESTIGACION".equals(tipo)
                    ? Proyecto.TipoProyecto.INVESTIGACION
                    : Proyecto.TipoProyecto.EXTENSION;

            boolean ok = controlador.agregarProyectoAProfesor(idProf, cod, nom, tp);
            if (ok) {
                mostrarExito("Proyecto agregado correctamente.");
                txtCodProy.setText(""); txtNomProy.setText("");
                actualizarListaProyectos();
                actualizarComboProyectosProducto();
            }
        } catch (Exception ex) {
            mostrarError("Error: " + ex.getMessage());
        }
    }

    private void agregarProducto() {
        try {
            String idProf = obtenerIdDesdeComboProfesores(cmbProfProy);
            if (idProf == null) { mostrarError("Seleccione un profesor."); return; }

            Object selProy = cmbProyProducto.getSelectedItem();
            if (selProy == null) { mostrarError("Seleccione un proyecto."); return; }
            String codProy = selProy.toString().split(" - ")[0];

            String titulo = txtTituloProducto.getText().trim();
            if (titulo.isEmpty()) { mostrarError("Ingrese el título del producto."); return; }

            String tipoStr = (String) cmbTipoProducto.getSelectedItem();
            Producto.TipoProducto tipo = Producto.TipoProducto.valueOf(tipoStr);
            int puntos = Integer.parseInt(txtPuntosProducto.getText().trim());

            boolean ok = controlador.agregarProductoAProyecto(idProf, codProy, titulo, tipo, puntos);
            if (ok) {
                mostrarExito("Producto académico agregado correctamente.");
                txtTituloProducto.setText(""); txtPuntosProducto.setText("");
                actualizarListaProyectos();
            }
        } catch (NumberFormatException ex) {
            mostrarError("Los puntos deben ser un número entero.");
        } catch (Exception ex) {
            mostrarError("Error: " + ex.getMessage());
        }
    }

    private void generarNomina() {
        modeloTablaNomina.setRowCount(0);
        List<Empleado> empleados = controlador.getEmpleados();

        for (Empleado e : empleados) {
            double salBase = e.getSalarioBase();
            double total   = e.calcularSalarioTotal();
            double extra   = total - salBase;

            modeloTablaNomina.addRow(new Object[]{
                e.getIdentificacion(),
                e.getNombreCompleto(),
                e.getTipoEmpleado(),
                formatoPesos.format(salBase),
                formatoPesos.format(extra),
                formatoPesos.format(total)
            });
        }

        double totalNomina = controlador.calcularTotalNomina();
        lblTotalNomina.setText("  Total Nómina: " + formatoPesos.format(totalNomina));
    }

    // ══════════════════════════════════════════════════════
    //  ACTUALIZAR UI
    // ══════════════════════════════════════════════════════

    private void actualizarTablaEmpleados() {
        modeloTablaEmpleados.setRowCount(0);
        for (Empleado e : controlador.getEmpleados()) {
            modeloTablaEmpleados.addRow(new Object[]{
                e.getIdentificacion(),
                e.getNombreCompleto(),
                e.getTipoEmpleado(),
                e.getCargo(),
                e.getFechaIngreso().toString(),
                e.calcularAntiguedad() + " año(s)",
                formatoPesos.format(e.getSalarioBase())
            });
        }
    }

    private void actualizarCombosProfesores() {
        cmbProfAsig.removeAllItems();
        cmbProfProy.removeAllItems();
        for (Profesor p : controlador.getProfesores()) {
            String item = p.getIdentificacion() + " - " + p.getNombreCompleto();
            cmbProfAsig.addItem(item);
            cmbProfProy.addItem(item);
        }
        actualizarListaAsignaturas();
        actualizarListaProyectos();
        actualizarComboProyectosProducto();
    }

    private void actualizarListaAsignaturas() {
        modeloListaAsig.clear();
        String idProf = obtenerIdDesdeComboProfesores(cmbProfAsig);
        if (idProf == null) return;
        Empleado e = controlador.buscarEmpleado(idProf);
        if (e instanceof Profesor) {
            for (Asignatura a : ((Profesor) e).getAsignaturas()) {
                modeloListaAsig.addElement(a.toString());
            }
        }
    }

    private void actualizarListaProyectos() {
        modeloListaProyectos.clear();
        String idProf = obtenerIdDesdeComboProfesores(cmbProfProy);
        if (idProf == null) return;
        Empleado e = controlador.buscarEmpleado(idProf);
        if (e instanceof Profesor) {
            for (Proyecto p : ((Profesor) e).getProyectos()) {
                modeloListaProyectos.addElement("🔬 " + p.toString()
                        + " | Puntos: " + p.calcularPuntosTotales());
                for (Producto prod : p.getProductos()) {
                    modeloListaProyectos.addElement("     ↳ " + prod.toString());
                }
            }
        }
    }

    private void actualizarComboProyectosProducto() {
        cmbProyProducto.removeAllItems();
        String idProf = obtenerIdDesdeComboProfesores(cmbProfProy);
        if (idProf == null) return;
        Empleado e = controlador.buscarEmpleado(idProf);
        if (e instanceof Profesor) {
            for (Proyecto p : ((Profesor) e).getProyectos()) {
                cmbProyProducto.addItem(p.getCodigo() + " - " + p.getNombre());
            }
        }
    }

    private String obtenerIdDesdeComboProfesores(JComboBox<String> cmb) {
        Object sel = cmb.getSelectedItem();
        if (sel == null) return null;
        return sel.toString().split(" - ")[0];
    }

    // ══════════════════════════════════════════════════════
    //  DETALLE EMPLEADO
    // ══════════════════════════════════════════════════════

    private void mostrarDetalleEmpleado(Empleado emp) {
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════\n");
        sb.append("  DETALLE DEL EMPLEADO\n");
        sb.append("══════════════════════════════════════\n");
        sb.append("ID:             ").append(emp.getIdentificacion()).append("\n");
        sb.append("Nombre:         ").append(emp.getNombreCompleto()).append("\n");
        sb.append("Tipo:           ").append(emp.getTipoEmpleado()).append("\n");
        sb.append("Cargo:          ").append(emp.getCargo()).append("\n");
        sb.append("Fecha Ingreso:  ").append(emp.getFechaIngreso()).append("\n");
        sb.append("Antigüedad:     ").append(emp.calcularAntiguedad()).append(" año(s)\n");
        sb.append("Salario Base:   ").append(formatoPesos.format(emp.getSalarioBase())).append("\n");

        if (emp instanceof Profesor) {
            Profesor prof = (Profesor) emp;
            sb.append("Perfil Académ.: ").append(prof.getPerfilAcademico()).append("\n");
            sb.append("Asignaturas:    ").append(prof.getAsignaturas().size()).append("\n");
            for (Asignatura a : prof.getAsignaturas()) {
                sb.append("   * ").append(a.toString()).append("\n");
            }
            sb.append("Proyectos:      ").append(prof.getProyectos().size()).append("\n");
            for (Proyecto p : prof.getProyectos()) {
                sb.append("   [P] ").append(p.toString())
                  .append(" | ").append(p.calcularPuntosTotales()).append(" pts\n");
                for (Producto pd : p.getProductos()) {
                    sb.append("       -> ").append(pd.toString()).append("\n");
                }
            }
            sb.append("Puntos Acum.:   ").append(prof.calcularPuntosAcumulados()).append("\n");
            sb.append("Bonificacion:   ").append(formatoPesos.format(prof.calcularBonificacion())).append("\n");
        } else if (emp instanceof Administrativo) {
            Administrativo adm = (Administrativo) emp;
            sb.append("Area:           ").append(adm.getArea()).append("\n");
            sb.append("Escalafon:      ").append(adm.getNivelEscalafon()).append("\n");
        }

        sb.append("──────────────────────────────────────\n");
        sb.append("TOTAL A PAGAR:  ").append(formatoPesos.format(emp.calcularSalarioTotal())).append("\n");
        sb.append("══════════════════════════════════════\n");

        JTextArea ta = new JTextArea(sb.toString());
        ta.setFont(new Font("Monospaced", Font.PLAIN, 13));
        ta.setEditable(false);
        ta.setBackground(new Color(250, 250, 250));
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(480, 400));
        JOptionPane.showMessageDialog(this, sp,
                "Detalle: " + emp.getNombreCompleto(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    // ══════════════════════════════════════════════════════
    //  UTILITARIOS UI
    // ══════════════════════════════════════════════════════

    private JTable crearTablaEstilizada(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(new Color(224, 224, 224));
        tabla.setSelectionBackground(new Color(187, 222, 251));
        tabla.setSelectionForeground(Color.BLACK);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(AZUL_OSCURO);
        header.setForeground(TEXTO_HEADER);
        header.setPreferredSize(new Dimension(header.getWidth(), 34));
        return tabla;
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(fondo);
        btn.setForeground(BLANCO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    private JPanel crearSubtitulo(String texto) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(AZUL_CLARO);
        p.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, AZUL_MEDIO));
        JLabel l = new JLabel("  " + texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l.setForeground(AZUL_OSCURO);
        p.add(l);
        return p;
    }

    private Border crearBordeSombra() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 189, 189), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void agregarCampoForm(JPanel form, GridBagConstraints g,
                                   String labelTxt, JComponent campo, int fila) {
        JLabel lbl = new JLabel(labelTxt);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g.gridx = 0; g.gridy = fila; g.weightx = 0;
        form.add(lbl, g);
        g.gridx = 1; g.weightx = 1.0;
        if (campo instanceof JTextField) ((JTextField) campo).setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(campo, g);
    }

    private void limpiarFormProfesor() {
        txtIdProf.setText(""); txtNombresProf.setText(""); txtApellidosProf.setText("");
        txtFechaProf.setText("2020-01-15"); txtSalarioProf.setText("");
        txtPerfilProf.setText(""); txtExtraProf.setText(""); chkDedicacion.setSelected(false);
    }

    private void limpiarFormAdm() {
        txtIdAdm.setText(""); txtNombresAdm.setText(""); txtApellidosAdm.setText("");
        txtFechaAdm.setText("2020-01-15"); txtCargoAdm.setText("");
        txtSalarioAdm.setText(""); txtAreaAdm.setText(""); txtEscalafonAdm.setText("");
    }

    private void mostrarExito(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
