package mis_componentes;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class TextoColor extends JTextField {

    public TextoColor() {
        super();
        setup();
    }

    public TextoColor(String texto) {
        super(texto);
        setup();
    }

    private void setup() {
        // Al ganar el foco cambia de color (ejemplo: amarillo claro)
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(new Color(255, 255, 200));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }
}