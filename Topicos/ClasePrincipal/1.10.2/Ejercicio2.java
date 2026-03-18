import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ejercicio2 extends JFrame {
    private JTextField campoTexto;
    private JComboBox<String> combo;
    private JButton btnAgregar;

    public Ejercicio2() {
        setTitle("Ejercicio 2 - Clase Anidada");
        setLayout(new FlowLayout());

        campoTexto = new JTextField(10);
        combo = new JComboBox<>();
        btnAgregar = new JButton("Agregar");

        EscuchadorBoton manejador = new EscuchadorBoton();
        btnAgregar.addActionListener(manejador);

        add(campoTexto);
        add(btnAgregar);
        add(combo);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setVisible(true);
    }

    private class EscuchadorBoton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String texto = campoTexto.getText().trim();

            if (!texto.isEmpty()) {
                combo.addItem(texto);
                campoTexto.setText("");
                campoTexto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "¡El campo está vacío!");
            }
        }
    }

    public static void main(String[] args) {
        new Ejercicio2();
    }
}