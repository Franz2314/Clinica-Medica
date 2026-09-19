package clinicamedica;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import servicios.ClinicaSistema;
import vista.ventanalogin;
import vista.ventanacarga;
import vista.ventanaprincipal;

public class ClinicaMedica {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            configurarTema();
            ventanacarga splash = new ventanacarga();
            splash.mostrarBrevemente(() -> {
                SwingWorker<ClinicaSistema, Void> worker = new SwingWorker<>() {
                    @Override
                    protected ClinicaSistema doInBackground() {
                        ClinicaSistema sistema = new ClinicaSistema();
                        sistema.cargarDatosIniciales();
                        return sistema;
                    }

                    @Override
                    protected void done() {
                        try {
                            ClinicaSistema sistema = get();
                            if (!ventanalogin.mostrar(null)) {
                                System.exit(0);
                                return;
                            }
                            ventanaprincipal ventana = new ventanaprincipal(sistema);
                            ventana.setVisible(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "No se pudo iniciar ATE-SALUD.\n\n" + e.getMessage(),
                                    "Error de inicio",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };
                worker.execute();
            });
        });
    }

    private static void configurarTema() {
        try {
            System.setProperty("flatlaf.useNativeLibrary", "false");
            UIManager.put("defaultFont", UIManager.getFont("Label.font").deriveFont(15f));
            UIManager.put("Component.arc", 16);
            UIManager.put("Button.arc", 18);
            UIManager.put("TextComponent.arc", 14);
            UIManager.put("ScrollBar.width", 14);
            UIManager.put("TabbedPane.tabHeight", 38);
            UIManager.put("TabbedPane.tabArc", 14);
            UIManager.put("TabbedPane.selectedBackground", java.awt.Color.WHITE);
            UIManager.put("TabbedPane.underlineColor", new java.awt.Color(15, 91, 150));
            UIManager.put("TabbedPane.focusColor", new java.awt.Color(15, 91, 150));
            UIManager.put("Table.showHorizontalLines", true);
            UIManager.put("Table.showVerticalLines", false);
            UIManager.put("Table.intercellSpacing", new java.awt.Dimension(0, 1));
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("No se pudo cargar FlatLaf: " + e.getMessage());
        }
    }
}
