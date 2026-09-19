package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import utilidades.ProjectPaths;

public class tarjetaresumen extends JPanel {
    private final JLabel iconLabel;
    private final JLabel titleLabel;
    private final JLabel indicatorLabel;
    private final JLabel valueLabel;
    private final Color accentColor;
    private final temaaplicacion tema;

    public tarjetaresumen(String iconText, String title, JLabel valueLabel, Color accentColor, temaaplicacion tema, String imageFile) {
        this.accentColor = accentColor;
        this.tema = tema;
        this.valueLabel = valueLabel;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(14, 22, 18, 22));
        setPreferredSize(new Dimension(248, 188));

        ImageIcon icono = cargarIcono(imageFile);
        iconLabel = icono != null
                ? new JLabel(icono, SwingConstants.CENTER)
                : new JLabel(iconText, SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(224, 176));
        if (icono == null) {
            iconLabel.setFont(fuentesinterfaz.strong(24));
            iconLabel.setForeground(accentColor);
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel = new JLabel("<html><div style='text-align:center;'>" + iconText + " - " + title + "</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(fuentesinterfaz.heading(20));
        titleLabel.setForeground(tema.tarjetaTexto);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        indicatorLabel = new JLabel("Estado del modulo", SwingConstants.CENTER);
        indicatorLabel.setFont(fuentesinterfaz.body(15));
        indicatorLabel.setForeground(tema.tarjetaTextoSuave);
        indicatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setFont(fuentesinterfaz.title(54));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(2));
        add(iconLabel);
        add(Box.createVerticalStrut(2));
        add(titleLabel);
        add(Box.createVerticalStrut(2));
        add(indicatorLabel);
        add(Box.createVerticalGlue());
        add(valueLabel);
        add(Box.createVerticalStrut(6));
    }

    private ImageIcon cargarIcono(String imageFile) {
        if (imageFile == null || imageFile.isBlank()) {
            return null;
        }
        java.io.File file = ProjectPaths.resolveProject("recursos", imageFile).toFile();
        if (!file.exists()) {
            return null;
        }
        ImageIcon original = new ImageIcon(file.getAbsolutePath());
        int originalW = Math.max(1, original.getIconWidth());
        int originalH = Math.max(1, original.getIconHeight());
        int maxW = 172;
        int maxH = 172;
        double scale = Math.min(maxW / (double) originalW, maxH / (double) originalH);
        int drawW = Math.max(1, (int) Math.round(originalW * scale));
        int drawH = Math.max(1, (int) Math.round(originalH * scale));
        Image escalada = original.getImage().getScaledInstance(drawW, drawH, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int horizontalInset = 18;
        int cardWidth = getWidth() - (horizontalInset * 2);

        g2.setColor(tema.tarjetaSombra);
        g2.fillRoundRect(horizontalInset + 8, 14, cardWidth - 16, getHeight() - 22, 32, 32);

        g2.setColor(tema.tarjetaFondo);
        g2.fillRoundRect(horizontalInset, 0, cardWidth, getHeight() - 14, 32, 32);

        g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 60));
        g2.drawRoundRect(horizontalInset, 0, cardWidth - 1, getHeight() - 15, 32, 32);
        g2.dispose();

        super.paintComponent(g);
    }
}
