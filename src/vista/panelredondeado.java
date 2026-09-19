package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class panelredondeado extends JPanel {
    private int arc;
    private Color fillColor;
    private Color borderColor;
    private int strokeWidth;

    public panelredondeado() {
        this(24);
    }

    public panelredondeado(int arc) {
        this.arc = arc;
        this.fillColor = Color.WHITE;
        this.borderColor = null;
        this.strokeWidth = 1;
        setOpaque(false);
    }

    public void setArc(int arc) {
        this.arc = arc;
        repaint();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        setBackground(fillColor);
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (fillColor != null) {
            g2.setColor(fillColor);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
        }
        if (borderColor != null && strokeWidth > 0) {
            g2.setColor(borderColor);
            for (int i = 0; i < strokeWidth; i++) {
                g2.drawRoundRect(i, i, getWidth() - 1 - (i * 2), getHeight() - 1 - (i * 2), arc, arc);
            }
        }
        g2.dispose();
        super.paintComponent(g);
    }
}
