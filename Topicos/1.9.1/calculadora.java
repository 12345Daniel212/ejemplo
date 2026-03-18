import javax.swing.*;
import java.awt.*;

public class calculadora extends JFrame {

    public calculadora() {
        // Configuración de la Ventana principal con BorderLayout
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        // 1. Panel del TextField (Norte)
        // Usa BorderLayout y contiene el campo de texto [cite: 4, 17, 18]
        JPanel panelTexto = new JPanel(new BorderLayout());
        JTextField pantalla = new JTextField();
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        panelTexto.add(pantalla, BorderLayout.CENTER);

        // 2. Panel de los Botones (Centro)
        // Usa GridLayout para organizar los botones [cite: 5, 17, 19]
        // Se define una rejilla de 4x4 según la imagen [cite: 20-31]
        JPanel panelBotones = new JPanel(new GridLayout(4, 4, 5, 5));

        String[] etiquetas = {
            "7", "8", "9", "x",
            "4", "5", "6", "/",
            "1", "2", "3", "+",
            ".", "0", "=", "-"
        };

        for (String texto : etiquetas) {
            panelBotones.add(new JButton(texto));
        }

        // Agregar los paneles al contenedor principal (Ventana) [cite: 14]
        add(panelTexto, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz
        SwingUtilities.invokeLater(() -> new calculadora());
    }
}