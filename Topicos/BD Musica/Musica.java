import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

// Importación de tus componentes personalizados
import mis_componentes.BotonColor;
import mis_componentes.TextoColor;

public class Musica extends JFrame {

    // Reemplazo de JTextField por TextoColor
    TextoColor nombreTxt;
    TextoColor nombreArTxt;

    JComboBox<String> generoTxtCombo;
    JSpinner duracionSpn;
    JSpinner numCancionesSpn;
    JSpinner anioSpn;
    JRadioButton[] calificacion;
    ButtonGroup grupoCalificacion;
    JCheckBox fisicoCbx;
    JCheckBox digitalCbx;

    JLabel nombreDis;
    JLabel nombreAr;

    public Musica() {
        inicializarCom();
    }

    private void inicializarCom() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Colección de música - Componentes Propios");
        this.setSize(600, 550);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        JLabel titulo = new JLabel("- COLECCIÓN DE MÚSICA -");
        titulo.setBounds(200, 10, 200, 30);
        this.add(titulo);

        nombreDis = new JLabel("Nombre del disco:");
        nombreDis.setBounds(10, 50, 150, 20);
        this.add(nombreDis);

        // Uso de TextoColor en lugar de JTextField
        nombreTxt = new TextoColor();
        nombreTxt.setBounds(10, 75, 550, 25);
        this.add(nombreTxt);

        nombreAr = new JLabel("Nombre del artista:");
        nombreAr.setBounds(10, 110, 150, 20);
        this.add(nombreAr);

        // Uso de TextoColor en lugar de JTextField
        nombreArTxt = new TextoColor();
        nombreArTxt.setBounds(10, 135, 550, 25);
        this.add(nombreArTxt);

        // Listeners para validación de color
        nombreTxt.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {}
            @Override public void focusLost(FocusEvent e) {
                nombreDis.setForeground(nombreTxt.getText().trim().isEmpty() ? Color.RED : Color.BLACK);
            }
        });

        nombreArTxt.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {}
            @Override public void focusLost(FocusEvent e) {
                nombreAr.setForeground(nombreArTxt.getText().trim().isEmpty() ? Color.RED : Color.BLACK);
            }
        });

        // Configuración de Genero y Spinners
        JLabel genero = new JLabel("Género:");
        genero.setBounds(10, 170, 100, 20);
        this.add(genero);

        String[] generoTxt = {"Rock", "Pop", "Metal", "Indie", "Electronica", "Clásica"};
        generoTxtCombo = new JComboBox<>(generoTxt);
        generoTxtCombo.setBounds(10, 195, 550, 25);
        this.add(generoTxtCombo);

        JLabel duracionLbl = new JLabel("Duración(min):");
        duracionLbl.setBounds(10, 230, 100, 20);
        this.add(duracionLbl);
        duracionSpn = new JSpinner(new SpinnerNumberModel(30, 1, 500, 1));
        duracionSpn.setBounds(10, 255, 550, 25);
        this.add(duracionSpn);

        JLabel numCanciones = new JLabel("No. de Canciones:");
        numCanciones.setBounds(10, 290, 150, 20);
        this.add(numCanciones);
        numCancionesSpn = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        numCancionesSpn.setBounds(10, 315, 550, 25);
        this.add(numCancionesSpn);

        JLabel anioLbl = new JLabel("Año:");
        anioLbl.setBounds(10, 350, 100, 20);
        this.add(anioLbl);
        anioSpn = new JSpinner(new SpinnerNumberModel(2000, 1900, 2026, 1));
        anioSpn.setBounds(10, 375, 550, 25);
        this.add(anioSpn);

        // Calificación con RadioButtons
        JLabel calificacionLbl = new JLabel("Calificación:");
        calificacionLbl.setBounds(10, 410, 100, 20);
        this.add(calificacionLbl);

        calificacion = new JRadioButton[5];
        grupoCalificacion = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            calificacion[i] = new JRadioButton((i + 1) + "");
            calificacion[i].setBounds(120 + (i * 50), 410, 45, 20);
            grupoCalificacion.add(calificacion[i]);
            this.add(calificacion[i]);
        }

        // Formato con CheckBoxes
        JLabel formatoLbl = new JLabel("Formato:");
        formatoLbl.setBounds(10, 440, 100, 20);
        this.add(formatoLbl);

        digitalCbx = new JCheckBox("Digital");
        digitalCbx.setBounds(120, 440, 80, 20);
        this.add(digitalCbx);

        fisicoCbx = new JCheckBox("Físico");
        fisicoCbx.setBounds(200, 440, 80, 20);
        this.add(fisicoCbx);

        // Reemplazo de JButton por BotonColor
        BotonColor aceptarBtn = new BotonColor("Aceptar");
        aceptarBtn.setBounds(150, 480, 100, 30);
        this.add(aceptarBtn);

        BotonColor cancelarBtn = new BotonColor("Cancelar");
        cancelarBtn.setBounds(260, 480, 100, 30);
        this.add(cancelarBtn);

        BotonColor salirBtn = new BotonColor("Salir");
        salirBtn.setBounds(370, 480, 100, 30);
        this.add(salirBtn);

        // Lógica de botones
        aceptarBtn.addActionListener(e -> {
            String faltantes = "";
            if (nombreTxt.getText().trim().isEmpty()) faltantes += "- Nombre del disco\n";
            if (nombreArTxt.getText().trim().isEmpty()) faltantes += "- Nombre del artista\n";
            if (grupoCalificacion.getSelection() == null) faltantes += "- Calificación\n";
            if (!digitalCbx.isSelected() && !fisicoCbx.isSelected()) faltantes += "- Formato del disco\n";

            if (!faltantes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Faltan los siguientes campos:\n" + faltantes, "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        salirBtn.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Aviso", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) System.exit(0);
        });

        cancelarBtn.addActionListener(e -> {
            nombreTxt.setText("");
            nombreArTxt.setText("");
            grupoCalificacion.clearSelection();
            digitalCbx.setSelected(false);
            fisicoCbx.setSelected(false);
        });
    }

    public static void main(String[] args) {
        new Musica().setVisible(true);
    }
}