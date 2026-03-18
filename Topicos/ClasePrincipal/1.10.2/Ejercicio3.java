import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ejercicio3 extends JFrame {
    private JLabel etiqueta;
    private JButton btnIzq, btnCen, btnDer;

    public Ejercicio3() {
        setTitle("Ejercicio 3 - Alineación con Clase Anidada");
        setLayout(new GridLayout(4, 1, 5, 5));

        etiqueta = new JLabel("Manejo de eventos", SwingConstants.CENTER);
        btnIzq = new JButton("Izquierda");
        btnCen = new JButton("Centro");
        btnDer = new JButton("Derecha");

        ManejadorAlineacion manejador = new ManejadorAlineacion();

        btnIzq.addActionListener(manejador);
        btnCen.addActionListener(manejador);
        btnDer.addActionListener(manejador);

        add(etiqueta);
        add(btnIzq);
        add(btnCen);
        add(btnDer);

        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ManejadorAlineacion implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Identificamos la fuente del evento
            Object fuente = e.getSource();

            if (fuente == btnIzq) {
                etiqueta.setHorizontalAlignment(SwingConstants.LEFT);
            } else if (fuente == btnCen) {
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
            } else if (fuente == btnDer) {
                etiqueta.setHorizontalAlignment(SwingConstants.RIGHT);
            }
        }
    }

    public static void main(String[] args) {
        new Ejercicio3();
    }
}