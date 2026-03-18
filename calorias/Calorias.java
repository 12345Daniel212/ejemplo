import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calorias extends JFrame {

    JSpinner pesoSpn;
    JSpinner alturaSpn;
    JSpinner edadSpn;
    JComboBox<String> sexoCmb;
    JComboBox<String> ejercicioCmb; // Renombrado para mayor claridad
    JButton calcularBtn;
    JButton salirBtn;
    JLabel resultadoLbl;

    public Calorias() {
        inicializarCalorias();
    }

    private void inicializarCalorias() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 400);
        this.setTitle("Cálculo de calorías");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        // Título
        JLabel tituloLbl = new JLabel("CÁLCULO DE CALORÍAS", SwingConstants.CENTER);
        tituloLbl.setFont(new Font("Arial", Font.BOLD, 22));
        tituloLbl.setForeground(new Color(173, 216, 230)); // Color azul claro de la imagen
        this.add(tituloLbl, BorderLayout.NORTH);

        /* Panel Central con GridLayout */
        JPanel panelCentral = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelCentral.add(new JLabel("Peso:"));
        pesoSpn = new JSpinner(new SpinnerNumberModel(50, 1, 300, 1));
        panelCentral.add(pesoSpn);

        panelCentral.add(new JLabel("Altura (cm):"));
        alturaSpn = new JSpinner(new SpinnerNumberModel(150, 1, 250, 1));
        panelCentral.add(alturaSpn);

        panelCentral.add(new JLabel("Edad:"));
        edadSpn = new JSpinner(new SpinnerNumberModel(18, 1, 120, 1));
        panelCentral.add(edadSpn);

        panelCentral.add(new JLabel("Género:"));
        String[] sexos = {"Masculino", "Femenino"};
        sexoCmb = new JComboBox<>(sexos);
        panelCentral.add(sexoCmb);

        panelCentral.add(new JLabel("Cantidad de ejercicio:"));
        String[] niveles = {"Poco o ninguno", "Ligero", "Moderado", "Fuerte", "Muy fuerte"};
        ejercicioCmb = new JComboBox<>(niveles);
        panelCentral.add(ejercicioCmb);

        // Botón Calcular y Etiqueta de Resultado
        calcularBtn = new JButton("Calcular");
        panelCentral.add(calcularBtn);

        resultadoLbl = new JLabel("Calorías");
        panelCentral.add(resultadoLbl);

        this.add(panelCentral, BorderLayout.CENTER);

        // Panel Inferior para el botón Salir
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        salirBtn = new JButton("Salir");
        panelInferior.add(salirBtn);
        this.add(panelInferior, BorderLayout.SOUTH);

        // --- LÓGICA DE LOS BOTONES ---

        // Evento Salir
        salirBtn.addActionListener(e -> System.exit(0));

        // Evento Calcular
        calcularBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularCalorias();
            }
        });
    }

    private void calcularCalorias() {
        // 1. Obtener datos de los componentes
        int peso = (int) pesoSpn.getValue();
        int altura = (int) alturaSpn.getValue();
        int edad = (int) edadSpn.getValue();
        int generoIndice = sexoCmb.getSelectedIndex(); // 0: Masculino, 1: Femenino
        int ejercicioIndice = ejercicioCmb.getSelectedIndex();

        // 2. Calcular TMB (Tasa Metabólica Basal) usando Harris-Benedict
        double tmb;
        if (generoIndice == 0) { // Masculino
            tmb = (10 * peso) + (6.25 * altura) - (5 * edad) + 5;
        } else { // Femenino
            tmb = (10 * peso) + (6.25 * altura) - (5 * edad) - 161;
        }

        // 3. Multiplicar por factor de actividad física
        double factorActividad[] = {1.2, 1.375, 1.55, 1.725, 1.9};
        double caloriasTotales = tmb * factorActividad[ejercicioIndice];

        // 4. Mostrar resultado formateado
        resultadoLbl.setText(String.format("%.2f kcal", caloriasTotales));
    }

    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new Calorias().setVisible(true);
        });
    }
}