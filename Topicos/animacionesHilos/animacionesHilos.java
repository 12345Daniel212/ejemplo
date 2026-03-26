import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class animacionesHilos extends JFrame {

    private JPanel panelDibujo;
    private ArrayList<Pelota> pelotas = new ArrayList<>();

    public animacionesHilos() {
        initComponents();
    }

    public void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Animaciones con Hilos Multi-Color");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        JPanel botones = new JPanel(new FlowLayout());
        this.add(botones, BorderLayout.SOUTH);

        JButton btnAnimacion1 = new JButton("Agregar Pelota");
        botones.add(btnAnimacion1);

        panelDibujo = new PanelDibujo();
        panelDibujo.setBackground(Color.BLACK);
        this.add(panelDibujo, BorderLayout.CENTER);

        btnAnimacion1.addActionListener(e -> {
            Pelota pelota = new Pelota(panelDibujo);
            pelotas.add(pelota);
            Thread hiloPelota = new Thread(pelota);
            hiloPelota.start();
        });
    }

    // El panel ahora usa el color propio de cada pelota
    class PanelDibujo extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujamos cada pelota con su color y posición actual
            for (int i = 0; i < pelotas.size(); i++) {
                Pelota p = pelotas.get(i);
                g.setColor(p.color);
                g.fillOval(p.x, p.y, 20, 20);
            }
        }
    }

    public static void main(String[] args) {
        // Ejecución segura en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new animacionesHilos().setVisible(true));
    }

    class Pelota implements Runnable {
        public int x, y;
        private int dx, dy;
        public Color color;
        private JPanel panel;

        public Pelota(JPanel panel) {
            this.panel = panel;
            Random rand = new Random();

            // Posición inicial aleatoria para que no salgan todas del mismo punto
            this.x = rand.nextInt(300);
            this.y = rand.nextInt(200);

            // Velocidad aleatoria entre 2 y 5
            this.dx = rand.nextInt(3) + 2;
            this.dy = rand.nextInt(3) + 2;

            // Color aleatorio guardado en el objeto
            this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }

        @Override
        public void run() {
            while (true) {
                x += dx;
                y += dy;

                // Rebotar en bordes
                if (x < 0 || x > panel.getWidth() - 20) dx = -dx;
                if (y < 0 || y > panel.getHeight() - 20) dy = -dy;

                panel.repaint();

                try {
                    Thread.sleep(5); // 15ms es ideal para 60fps aprox.
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}