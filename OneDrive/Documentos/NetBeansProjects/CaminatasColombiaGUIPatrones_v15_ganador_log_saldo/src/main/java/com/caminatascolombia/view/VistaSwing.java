
package com.caminatascolombia.view;

import com.caminatascolombia.controller.BlogController;
import com.caminatascolombia.controller.CaminataController;
import com.caminatascolombia.controller.InscripcionController;
import com.caminatascolombia.controller.UsuarioController;
import com.caminatascolombia.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Vista principal con Swing.
 * Implementa:
 * - Pestaña de Caminatas (listar, crear aleatorias, reportes, busquedas)
 * - Pestaña de Inscripciones (decorator de seguros, estado de inscripcion)
 * - Pestaña de Blog (resenas solo para caminatas finalizadas)
 * Ademas muestra la fecha actual del sistema y permite adelantar dias.
 */
public class VistaSwing extends JFrame implements Vista {

    private JTextArea txtLog;
    private JLabel lblUsuarioInfo;
    private JButton btnRegistrarUsuario;
    private JButton btnCerrarSesion;
    private JLabel lblFechaActual;

    private UsuarioController usuarioController;
    private CaminataController caminataController;
    private InscripcionController inscripcionController;
    private BlogController blogController;

    private Usuario usuarioActual;

    // Componentes de inscripciones
    private JComboBox<Caminata> cmbCaminatas;
    private JTextArea txtDetalleCaminata;
    private JCheckBox chkSeguroAccidentes;
    private JCheckBox chkSeguroEquipo;
    private JLabel lblCostoBase;
    private JLabel lblCostoSeguros;
    private JLabel lblCostoTotal;

    // Componentes del Blog
    private JTextArea txtBlogListado;
    private JComboBox<Caminata> cmbCaminatasBlog;
    private JSpinner spnCalificacionBlog;
    private JTextArea txtResenaBlog;
    private JLabel lblMensajeBlog;

