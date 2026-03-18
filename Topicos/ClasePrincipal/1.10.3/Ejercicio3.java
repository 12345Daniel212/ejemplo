import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ejercicio3 extends JFrame {
    private JLabel etiqueta;
    private JButton btnIzq, btnCen, btnDer;

    public Ejercicio3() {
        setTitle("Ejercicio 3 - Clases Anónimas");
        setLayout(new GridLayout(4, 1, 5, 5));

        etiqueta = new JLabel("Manejo de eventos", SwingConstants.CENTER);
        btnIzq = new JButton("Izquierda");
        btnCen = new JButton("Centro");
        btnDer = new JButton("Derecha");

        // Tres clases anónimas distintas, una para cada botón
        btnIzq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                etiqueta.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });

        btnCen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
            }
        });

        btnDer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                etiqueta.setHorizontalAlignment(SwingConstants.RIGHT);
            }
        });

        add(etiqueta); add(btnIzq); add(btnCen); add(btnDer);
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Ejercicio3();
    }
}