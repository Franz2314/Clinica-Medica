package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;

public class ventanacarga extends JWindow {

    public ventanacarga() {
        getContentPane().setLayout(new MigLayout("fill, insets 24", "[grow]", "[][][]"));
        getContentPane().setBackground(new Color(245, 250, 255));
        ((javax.swing.JComponent) getContentPane()).setBorder(BorderFactory.createLineBorder(new Color(196, 216, 234), 1, true));

        JLabel logo = new JLabel("CM", JLabel.CENTER);
        logo.setOpaque(true);
        logo.setBackground(new Color(22, 110, 191));
        logo.setForeground(Color.WHITE);
        logo.setFont(fuentesinterfaz.title(28));
        logo.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JLabel titulo = new JLabel("ATE-SALUD", JLabel.CENTER);
        titulo.setFont(fuentesinterfaz.title(30));
        titulo.setForeground(new Color(17, 86, 148));

        JLabel subtitulo = new JLabel("Cargando sistema integral de gestion clinica...", JLabel.CENTER);
        subtitulo.setFont(fuentesinterfaz.body(14));
        subtitulo.setForeground(new Color(84, 106, 128));

        JProgressBar barra = new JProgressBar();
        barra.setIndeterminate(true);

        getContentPane().add(logo, "center, wrap");
        getContentPane().add(titulo, "center, wrap");
        getContentPane().add(subtitulo, "center, wrap");
        getContentPane().add(barra, "growx");

        setSize(420, 240);
        setLocationRelativeTo(null);
    }

    public void mostrarBrevemente(Runnable onFinish) {
        setVisible(true);
        Timer timer = new Timer(1400, e -> {
            setVisible(false);
            dispose();
            onFinish.run();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
