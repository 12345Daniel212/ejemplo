import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClasePrincipal extends JFrame implements ActionListener {
    // Definición de los campos de texto
    private JTextField campo1, campo2;

    public ClasePrincipal() {
        ini();
    }

    private void ini() {
        // Configuración básica de la ventana
        setTitle("Tópicos Avanzados - Unidad I - Eventos");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        // Inicializar los campos de texto
        campo1 = new JTextField(25);
        campo2 = new JTextField(25);

        // El segundo campo suele ser de solo lectura para mostrar el resultado
        campo2.setEditable(false);

        // Agregar los componentes al contenedor
        this.add(new JLabel("Ingrese texto y presione ENTER:"));
        this.add(campo1);
        this.add(new JLabel("Resultado:"));
        this.add(campo2);

        // Registrar el evento: La clase 'this' (ClasePrincipal) manejará el evento
        campo1.addActionListener(this);
    }

    public static void main(String[] args) {
        ClasePrincipal cp = new ClasePrincipal();
        cp.setVisible(true);
        // Centrar la ventana al iniciar
        cp.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Verificamos si la fuente del evento fue el primer campo de texto
        if (e.getSource() == campo1) {
            // Obtenemos la cadena del primer campo
            String cadena = campo1.getText();
            // La copiamos al segundo cuadro de texto
            campo2.setText(cadena);

            // Opcional: limpiar el primer campo después de copiar
            // campo1.setText("");
        }
    }
}