    public VistaSwing() {
        setIconImage(new ImageIcon("img/logo_caminatas.png").getImage());
        setTitle("Caminatas Colombia - Gestion (Patrones)");
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(
                        VistaSwing.this,
                        "Desea salir de la aplicacion?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        initComponents();
    }

    public void setControllers(UsuarioController usuarioController,
                               CaminataController caminataController,
                               InscripcionController inscripcionController,
                               BlogController blogController) {
        this.usuarioController = usuarioController;
        this.caminataController = caminataController;
        this.inscripcionController = inscripcionController;
        this.blogController = blogController;
    }

    public void setUsuarioActual(Usuario usuario) {
        this.usuarioActual = usuario;
        actualizarUsuarioInfo();
        recargarBlogCaminatasRealizadas();
    }

    private void actualizarUsuarioInfo() {
        if (usuarioActual == null) {
            lblUsuarioInfo.setText("Sin sesion iniciada");
        } else {
            lblUsuarioInfo.setText(
                    "Usuario: " + usuarioActual.getNombre() +
                            " (" + usuarioActual.getTipo() + ") - Saldo: " + usuarioActual.getSaldo());
        }
    }

    private void actualizarFechaActual() {
        java.util.Date fecha = SistemaCaminatas.getInstancia().getFechaActual();
        lblFechaActual.setText("Fecha actual del sistema: " + fecha);
    }

    

private void initComponents() {
        // Panel superior: info usuario + botones de usuario + fecha actual y adelanto de dias
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);

        // Logo a la izquierda, escalado para no ocupar demasiado espacio
        ImageIcon iconLogo = new ImageIcon("img/logo_caminatas.png");
        Image imgLogo = iconLogo.getImage();
        Image imgEscalada = imgLogo.getScaledInstance(140, -1, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imgEscalada));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 15));
        panelTop.add(lblLogo, BorderLayout.WEST);

        lblUsuarioInfo = new JLabel("Sin sesion iniciada");
        lblUsuarioInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Boton para registrar un nuevo usuario
        btnRegistrarUsuario = new JButton("Registrar nuevo usuario");
        btnRegistrarUsuario.addActionListener(e -> {
            if (usuarioController == null) {
                mostrarMensaje("Controlador de usuarios no disponible.");
                return;
            }
            RegistroUsuarioDialog dialog = new RegistroUsuarioDialog(this, usuarioController);
            dialog.setVisible(true);
            Usuario u = dialog.getUsuarioCreado();
            if (u != null) {
                this.usuarioActual = u;
                actualizarUsuarioInfo();
                recargarBlogCaminatasRealizadas();
            }
        });

        // Boton para cerrar sesion y volver al login
        btnCerrarSesion = new JButton("Cerrar sesion");
        btnCerrarSesion.addActionListener(e -> {
            if (usuarioController == null) {
                mostrarMensaje("Controlador de usuarios no disponible.");
                return;
            }
            int op = JOptionPane.showConfirmDialog(
                    this,
                    "Desea cerrar la sesion actual?",
                    "Cerrar sesion",
                    JOptionPane.YES_NO_OPTION
            );
            if (op == JOptionPane.YES_OPTION) {
                // "Volver al menu inicial": ocultamos esta ventana y mostramos el login
                this.usuarioActual = null;
                actualizarUsuarioInfo();
                recargarBlogCaminatasRealizadas();

                setVisible(false);
                LoginDialog login = new LoginDialog(this, usuarioController);
                login.setVisible(true);
                Usuario nuevo = login.getUsuarioAutenticado();
                if (nuevo == null) {
                    System.exit(0);
                }
                setUsuarioActual(nuevo);
                setVisible(true);
                mostrarMensaje("Sesion iniciada como " + nuevo.getNombre());
            }
        });

        JPanel panelBotonesUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonesUsuario.setOpaque(false);
        panelBotonesUsuario.add(btnRegistrarUsuario);
        panelBotonesUsuario.add(btnCerrarSesion);

        JPanel panelUsuario = new JPanel(new BorderLayout());
        panelUsuario.setOpaque(false);
        panelUsuario.add(lblUsuarioInfo, BorderLayout.CENTER);
        panelUsuario.add(panelBotonesUsuario, BorderLayout.EAST);

        // Panel de fecha actual del sistema
        lblFechaActual = new JLabel();
        actualizarFechaActual();
        JTextField txtDias = new JTextField("1", 3);
        JButton btnAdelantar = new JButton("Adelantar dias");
        btnAdelantar.addActionListener(e -> {
            try {
                int dias = Integer.parseInt(txtDias.getText());
                if (dias <= 0) {
                    mostrarMensaje("Ingrese un numero de dias mayor que cero.");
                    return;
                }
                SistemaCaminatas.getInstancia().adelantarDias(dias);
                actualizarFechaActual();
                recargarCaminatasEnCombo();
                recargarBlogCaminatasRealizadas();
                mostrarGanadoresCompetencias();
                actualizarUsuarioInfo();
                mostrarMensaje("Se adelantaron " + dias + " dias en el sistema.");
            } catch (NumberFormatException ex) {
                mostrarMensaje("Numero de dias invalido.");
            }
        });


        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFecha.setOpaque(false);
        panelFecha.add(lblFechaActual);
        panelFecha.add(new JLabel("  Dias a adelantar: "));
        panelFecha.add(txtDias);
        panelFecha.add(btnAdelantar);

        JPanel panelTopInner = new JPanel(new BorderLayout());
        panelTopInner.setOpaque(false);
        panelTopInner.add(panelUsuario, BorderLayout.NORTH);
        panelTopInner.add(panelFecha, BorderLayout.SOUTH);

        panelTop.add(panelTopInner, BorderLayout.CENTER);

        // Area de log general
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setPreferredSize(new Dimension(1024, 180));

        // Pestañas principales
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Caminatas", crearPanelCaminatas());
        tabs.addTab("Inscripciones", crearPanelInscripciones());
        tabs.addTab("Blog", crearPanelBlog());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelTop, BorderLayout.NORTH);
        getContentPane().add(tabs, BorderLayout.CENTER);
        getContentPane().add(scrollLog, BorderLayout.SOUTH);
    }



    private JPanel crearPanelCaminatas() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botones principales
        JButton btnListar = new JButton("Listar caminatas programadas");
        JButton btnCrearAleatoria = new JButton("Crear caminata");
        JButton btnReporte = new JButton("Generar reporte");

        JLabel lblDificultad = new JLabel("Filtrar por dificultad:");
        JComboBox<Dificultad> cmbDificultad = new JComboBox<>(Dificultad.values());
        JButton btnFiltrar = new JButton("Buscar");

        JLabel lblTexto = new JLabel("Buscar por texto (lugar/descripcion):");
        JTextField txtBusqueda = new JTextField(15);
        JButton btnBuscarTexto = new JButton("Buscar texto");

        // Seccion de creacion manual de caminatas
        JPanel panelManual = new JPanel(new GridBagLayout());
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(3,3,3,3);
        g2.fill = GridBagConstraints.HORIZONTAL;
        g2.weightx = 1.0;

        JLabel lblTituloManual = new JLabel("Crear caminata manualmente");
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(15);
        JLabel lblLugar = new JLabel("Lugar:");
        JTextField txtLugar = new JTextField(15);
        JLabel lblDias = new JLabel("Dias desde hoy:");
        JSpinner spnDias = new JSpinner(new SpinnerNumberModel(7, 0, 365, 1));
        JLabel lblTipo = new JLabel("Tipo:");
        JComboBox<TipoCaminata> cmbTipo = new JComboBox<>(TipoCaminata.values());
        JLabel lblDifManual = new JLabel("Dificultad:");
        JComboBox<Dificultad> cmbDifManual = new JComboBox<>(Dificultad.values());
        JLabel lblCosto = new JLabel("Costo base ($):");
        JTextField txtCosto = new JTextField("100000", 10);

        JCheckBox chkPatrocinada = new JCheckBox("Caminata patrocinada con premio");
        JLabel lblPatrocinador = new JLabel("Patrocinador:");
        JTextField txtPatrocinador = new JTextField(12);
        JLabel lblPremio = new JLabel("Premio ($):");
        JTextField txtPremio = new JTextField("500000", 10);

        JButton btnCrearManual = new JButton("Crear caminata manual");

        // Estado inicial de campos de patrocinio
        txtPatrocinador.setEnabled(false);
        txtPremio.setEnabled(false);

        chkPatrocinada.addActionListener(e -> {
            boolean sel = chkPatrocinada.isSelected();
            txtPatrocinador.setEnabled(sel);
            txtPremio.setEnabled(sel);
        });

        cmbTipo.addActionListener(e -> {
            TipoCaminata t = (TipoCaminata) cmbTipo.getSelectedItem();
            if (t != TipoCaminata.DEPORTIVA_COMPETENCIA) {
                chkPatrocinada.setSelected(false);
                txtPatrocinador.setEnabled(false);
                txtPremio.setEnabled(false);
            }
        });

        int fila = 0;
        g2.gridx = 0; g2.gridy = fila++; g2.gridwidth = 4;
        panelManual.add(lblTituloManual, g2);

        g2.gridwidth = 1;
        g2.gridx = 0; g2.gridy = fila;
        panelManual.add(lblNombre, g2);
        g2.gridx = 1; g2.gridwidth = 3;
        panelManual.add(txtNombre, g2);
        fila++;

        g2.gridwidth = 1;
        g2.gridx = 0; g2.gridy = fila;
        panelManual.add(lblLugar, g2);
        g2.gridx = 1; g2.gridwidth = 3;
        panelManual.add(txtLugar, g2);
        fila++;

        g2.gridwidth = 1;
        g2.gridx = 0; g2.gridy = fila;
        panelManual.add(lblDias, g2);
        g2.gridx = 1;
        panelManual.add(spnDias, g2);
        fila++;

        g2.gridx = 0; g2.gridy = fila;
        panelManual.add(lblTipo, g2);
        g2.gridx = 1;
        panelManual.add(cmbTipo, g2);
        g2.gridx = 2;
        panelManual.add(lblDifManual, g2);
        g2.gridx = 3;
        panelManual.add(cmbDifManual, g2);
        fila++;

        g2.gridx = 0; g2.gridy = fila;
        panelManual.add(lblCosto, g2);
        g2.gridx = 1;
        panelManual.add(txtCosto, g2);
        fila++;

        g2.gridx = 0; g2.gridy = fila; g2.gridwidth = 4;
        panelManual.add(chkPatrocinada, g2);
        fila++;

        g2.gridwidth = 1;
        g2.gridx = 0; g2.gridy = fila;
        panelManual.add(lblPatrocinador, g2);
        g2.gridx = 1;
        panelManual.add(txtPatrocinador, g2);
        g2.gridx = 2;
        panelManual.add(lblPremio, g2);
        g2.gridx = 3;
        panelManual.add(txtPremio, g2);
        fila++;

        g2.gridx = 0; g2.gridy = fila; g2.gridwidth = 4;
        panelManual.add(btnCrearManual, g2);

        // Layout principal de la pestaña Caminatas
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        panel.add(btnListar, gbc);

        gbc.gridy = 1;
        panel.add(btnCrearAleatoria, gbc);

        gbc.gridy = 2;
        panel.add(btnReporte, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblDificultad, gbc);
        gbc.gridx = 1;
        panel.add(cmbDificultad, gbc);
        gbc.gridx = 2;
        panel.add(btnFiltrar, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblTexto, gbc);
        gbc.gridx = 1;
        panel.add(txtBusqueda, gbc);
        gbc.gridx = 2;
        panel.add(btnBuscarTexto, gbc);

        // Panel manual debajo de los filtros
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(panelManual, gbc);

        // Listeners
        btnListar.addActionListener(e -> {
            if (caminataController != null) {
                caminataController.listarTodas();
            }
        });

        btnCrearAleatoria.addActionListener(e -> {
            if (caminataController != null) {
                caminataController.crearCaminataAleatoria();
                recargarCaminatasEnCombo();
            }
        });

        btnReporte.addActionListener(e -> {
            if (caminataController != null) {
                caminataController.generarReporte();
            }
        });

        btnFiltrar.addActionListener(e -> {
            if (caminataController != null) {
                Dificultad dif = (Dificultad) cmbDificultad.getSelectedItem();
                caminataController.buscarPorDificultad(dif);
            }
        });

        btnBuscarTexto.addActionListener(e -> {
            if (caminataController != null) {
                caminataController.buscarPorTexto(txtBusqueda.getText());
            }
        });

        btnCrearManual.addActionListener(e -> {
            if (caminataController == null) {
                mostrarMensaje("Controlador de caminatas no disponible.");
                return;
            }
            String nombre = txtNombre.getText().trim();
            String lugar = txtLugar.getText().trim();
            if (nombre.isEmpty() || lugar.isEmpty()) {
                mostrarMensaje("Nombre y lugar son obligatorios.");
                return;
            }
            double costo;
            try {
                costo = Double.parseDouble(txtCosto.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarMensaje("El costo base debe ser un numero valido.");
                return;
            }
            int dias = (Integer) spnDias.getValue();
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(SistemaCaminatas.getInstancia().getFechaActual());
            cal.add(java.util.Calendar.DAY_OF_MONTH, dias);
            java.util.Date fecha = cal.getTime();

            TipoCaminata tipo = (TipoCaminata) cmbTipo.getSelectedItem();
            Dificultad dif = (Dificultad) cmbDifManual.getSelectedItem();

            boolean conPatro = chkPatrocinada.isSelected();
            String patrocinador = "";
            double premio = 0.0;
            if (conPatro) {
                patrocinador = txtPatrocinador.getText().trim();
                if (patrocinador.isEmpty()) {
                    mostrarMensaje("Debe indicar el nombre del patrocinador.");
                    return;
                }
                try {
                    premio = Double.parseDouble(txtPremio.getText().trim());
                } catch (NumberFormatException ex) {
                    mostrarMensaje("El premio debe ser un numero valido.");
                    return;
                }
            }

            Caminata nueva = caminataController.crearCaminataManual(
                    nombre, lugar, fecha, tipo, dif, costo, conPatro, patrocinador, premio);

            if (nueva != null) {
                recargarCaminatasEnCombo();
                txtNombre.setText("");
                txtLugar.setText("");
                txtCosto.setText("100000");
                spnDias.setValue(7);
                chkPatrocinada.setSelected(false);
                txtPatrocinador.setText("");
                txtPatrocinador.setEnabled(false);
                txtPremio.setText("500000");
                txtPremio.setEnabled(false);
            }
        });

        // Hacemos que toda la pestaña sea desplazable para que se vean todos los botones
        JScrollPane scroll = new JScrollPane(panel);
        // Barra vertical siempre visible para que el usuario sepa que puede desplazarse
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // Barra horizontal desactivada para que el contenido se ajuste en varias lineas
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(scroll, BorderLayout.CENTER);
        return contenedor;
    }

