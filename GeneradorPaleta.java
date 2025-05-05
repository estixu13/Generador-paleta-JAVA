import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class GeneradorPaleta extends JFrame {

    private JPanel panelColores;
    private final int NUM_COLORES = 5; // Número de colores que voy a mostrar 

    public GeneradorPaleta() {
        setTitle("🎨 Generador de Paletas"); // Título de la ventana
        setSize(800, 300); // Tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cierro la aplicación al cerrar ventana
        setLayout(new BorderLayout()); // Uso diseño de borde

        // Creo el botón para generar colores
        JButton botonGenerar = new JButton("Generar nueva paleta");

        // Acción de clic en el botón
        botonGenerar.addActionListener(e -> generarPaleta());

        // Panel que tiene los colores
        panelColores = new JPanel();
        panelColores.setLayout(new GridLayout(1, NUM_COLORES, 10, 10));
        panelColores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Añado componentes a la ventana
        add(botonGenerar, BorderLayout.NORTH);
        add(panelColores, BorderLayout.CENTER);

        generarPaleta(); // Genero la primera paleta al iniciar
        setVisible(true); // Muestro la ventana
    }

    // Método para generar y mostrar los colores
    private void generarPaleta() {
        panelColores.removeAll(); // Limpio los colores anteriores
        Random rand = new Random();

        for (int i = 0; i < NUM_COLORES; i++) {
            // Creo color aleatorio
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());

            // Creo panel con el color
            JPanel panel = new JPanel();
            panel.setBackground(color);
            panel.setToolTipText(hex); // Muestro el código al pasar el ratón

            // Muestro el código HEX como texto
            JLabel etiqueta = new JLabel(hex, SwingConstants.CENTER);
            etiqueta.setForeground(Color.WHITE);
            etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
            panel.setLayout(new BorderLayout());
            panel.add(etiqueta, BorderLayout.SOUTH);

            // Copio el color al portapapeles al hacer clic
            panel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    StringSelection selection = new StringSelection(hex);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, null);
                    JOptionPane.showMessageDialog(null, "¡Color copiado: " + hex + "!");
                }
            });

            panelColores.add(panel); // Añado panel al contenedor
        }

        panelColores.revalidate();
        panelColores.repaint();
    }

    public static void main(String[] args) {
        // Ejecuto en el hilo de interfaz gráfica
        SwingUtilities.invokeLater(() -> new GeneradorPaleta());
    }
}
