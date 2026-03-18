import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class VisualizadorImagenes extends JFrame {

    // 1. Movemos el índice y el arreglo aquí para que sean accesibles
    private int indice = 0;
    private final String[] imagenes = {
        "/imagenes/atardecer.png",
        "/imagenes/australia.png",
        "/imagenes/bajodelmar.png",
        "/imagenes/birds.png",
        "/imagenes/buho.png"
    };

    public VisualizadorImagenes() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Visualizador de Imágenes");
        this.setSize(1050, 550);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JLabel imagenLabel = new JLabel();
        imagenLabel.setHorizontalAlignment(JLabel.CENTER); // Centra la imagen
        this.add(imagenLabel, BorderLayout.CENTER);

        // 2. Corregido: Cargamos la primera imagen correctamente
        actualizarImagen(imagenLabel);

        JButton izquierdaBtn = new JButton("<");
        this.add(izquierdaBtn, BorderLayout.WEST);

        JButton derechaBtn = new JButton(">");
        this.add(derechaBtn, BorderLayout.EAST);

        // 3. Lógica de los botones
        derechaBtn.addActionListener(evento -> {
            indice = (indice + 1) % imagenes.length;
            actualizarImagen(imagenLabel);
        });

        izquierdaBtn.addActionListener(evento -> {
            indice = (indice - 1 + imagenes.length) % imagenes.length; // Truco para no ir a negativos
            actualizarImagen(imagenLabel);
        });
    }

    // Método auxiliar para no repetir código
    private void actualizarImagen(JLabel label) {
        try {
            label.setIcon(new ImageIcon(getClass().getResource(imagenes[indice])));
        } catch (Exception e) {
            System.err.println("No se encontró la imagen: " + imagenes[indice]);
        }
    }

    public static void main(String[] args) {
        new VisualizadorImagenes().setVisible(true);
    }
}