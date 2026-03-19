import javax.swing.*;
import java.awt.*;

public class TemporizadorPrueba extends JFrame {
    private int contador = 0;

    public TemporizadorPrueba() {
        JButton btn = new JButton("Nuevo Temporizador (10s)");
        btn.addActionListener(e -> new Thread(new Temporizador(10, ++contador)).start());

        add(btn);
        setLayout(new FlowLayout());
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TemporizadorPrueba();
    }
}

