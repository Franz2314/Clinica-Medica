package vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import utilidades.ProjectPaths;

public final class ventanalogin {
    private static final String USUARIO = "admin";
    private static final String CLAVE = "admin";
    private static final char ECHO_CHAR = '\u2022';

    private ventanalogin() {
    }

    public static boolean mostrar(Component parent) {
        Window owner = parent == null ? null : SwingUtilities.getWindowAncestor(parent);
        LoginDialog dialog = new LoginDialog(owner);
        dialog.setVisible(true);
        return dialog.isAutenticado();
    }

    private static final class LoginDialog extends JDialog {
        private final JTextField txtUsuario;
        private final JPasswordField txtClave;
        private boolean autenticado;

        LoginDialog(Window owner) {
            super(owner instanceof Frame ? (Frame) owner : null, "Login CORE-SALUD", ModalityType.APPLICATION_MODAL);
            if (owner instanceof Dialog dialogOwner) {
                setModalityType(ModalityType.DOCUMENT_MODAL);
                setLocationRelativeTo(dialogOwner);
            }

            temaaplicacion tema = temaaplicacion.actual();
            panelfondo fondo = new panelfondo(
                    ProjectPaths.resolveProject("recursos", "FONDO.jpg"),
                    true,
                    new Color(225, 234, 243),
                    new Color(248, 251, 255, 56),
                    true);
            fondo.setLayout(new GridBagLayout());
            fondo.setBorder(BorderFactory.createEmptyBorder(26, 24, 26, 24));

            RoundedCardPanel tarjeta = new RoundedCardPanel();
            tarjeta.setLayout(new MigLayout("fillx, insets 28 34 26 34, wrap 1, gapy 12", "[grow]", ""));
            tarjeta.setPreferredSize(new Dimension(610, 540));

            MedicalPulseLogo logo = new MedicalPulseLogo();
            logo.setPreferredSize(new Dimension(92, 92));

            JLabel titulo = new JLabel("Bienvenido a CORE-SALUD", SwingConstants.CENTER);
            titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            titulo.setFont(fuentesinterfaz.bannerTitle(25));
            titulo.setForeground(new Color(21, 50, 87));

            JLabel subtitulo = new JLabel("Ingrese sus credenciales para continuar", SwingConstants.CENTER);
            subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
            subtitulo.setFont(fuentesinterfaz.bannerSubtitle(16));
            subtitulo.setForeground(new Color(92, 108, 126));

            JPanel cabecera = new JPanel();
            cabecera.setOpaque(false);
            cabecera.setLayout(new BoxLayout(cabecera, BoxLayout.Y_AXIS));
            cabecera.add(logo);
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            cabecera.add(Box.createVerticalStrut(10));
            cabecera.add(titulo);
            cabecera.add(Box.createVerticalStrut(8));
            cabecera.add(subtitulo);

            txtUsuario = new JTextField("admin");
            txtClave = new JPasswordField("admin");
            txtClave.setEchoChar(ECHO_CHAR);

            JPanel usuarioCampo = crearContenedorCampo(txtUsuario, "U", null);
            JToggleButton btnMostrar = crearBotonMostrar(txtClave);
            JPanel claveCampo = crearContenedorCampo(txtClave, "L", btnMostrar);

            JPanel formulario = new JPanel(new MigLayout("fillx, insets 8 0 4 0, wrap 1, gapy 14", "[grow]", ""));
            formulario.setOpaque(false);
            formulario.add(crearBloqueCampo("Usuario", usuarioCampo), "growx");
            formulario.add(crearBloqueCampo("Contrasena", claveCampo), "growx");

            PillButton btnCancelar = new PillButton("Cancelar", false);
            PillButton btnIngresar = new PillButton("Ingresar", true);
            btnIngresar.addActionListener(e -> intentarLogin());
            btnCancelar.addActionListener(e -> dispose());

            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
            acciones.setOpaque(false);
            acciones.add(btnCancelar);
            acciones.add(btnIngresar);

            tarjeta.add(cabecera, "growx");
            tarjeta.add(Box.createVerticalStrut(6), "growx");
            tarjeta.add(formulario, "growx");
            tarjeta.add(Box.createVerticalStrut(8), "growx");
            tarjeta.add(acciones, "growx");

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(8, 8, 8, 8);
            fondo.add(tarjeta, gbc);

            setContentPane(fondo);
            setResizable(false);
            pack();
            setMinimumSize(new Dimension(920, 650));
            setSize(new Dimension(920, 650));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(owner);
            getRootPane().setDefaultButton(btnIngresar);
            registrarEscape();
        }

