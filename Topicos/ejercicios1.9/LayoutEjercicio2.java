// ejercicio 2 TOPICOS AVANZADOS DE PROGRAMACION
// 1.9 LAYOUTS

import javax.swing.*;
import java.awt.GridLayout;

public class LayoutEjercicio2 extends JFrame {

    public LayoutEjercicio2() {
        componentesVentana();
    }

    private void componentesVentana() {
        // CONFIGURACION DE LA VENTANA
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Calculadora");
        this.setSize(300, 400);
        this.setLocationRelativeTo(null);

        // Definimos la rejilla de 4x4 como en la diapositivas
        this.setLayout(new GridLayout(4, 4, 10, 10));

        this.add(new JButton("7"));
        this.add(new JButton("8"));
        this.add(new JButton("9"));
        this.add(new JButton("x"));
        this.add(new JButton("4"));
        this.add(new JButton("5"));
        this.add(new JButton("6"));
        this.add(new JButton("/"));
        this.add(new JButton("1"));
        this.add(new JButton("2"));
        this.add(new JButton("3"));
        this.add(new JButton("+"));
        this.add(new JButton("."));
        this.add(new JButton("0"));
        this.add(new JButton("="));
        this.add(new JButton("-"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LayoutEjercicio2 ventana = new LayoutEjercicio2();
            ventana.setVisible(true);
        });
    }
}