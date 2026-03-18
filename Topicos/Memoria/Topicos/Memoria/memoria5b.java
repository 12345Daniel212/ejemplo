package Topicos.Memoria;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.*;

public class memoria5b extends JFrame {

    private JButton primeraTarjeta;
    private int errores = 0;
    private int aciertos = 0;
    private JLabel marcadorLbl;
    private boolean esperando = false; // Para evitar clics extra durante el timer

    public memoria5b() {
        inializarComponentes();
    }

    private void inializarComponentes() {
        setTitle("Memoria");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // setLayout(new GridLayout(4, 4, 10, 10));
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel(new FlowLayout());
        this.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel((new GridLayout(4, 4, 10, 10)));
        this.add(panelCentral, BorderLayout.CENTER);

        marcadorLbl = new JLabel("Errores: 0");
        marcadorLbl.setFont(new Font("Arial", Font.BOLD, 18));
        panelSuperior.add(marcadorLbl);

        Font font = new Font("Arial", Font.BOLD, 18);

        //panel central
        JButton[] tarjetasBtn = new JButton[16];
        for (int i = 0; i < tarjetasBtn.length; i++) {
            tarjetasBtn[i] = new JButton();
            tarjetasBtn[i].setFont(font);
            // Ocultar texto inicialmente igualando color de fuente al del botón
            tarjetasBtn[i].setForeground(new Color(240, 240, 240));
            tarjetasBtn[i].addActionListener(evento -> tarjetaClick(evento));
            panelCentral.add(tarjetasBtn[i]);
        }

        String[] palabras = { "Tala Rangel", "Armando Gonzales", "Oso Gonzales", "Daniel Aguirre", "Omar Govea",
                "Piojo Alvarado", "Ledezma", "Efrain Alvarez" };

        Random rand = new Random(System.currentTimeMillis());
        int index;
        for (int i = 0; i < 2; i++) {
            for (String palabra : palabras) {
                do {
                    index = rand.nextInt(16);
                } while (tarjetasBtn[index].getText() != null && !tarjetasBtn[index].getText().isEmpty());
                tarjetasBtn[index].setText(palabra);
            }
        }
    }

    private void tarjetaClick(ActionEvent evento) {
        if (esperando)
            return; // Si el timer está corriendo, no hace nada

        JButton tarjetaActiva = (JButton) evento.getSource();

        // Evitar clickear la misma o una ya desactivada
        if (tarjetaActiva == primeraTarjeta || !tarjetaActiva.isEnabled())
            return;

        // Mostrar texto
        tarjetaActiva.setForeground(Color.BLACK);

        if (primeraTarjeta == null) {
            primeraTarjeta = tarjetaActiva;
        } else {
            if (primeraTarjeta.getText().equals(tarjetaActiva.getText())) {
                // ACIERTO
                primeraTarjeta.setEnabled(false);
                tarjetaActiva.setEnabled(false);
                primeraTarjeta = null;
                aciertos++;

                if (aciertos == 8) {
                    JOptionPane.showMessageDialog(this, "¡Felicidades! Ganaste.");
                }
            } else {
                // ERROR
                esperando = true;
                errores++;
                System.out.println("Errores cometidos: " + errores);

                Timer timer = new Timer(1000, e -> {
                    primeraTarjeta.setForeground(tarjetaActiva.getBackground());
                    tarjetaActiva.setForeground(tarjetaActiva.getBackground());
                    primeraTarjeta = null;
                    esperando = false;
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new memoria5b().setVisible(true);
        });
    }
}