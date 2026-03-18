import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClasePrincipal extends JFrame {
    private JTextField campo1, campo2;

    public ClasePrincipal() {
        ini();
    }

    private void ini() {
        setTitle("Tópicos Avanzados - Clase Anidada");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        campo1 = new JTextField(25);
        campo2 = new JTextField(25);
        campo2.setEditable(false);

        this.add(new JLabel("Ingrese texto y presione ENTER:"));
        this.add(campo1);
        this.add(new JLabel("Resultado:"));
        this.add(campo2);

        ManejadorEvento manejador = new ManejadorEvento();
        campo1.addActionListener(manejador);
    }
    private class ManejadorEvento implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == campo1) {
                String cadena = campo1.getText();
                campo2.setText(cadena);
            }
        }
    }

    public static void main(String[] args) {
        ClasePrincipal cp = new ClasePrincipal();
        cp.setVisible(true);
        cp.setLocationRelativeTo(null);
    }
}