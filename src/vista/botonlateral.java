package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class botonlateral extends JButton {
    private static final Color MENU_FONDO = new Color(241, 247, 252);
    private static final Color MENU_FONDO_ACTIVO = new Color(220, 235, 248);
    private final Color accentColor;
    private final temaaplicacion tema;
    private boolean active;

    public botonlateral(String iconText, String label, Color accentColor, temaaplicacion tema) {
        super(iconText + "  " + label);
        this.accentColor = accentColor;
        this.tema = tema;
        setFont(fuentesinterfaz.heading(14));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setHorizontalAlignment(LEFT);
        setPreferredSize(new Dimension(228, 42));
        setMaximumSize(new Dimension(228, 42));
        setRolloverEnabled(false);
        aplicarEstilo();
    }

    public void setActive(boolean active) {
        this.active = active;
        aplicarEstilo();
    }

    public Color getAccentColor() {
        return accentColor;
    }

    private void aplicarEstilo() {
        if (active) {
            setBackground(MENU_FONDO_ACTIVO);
            setForeground(accentColor.darker());
            setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 14));
        } else {
            setBackground(MENU_FONDO);
            setForeground(tema.tablaTexto);
            setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 14));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
        if (active) {
            g2.setColor(accentColor);
            g2.fillRoundRect(0, 0, 6, getHeight(), 24, 24);
        } else {
            g2.setColor(new Color(214, 226, 236));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
