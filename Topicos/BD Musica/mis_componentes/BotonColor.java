package mis_componentes;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonColor extends JButton {

    public BotonColor(String texto) {
        super(texto);
        // Establecemos un color inicial
        setBackground(Color.LIGHT_GRAY);

        // Añadimos un efecto sencillo para que haga honor a su nombre
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.CYAN);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY);
            }
        });
    }
}