        boolean isAutenticado() {
            return autenticado;
        }

        private void intentarLogin() {
            String usuario = txtUsuario.getText().trim();
            String clave = new String(txtClave.getPassword());
            if (USUARIO.equals(usuario) && CLAVE.equals(clave)) {
                autenticado = true;
                dispose();
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Credenciales incorrectas. Use usuario: admin y contrasena: admin",
                    "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE
            );
            txtClave.setText("admin");
            txtUsuario.selectAll();
            txtUsuario.requestFocusInWindow();
        }

        private JPanel crearBloqueCampo(String etiqueta, JComponent campo) {
            JPanel panel = new JPanel(new BorderLayout(0, 8));
            panel.setOpaque(false);

            JLabel label = new JLabel(etiqueta);
            label.setFont(fuentesinterfaz.heading(15));
            label.setForeground(new Color(34, 49, 66));

            panel.add(label, BorderLayout.NORTH);
            panel.add(campo, BorderLayout.CENTER);
            return panel;
        }

        private JPanel crearContenedorCampo(JComponent campo, String marca, JComponent extraDerecha) {
            JPanel contenedor = new JPanel(new BorderLayout(12, 0));
            contenedor.setOpaque(false);
            contenedor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            RoundedFieldPanel marco = new RoundedFieldPanel();
            marco.setLayout(new BorderLayout(8, 0));
            marco.setPreferredSize(new Dimension(100, 56));

            JLabel icono = new JLabel(marca, SwingConstants.CENTER);
            icono.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            icono.setForeground(new Color(104, 122, 141));
            icono.setPreferredSize(new Dimension(38, 38));

            campo.setFont(fuentesinterfaz.body(18));
            campo.setOpaque(false);
            campo.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
            if (campo instanceof JTextField textField) {
                textField.setForeground(new Color(34, 49, 66));
                textField.setCaretColor(new Color(32, 83, 142));
            }

            marco.add(icono, BorderLayout.WEST);
            marco.add(campo, BorderLayout.CENTER);
            if (extraDerecha != null) {
                marco.add(extraDerecha, BorderLayout.EAST);
            }

            contenedor.add(marco, BorderLayout.CENTER);
            return contenedor;
        }

