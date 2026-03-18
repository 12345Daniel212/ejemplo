import javax.swing.*;
import java.awt.*;

public class coleccion extends JFrame {

    public coleccion() {
        setTitle("Colección de música");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);
        setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNorte.add(new JLabel("COLECCIÓN DE MÚSICA"));
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(new JButton("Aceptar"));
        panelSur.add(new JButton("Cancelar"));
        add(panelSur, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel(new GridLayout(16, 1));

        panelCentro.add(new JLabel("Nombre del disco:"));
        panelCentro.add(new JTextField());

        panelCentro.add(new JLabel("Nombre del artista:"));
        panelCentro.add(new JTextField());

        panelCentro.add(new JLabel("Género:"));
        String[] generos = {"Rock", "Pop", "Metal", "Jazz"};
        panelCentro.add(new JComboBox<>(generos));

        panelCentro.add(new JLabel("Duración (min):"));
        panelCentro.add(new JSpinner(new SpinnerNumberModel(30, 1, 500, 1)));

        panelCentro.add(new JLabel("No. de Canciones:"));
        panelCentro.add(new JSpinner(new SpinnerNumberModel(10, 1, 100, 1)));

        panelCentro.add(new JLabel("Año:"));
        panelCentro.add(new JSpinner(new SpinnerNumberModel(2000, 1900, 2026, 1)));

        panelCentro.add(new JLabel("Calificación:"));
        JPanel panelCalificacion = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for(int i=1; i<=5; i++) {
            panelCalificacion.add(new JRadioButton());
        }
        panelCentro.add(panelCalificacion);

        panelCentro.add(new JLabel("Formato:"));
        JPanel panelFormato = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFormato.add(new JCheckBox("Digital"));
        panelFormato.add(new JCheckBox("Fisico"));

        panelCentro.add(panelFormato);

        add(panelCentro, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new coleccion());
    }
}