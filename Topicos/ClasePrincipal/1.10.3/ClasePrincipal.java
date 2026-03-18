import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClasePrincipal extends JFrame {
    private JTextField campo1, campo2;

    public ClasePrincipal() {
        setTitle("Ejercicio 1 - Clase Anónima");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        campo1 = new JTextField(25);
        campo2 = new JTextField(25);
        campo2.setEditable(false);

        // CLASE ANÓNIMA: Se define "al vuelo"
        campo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campo2.setText(campo1.getText());
            }
        });

        add(new JLabel("Ingrese texto y ENTER:"));
        add(campo1);
        add(new JLabel("Resultado:"));
        add(campo2);
    }

    public static void main(String[] args) {
        new ClasePrincipal().setVisible(true);
    }
}