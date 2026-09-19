package vista;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;

public class panelencabezado extends JPanel {
    private final JLabel titleLabel;
    private final JLabel subtitleLabel;
    private temaaplicacion tema;

    public panelencabezado(temaaplicacion tema) {
        this.tema = tema;
        setOpaque(false);
        setLayout(new MigLayout("fill, insets 22 28 18 28", "[grow]", "[]8[]"));
        titleLabel = new JLabel("ATE-SALUD", SwingConstants.CENTER);
        titleLabel.setFont(fuentesinterfaz.bannerTitle(42));
        titleLabel.setForeground(tema.bannerTitulo);

        subtitleLabel = new JLabel("Sistema integral de gestion clinica, triaje, citas y facturacion", SwingConstants.CENTER);
        subtitleLabel.setFont(fuentesinterfaz.bannerSubtitle(18));
        subtitleLabel.setForeground(tema.bannerSubtitulo);

        add(titleLabel, "growx, wrap");
        add(subtitleLabel, "growx");
    }

    public void actualizarModulo(String nombre, Color primary, Color secondary) {
        titleLabel.setText("ATE-SALUD");
        subtitleLabel.setText(nombre);
    }

    public void actualizarTema(temaaplicacion tema) {
        this.tema = tema;
        titleLabel.setForeground(tema.bannerTitulo);
        subtitleLabel.setForeground(tema.bannerSubtitulo);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(tema.bannerTarjeta);
        g2.fillRoundRect(getWidth() / 2 - 290, 22, 580, 108, 36, 36);
        g2.dispose();
        super.paintComponent(g);
    }
}
