package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class panelfondo extends JPanel {
    private Image imagen;
    private final boolean aplicarVelo;
    private final Color colorFondo;
    private final Color colorVelo;
    private final boolean cubrirArea;

    public panelfondo(Path rutaImagen, boolean aplicarVelo, Color colorFondo, Color colorVelo) {
        this(rutaImagen, aplicarVelo, colorFondo, colorVelo, true);
    }

    public panelfondo(Path rutaImagen, boolean aplicarVelo, Color colorFondo, Color colorVelo, boolean cubrirArea) {
        setOpaque(true);
        this.colorFondo = colorFondo;
        this.colorVelo = colorVelo;
        this.aplicarVelo = aplicarVelo;
        this.cubrirArea = cubrirArea;
        setBackground(colorFondo);
        try {
            if (rutaImagen != null && Files.exists(rutaImagen)) {
                this.imagen = ImageIO.read(rutaImagen.toFile());
            }
        } catch (IOException e) {
            this.imagen = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (imagen != null) {
            int imgW = imagen.getWidth(this);
            int imgH = imagen.getHeight(this);
            if (imgW > 0 && imgH > 0) {
                double scale = cubrirArea
                        ? Math.max(getWidth() / (double) imgW, getHeight() / (double) imgH)
                        : Math.min(getWidth() / (double) imgW, getHeight() / (double) imgH);
                int drawW = (int) Math.round(imgW * scale);
                int drawH = (int) Math.round(imgH * scale);
                int x = (getWidth() - drawW) / 2;
                int y = (getHeight() - drawH) / 2;
                g2.drawImage(imagen, x, y, drawW, drawH, this);
            }
        } else {
            g2.setColor(colorFondo);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        if (aplicarVelo) {
            g2.setColor(colorVelo);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        g2.dispose();
    }
}
