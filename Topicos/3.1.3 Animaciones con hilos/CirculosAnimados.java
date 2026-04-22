import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CirculosAnimados extends JFrame {

    private JButton iniciarBoton;
    private JButton detenerBoton;
    private PanelDibujo panel;

    // Usamos una lista sincronizada y guardamos los hilos para poder interrumpirlos
    private List<CirculoAnimado> circulos = Collections.synchronizedList(new ArrayList<>());
    private List<Thread> hilos = Collections.synchronizedList(new ArrayList<>());

    public CirculosAnimados() {
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setTitle("Hilos - Interrupción de Hilos Dormidos");
        setLayout(new BorderLayout());

        panel = new PanelDibujo();
        add(panel, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        add(panelBotones, BorderLayout.SOUTH);

        detenerBoton = new JButton("Detener");
        iniciarBoton = new JButton("Iniciar");

        panelBotones.add(iniciarBoton);
        panelBotones.add(detenerBoton);

        iniciarBoton.addActionListener(e -> {
            CirculoAnimado c = new CirculoAnimado(panel);
            circulos.add(c);
            Thread hilo = new Thread(c);
            hilos.add(hilo);
            hilo.start();
        });

        detenerBoton.addActionListener(e -> {
            for (Thread h : hilos) {
                h.interrupt();
            }
            hilos.clear();
            circulos.clear();
            panel.repaint();
        });
    }

    public class PanelDibujo extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);

            synchronized(circulos) {
                for (CirculoAnimado c : circulos) {
                    int tamano = c.getTamano();
                    g.drawOval(getWidth()/2 - (tamano/2), getHeight()/2 - (tamano/2), tamano, tamano);
                }
            }
            repaint();
        }
    }

    public static void main(String args[]) {
        new CirculosAnimados().setVisible(true);
    }
}

class CirculoAnimado implements Runnable {
    private int tamano;
    private JPanel panel;

    public CirculoAnimado(JPanel panel) {
        this.panel = panel;
        tamano = 10;
    }

    public int getTamano() {
        return tamano;
    }

    @Override
    public void run() {
        animar();
    }

    public void animar() {
        int inc = 1;
        tamano = 10;

        while (!Thread.currentThread().interrupted()) {
            try {

                Thread.sleep(10);

                if (tamano < 10 || tamano > panel.getHeight()) {
                    inc = -inc;
                }
                tamano += inc;

            } catch (InterruptedException ex) {
                // EXPLICACIÓN DE LA ACTIVIDAD:

                Thread.currentThread().interrupt();
                System.out.println("Hilo interrumpido mientras dormia. Finalizando...");
            }
        }
    }
}