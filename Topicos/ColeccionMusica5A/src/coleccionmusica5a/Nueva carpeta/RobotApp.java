import javax.swing.*;
import java.awt.*;

public class RobotApp extends JFrame {

    public RobotApp() {
        // Configuración de la ventana de 400x400 píxeles
        setTitle("Robot");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Agregar el panel de dibujo
        add(new PanelDibujo());
    }

    // Clase anidada que hereda de JPanel para realizar el dibujo
    private class PanelDibujo extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 1. Dibujar la cabeza del robot (Cuadrado azul)
            // Tamaño 200x200 en la coordenada (100, 100)
            g.setColor(Color.BLUE);
            g.fillRect(100, 100, 200, 200);

            // 2. Dibujar los ojos (Cuadrados amarillos)
            g.setColor(Color.YELLOW);
            g.fillRect(135, 140, 50, 50); // Ojo izquierdo
            g.fillRect(215, 140, 50, 50); // Ojo derecho

            // 3. Dibujar las pupilas (Cuadrados negros)
            g.setColor(Color.BLACK);
            g.fillRect(150, 155, 20, 20); // Pupila izquierda
            g.fillRect(230, 155, 20, 20); // Pupila derecha

            // 4. Dibujar la boca (Rectángulo amarillo)
            g.setColor(Color.YELLOW);
            g.fillRect(135, 220, 130, 50);
        }
    }

    public static void main(String[] args) {
        // Ejecutar la aplicación
        SwingUtilities.invokeLater(() -> {
            new RobotApp().setVisible(true);
        });
    }
}