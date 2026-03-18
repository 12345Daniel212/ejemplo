import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ejercicio2 extends JFrame {
    private JTextField campoTexto;
    private JComboBox<String> combo;
    private JButton btnAgregar;

    public Ejercicio2() {
        setTitle("Ejercicio 2 - Clase Anónima");
        setLayout(new FlowLayout());

        campoTexto = new JTextField(10);
        combo = new JComboBox<>();
        btnAgregar = new JButton("Agregar");

        // CLASE ANÓNIMA para el botón
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = campoTexto.getText().trim();
                if (!texto.isEmpty()) {
                    combo.addItem(texto);
                    campoTexto.setText("");
                }
            }
        });

        add(campoTexto); add(btnAgregar); add(combo);
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Ejercicio2();
    }
}