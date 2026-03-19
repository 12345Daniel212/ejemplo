import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemporizadorPrueba extends JFrame {
    private int contadorHilos = 0;

    public TemporizadorPrueba() {
        setTitle("Control de Temporizadores");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton botonIniciar = new JButton("Iniciar Nuevo Temporizador");
        add(botonIniciar);

        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contadorHilos++;
                Runnable r = new Temporizador(10, contadorHilos);
                Thread t = new Thread(r);
                t.start();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        new TemporizadorPrueba().setVisible(true);
        });
    }
}