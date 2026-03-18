import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class EjemploFocusListener extends JFrame {

    private JTextField txtNombre, txtApellidoP, txtApellidoM;
    private JLabel lblNombre, lblApellidoP, lblApellidoM;

    public EjemploFocusListener() {
        // Configuración de la ventana
        setTitle("Manejo de FocusListener");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Inicialización de componentes
        lblNombre = new JLabel(" Nombre:");
        txtNombre = new JTextField();

        lblApellidoP = new JLabel(" Apellido Paterno:");
        txtApellidoP = new JTextField();

        lblApellidoM = new JLabel(" Apellido Materno:");
        txtApellidoM = new JTextField();

        // --- Agregando los FocusListeners con Clases Anónimas ---

        // Validación para Nombre
        txtNombre.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // No se requiere acción al ganar el foco
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtNombre.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Nombre' no puede estar vacío");
                }
            }
        });

        // Validación para Apellido Paterno
        txtApellidoP.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtApellidoP.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Apellido Paterno' no puede estar vacío");
                }
            }
        });

        // Validación para Apellido Materno
        txtApellidoM.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtApellidoM.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo 'Apellido Materno' no puede estar vacío");
                }
            }
        });

        // Agregar componentes a la ventana
        add(lblNombre);
        add(txtNombre);
        add(lblApellidoP);
        add(txtApellidoP);
        add(lblApellidoM);
        add(txtApellidoM);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Ejecutar el formulario
        new EjemploFocusListener();
    }
}