private JPanel crearPanelInscripciones() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInfo = new JLabel("<html>Seleccione la caminata en la que desea inscribirse. " +
                "Cada caminata tiene una descripcion y un espacio reservado para que usted agregue sus fotos en el futuro.</html>");
        JLabel lblCaminata = new JLabel("Caminata:");
        cmbCaminatas = new JComboBox<>();

        chkSeguroAccidentes = new JCheckBox("Seguro accidentes personales");
        chkSeguroEquipo = new JCheckBox("Seguro equipo y pertenencias");

        JButton btnInscribir = new JButton("Inscribir");
        JButton btnVerDetalle = new JButton("Ver detalle completo");

        txtDetalleCaminata = new JTextArea(6, 40);
        txtDetalleCaminata.setEditable(false);
        txtDetalleCaminata.setLineWrap(true);
        txtDetalleCaminata.setWrapStyleWord(true);
        txtDetalleCaminata.setBorder(BorderFactory.createTitledBorder("Descripcion y recomendaciones"));
        JScrollPane scrollDetalle = new JScrollPane(txtDetalleCaminata);
        scrollDetalle.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        lblCostoBase = new JLabel("Costo base: -");
        lblCostoSeguros = new JLabel("Costo seguros: -");
        lblCostoTotal = new JLabel("Total: -");

        recargarCaminatasEnCombo();

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        panel.add(lblInfo, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(lblCaminata, gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        panel.add(cmbCaminatas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(chkSeguroAccidentes, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        panel.add(chkSeguroEquipo, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        JPanel panelCostos = new JPanel(new GridLayout(1, 3, 5, 5));
        panelCostos.add(lblCostoBase);
        panelCostos.add(lblCostoSeguros);
        panelCostos.add(lblCostoTotal);
        panel.add(panelCostos, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnInscribir, gbc);
        gbc.gridx = 2; gbc.gridwidth = 2;
        panel.add(btnVerDetalle, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4;
        panel.add(scrollDetalle, gbc);

        cmbCaminatas.addActionListener(e -> {
            actualizarDetalleCaminata();
            actualizarCostos();
        });

        chkSeguroAccidentes.addActionListener(e -> actualizarCostos());
        chkSeguroEquipo.addActionListener(e -> actualizarCostos());

        btnInscribir.addActionListener(e -> {
            if (inscripcionController == null) {
                mostrarMensaje("Controlador de inscripciones no disponible.");
                return;
            }
            if (usuarioActual == null) {
                mostrarMensaje("No hay usuario autenticado. Inicie sesion primero.");
                return;
            }
            Caminata seleccionada = (Caminata) cmbCaminatas.getSelectedItem();
            if (seleccionada == null) {
                mostrarMensaje("No hay caminatas disponibles para inscribirse.");
                return;
            }
            boolean segAcc = chkSeguroAccidentes.isSelected();
            boolean segEq = chkSeguroEquipo.isSelected();
            inscripcionController.inscribirUsuarioEnCaminata(usuarioActual, seleccionada, segAcc, segEq);
            actualizarUsuarioInfo();
            actualizarCostos();
            recargarBlogCaminatasRealizadas();
        });

        btnVerDetalle.addActionListener(e -> {
            Caminata seleccionada = (Caminata) cmbCaminatas.getSelectedItem();
            if (seleccionada == null) {
                mostrarMensaje("No hay caminata seleccionada.");
                return;
            }
            DetalleCaminataDialog dialog = new DetalleCaminataDialog(this, seleccionada);
            dialog.setVisible(true);
        });

        return panel;
    }

    private JPanel crearPanelBlog() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JLabel lblInfo = new JLabel("<html><b>Blog de caminatas</b>. " +
                "Todos los usuarios pueden leer las resenas, " +
                "pero solo quienes hayan completado una caminata pueden publicar.</html>");

        txtBlogListado = new JTextArea(12, 40);
        txtBlogListado.setEditable(false);
        txtBlogListado.setLineWrap(true);
        txtBlogListado.setWrapStyleWord(true);
        JScrollPane scrollListado = new JScrollPane(txtBlogListado);
        scrollListado.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollListado.setBorder(BorderFactory.createTitledBorder("Resenas publicadas"));

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(4,4,4,4);
        g2.fill = GridBagConstraints.HORIZONTAL;
        g2.weightx = 1;

        JLabel lblCaminata = new JLabel("Caminata realizada:");
        cmbCaminatasBlog = new JComboBox<>();

        JLabel lblCalif = new JLabel("Calificacion (1-5):");
        spnCalificacionBlog = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));

        JLabel lblTexto = new JLabel("Texto de la resena:");
        txtResenaBlog = new JTextArea(5, 25);
        txtResenaBlog.setLineWrap(true);
        txtResenaBlog.setWrapStyleWord(true);
        JScrollPane scrollResena = new JScrollPane(txtResenaBlog);
        scrollResena.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton btnPublicar = new JButton("Publicar resena");
        lblMensajeBlog = new JLabel("");

        g2.gridx = 0; g2.gridy = 0; g2.gridwidth = 2;
        panelForm.add(lblCaminata, g2);
        g2.gridy = 1;
        panelForm.add(cmbCaminatasBlog, g2);

        g2.gridy = 2; g2.gridwidth = 1;
        panelForm.add(lblCalif, g2);
        g2.gridx = 1;
        panelForm.add(spnCalificacionBlog, g2);

        g2.gridx = 0; g2.gridy = 3; g2.gridwidth = 2;
        panelForm.add(lblTexto, g2);
        g2.gridy = 4;
        g2.fill = GridBagConstraints.BOTH;
        panelForm.add(scrollResena, g2);
        g2.gridy = 5; g2.fill = GridBagConstraints.HORIZONTAL;
        panelForm.add(btnPublicar, g2);
        g2.gridy = 6;
        panelForm.add(lblMensajeBlog, g2);

        btnPublicar.addActionListener(e -> {
            if (blogController == null) {
                mostrarMensaje("Controlador de blog no disponible.");
                return;
            }
            if (usuarioActual == null) {
                mostrarMensaje("Debe iniciar sesion para publicar una resena.");
                return;
            }
            Caminata seleccionada = (Caminata) cmbCaminatasBlog.getSelectedItem();
            int calif = (Integer) spnCalificacionBlog.getValue();
            String texto = txtResenaBlog.getText();
            blogController.publicarResena(usuarioActual, seleccionada, calif, texto);
            txtResenaBlog.setText("");
            recargarBlogListado();
            recargarBlogCaminatasRealizadas();
        });

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblInfo, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollListado, gbc);

        gbc.gridx = 1;
        panel.add(panelForm, gbc);

        recargarBlogListado();
        recargarBlogCaminatasRealizadas();

        return panel;
    }

    private void actualizarDetalleCaminata() {
        Caminata c = (Caminata) cmbCaminatas.getSelectedItem();
        if (c == null) {
            txtDetalleCaminata.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(c.getNombre()).append("\n");
        sb.append("Lugar: ").append(c.getLugar()).append("\n");
        sb.append("Fecha: ").append(c.getFecha()).append("\n");
        sb.append("Tipo: ").append(c.getTipo()).append(" | Dificultad: ").append(c.getDificultad()).append("\n\n");
        if (c.isConPatrocinador()) {
            sb.append("Competencia con patrocinador: ").append(c.getNombrePatrocinador()).append("\n");
            sb.append("Premio en dinero: $").append(c.getPremioDinero()).append("\n");
            if (c.getGanadorCompetencia() != null) {
                sb.append("Ganador: ").append(c.getGanadorCompetencia().getNombre())
                  .append(" (ID ").append(c.getGanadorCompetencia().getId()).append(")\n");
            }
            sb.append("\n");
        }
        sb.append("Descripcion: ").append(c.getDescripcion()).append("\n\n");
        sb.append("Recomendaciones: ").append(c.getRecomendaciones()).append("\n\n");
        sb.append("Foto principal (placeholder): ").append(c.getFotoPrincipal()).append("\n");
        sb.append("(Aqui podras asociar tus propias fotos en futuras versiones de la app)");
        txtDetalleCaminata.setText(sb.toString());
    }

    private void actualizarCostos() {
        Caminata c = (Caminata) cmbCaminatas.getSelectedItem();
        if (c == null) {
            lblCostoBase.setText("Costo base: -");
            lblCostoSeguros.setText("Costo seguros: -");
            lblCostoTotal.setText("Total: -");
            return;
        }
        double base = c.getCostoBase();
        ISeguro seguro = new SeguroBasico();
        if (chkSeguroAccidentes.isSelected()) {
            seguro = new SeguroAccidentes(seguro);
        }
        if (chkSeguroEquipo.isSelected()) {
            seguro = new SeguroEquipo(seguro);
        }
        double extra = seguro.getCostoExtra(base);
        double total = base + extra;

        lblCostoBase.setText("Costo base: " + base);
        lblCostoSeguros.setText("Costo seguros: " + extra);
        lblCostoTotal.setText("Total: " + total);
    }

    
    /**
     * Muestra en el log los ganadores de caminatas patrocinadas
     * que ya tienen ganador asignado.
     */
    private void mostrarGanadoresCompetencias() {
        SistemaCaminatas sis = SistemaCaminatas.getInstancia();
        for (Caminata c : sis.getCaminatas()) {
            if (c.isConPatrocinador() && c.getGanadorCompetencia() != null) {
                Usuario g = c.getGanadorCompetencia();
                mostrarMensaje("Ganador caminata patrocinada '" + c.getNombre() + "': "
                        + g.getNombre() + " (ID " + g.getId() + "), nuevo saldo = " + g.getSaldo());
            }
        }
    }

private void recargarCaminatasEnCombo() {
        if (cmbCaminatas == null) return;
        cmbCaminatas.removeAllItems();
        List<Caminata> lista = SistemaCaminatas.getInstancia().getCaminatasProgramables();
        for (Caminata c : lista) {
            cmbCaminatas.addItem(c);
        }
        actualizarDetalleCaminata();
        actualizarCostos();
    }

    private void recargarBlogListado() {
        if (txtBlogListado == null || blogController == null) return;
        StringBuilder sb = new StringBuilder();
        List<ResenaBlog> resenas = blogController.obtenerResenas();
        if (resenas.isEmpty()) {
            sb.append("Todavia no hay resenas publicadas.");
        } else {
            for (ResenaBlog r : resenas) {
                sb.append(r.toString()).append("\n\n");
            }
        }
        txtBlogListado.setText(sb.toString());
    }

    private void recargarBlogCaminatasRealizadas() {
        if (cmbCaminatasBlog == null) return;
        cmbCaminatasBlog.removeAllItems();

        if (usuarioActual == null || blogController == null) {
            cmbCaminatasBlog.setEnabled(false);
            if (lblMensajeBlog != null) {
                lblMensajeBlog.setText("Debe iniciar sesion para publicar resenas.");
            }
            return;
        }

        List<Caminata> realizadas = blogController.obtenerCaminatasRealizadas(usuarioActual);
        if (realizadas.isEmpty()) {
            cmbCaminatasBlog.setEnabled(false);
            if (lblMensajeBlog != null) {
                lblMensajeBlog.setText("Todavia no ha completado caminatas. Puede leer resenas, pero no publicar.");
            }
        } else {
            cmbCaminatasBlog.setEnabled(true);
            for (Caminata c : realizadas) {
                cmbCaminatasBlog.addItem(c);
            }
            if (lblMensajeBlog != null) {
                lblMensajeBlog.setText("Seleccione una caminata completada para publicar su resena.");
            }
        }
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        txtLog.append(mensaje + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    @Override
    public void mostrarCaminatas(List<Caminata> caminatas) {
        mostrarMensaje("Caminatas encontradas: " + caminatas.size());
        for (Caminata c : caminatas) {
            mostrarMensaje(" - " + c.getNombre()
                    + " | Lugar: " + c.getLugar()
                    + " | Fecha: " + c.getFecha()
                    + " | Tipo: " + c.getTipo()
                    + " | Dificultad: " + c.getDificultad()
                    + " | Costo base: " + c.getCostoBase());
        }
    }
}
