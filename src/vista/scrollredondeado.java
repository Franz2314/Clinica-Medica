package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class scrollredondeado extends JScrollPane {
    private final int arc;
    private final Color fillColor;
    private final Color borderColor;

    public scrollredondeado(JComponent view, int arc, Color fillColor, Color borderColor) {
        super(view);
        this.arc = arc;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
        setOpaque(false);
        getViewport().setOpaque(false);
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (fillColor != null) {
            g2.setColor(fillColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        }
        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