        private JToggleButton crearBotonMostrar(JPasswordField campoClave) {
            JToggleButton boton = new JToggleButton("Ver");
            boton.setFocusPainted(false);
            boton.setFont(fuentesinterfaz.heading(12));
            boton.setForeground(new Color(101, 117, 136));
            boton.setOpaque(false);
            boton.setContentAreaFilled(false);
            boton.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 4));
            boton.addActionListener(e -> {
                campoClave.setEchoChar(boton.isSelected() ? (char) 0 : ECHO_CHAR);
                boton.setText(boton.isSelected() ? "Ocultar" : "Ver");
            });
            return boton;
        }

        private void registrarEscape() {
            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cerrar");
            getRootPane().getActionMap().put("cerrar", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
    }

    private static final class RoundedCardPanel extends JPanel {
        RoundedCardPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(17, 47, 78, 36));
            g2.fillRoundRect(12, 16, getWidth() - 24, getHeight() - 20, 34, 34);

            g2.setColor(new Color(255, 255, 255, 226));
            g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 16, 34, 34);

            g2.setColor(new Color(225, 233, 241, 160));
            g2.drawRoundRect(0, 0, getWidth() - 13, getHeight() - 17, 34, 34);

            dibujarMarca(g2, 28, 22, 90, new Color(34, 119, 181, 28));
            dibujarMarca(g2, getWidth() - 165, 20, 106, new Color(34, 119, 181, 24));
            dibujarMarca(g2, 26, getHeight() - 138, 108, new Color(34, 119, 181, 22));
            g2.dispose();

            super.paintComponent(g);
        }

        private void dibujarMarca(Graphics2D g2, int x, int y, int size, Color color) {
            Graphics2D copy = (Graphics2D) g2.create();
            copy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            copy.setColor(color);
            copy.setStroke(new BasicStroke(3.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int arm = size / 3;
            copy.drawRoundRect(x + arm, y, arm, size, 18, 18);
            copy.drawRoundRect(x, y + arm, size, arm, 18, 18);
            copy.drawLine(x + arm / 2, y + (size / 2), x + arm + 10, y + (size / 2));
            copy.drawLine(x + arm + 10, y + (size / 2), x + arm + 24, y + (size / 2) - 10);
            copy.drawLine(x + arm + 24, y + (size / 2) - 10, x + arm + 38, y + (size / 2) + 12);
            copy.drawLine(x + arm + 38, y + (size / 2) + 12, x + arm + arm + 18, y + (size / 2) - 6);
            copy.drawLine(x + arm + arm + 18, y + (size / 2) - 6, x + size - arm / 2, y + (size / 2) - 6);
            copy.dispose();
        }
    }

    private static final class RoundedFieldPanel extends JPanel {
        RoundedFieldPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            RoundRectangle2D shape = new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
            g2.setColor(new Color(255, 255, 255, 245));
            g2.fill(shape);
            g2.setPaint(new GradientPaint(0, 0, new Color(67, 113, 160, 160), getWidth(), 0, new Color(202, 216, 230, 160)));
            g2.draw(shape);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class PillButton extends JButton {
        private final boolean principal;

        PillButton(String texto, boolean principal) {
            super(texto);
            this.principal = principal;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
                setFont(fuentesinterfaz.heading(15));
            setForeground(principal ? Color.WHITE : new Color(46, 74, 108));
            setPreferredSize(new Dimension(principal ? 164 : 136, 52));
            setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color fill = principal
                    ? (getModel().isRollover() ? new Color(29, 79, 145) : new Color(26, 71, 130))
                    : (getModel().isRollover() ? new Color(241, 247, 252) : new Color(255, 255, 255, 220));
            Color border = principal ? new Color(21, 62, 116) : new Color(112, 146, 178);

            g2.setColor(new Color(18, 45, 77, principal ? 38 : 18));
            g2.fillRoundRect(4, 5, getWidth() - 8, getHeight() - 8, 28, 28);

            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 28, 28);
            g2.setColor(border);
            g2.drawRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 28, 28);
            g2.dispose();

            super.paintComponent(g);
        }
    }

    private static final class MedicalPulseLogo extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int w = getWidth();
            int h = getHeight();
            int arm = Math.min(w, h) / 3;
            int centerX = w / 2;
            int centerY = h / 2;

            g2.setColor(new Color(38, 90, 150));
            g2.drawRoundRect(centerX - arm / 2, 8, arm, h - 16, 16, 16);
            g2.setColor(new Color(81, 176, 170));
            g2.drawRoundRect(8, centerY - arm / 2, w - 16, arm, 16, 16);

            g2.setColor(new Color(52, 125, 177));
            int baseY = centerY;
            g2.drawLine(18, baseY, centerX - 18, baseY);
            g2.drawLine(centerX - 18, baseY, centerX - 6, baseY - 16);
            g2.drawLine(centerX - 6, baseY - 16, centerX + 6, baseY + 10);
            g2.drawLine(centerX + 6, baseY + 10, centerX + 18, baseY - 6);
            g2.drawLine(centerX + 18, baseY - 6, w - 18, baseY - 6);
            g2.dispose();
        }
    }
}
