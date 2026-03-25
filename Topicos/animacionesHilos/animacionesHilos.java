import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;

public class animacionesHilos extends JFrame {

    private JPanel panelDibujo;
    // Usamos una lista sincronizada para evitar errores de concurrencia al iterar
    private List<Pelota> pelotas = Collections.synchronizedList(new ArrayList<>());

    public animacionesHilos() {
        initComponents();
    }

    public void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Colisiones con Hilos");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        JPanel botones = new JPanel(new FlowLayout());
        this.add(botones, BorderLayout.SOUTH);

        JButton btnAgregar = new JButton("Agregar Pelota");
        botones.add(btnAgregar);

        panelDibujo = new PanelDibujo();
        panelDibujo.setBackground(Color.BLACK);
        this.add(panelDibujo, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> {
            Pelota pelota = new Pelota(panelDibujo, pelotas);
            pelotas.add(pelota);
            new Thread(pelota).start();
        });
    }

    class PanelDibujo extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Dibujamos de forma segura sincronizando la lista
            synchronized (pelotas) {
                for (Pelota p : pelotas) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, 20, 20);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new animacionesHilos().setVisible(true));
    }

    class Pelota implements Runnable {
        public int x, y;
        private int dx, dy;
        public Color color;
        private JPanel panel;
        private List<Pelota> listaPelotas;
        private final int RADIO = 20;

        public Pelota(JPanel panel, List<Pelota> lista) {
            this.panel = panel;
            this.listaPelotas = lista;
            Random rand = new Random();
            this.x = rand.nextInt(500);
            this.y = rand.nextInt(300);
            this.dx = rand.nextBoolean() ? 2 : -2;
            this.dy = rand.nextBoolean() ? 2 : -2;
            this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }

        private void verificarColisiones() {
            synchronized (listaPelotas) {
                for (Pelota otra : listaPelotas) {
                    if (otra == this) continue; // No chocar con uno mismo

                    // Distancia entre centros usando Pitágoras
                    double dx_dist = (this.x + 10) - (otra.x + 10);
                    double dy_dist = (this.y + 10) - (otra.y + 10);
                    double distancia = Math.sqrt(dx_dist * dx_dist + dy_dist * dy_dist);

                    if (distancia < RADIO) {
                        // Intercambio simple de dirección (rebote elástico básico)
                        this.dx = -this.dx;
                        this.dy = -this.dy;
                        otra.dx = -otra.dx;
                        otra.dy = -otra.dy;

                        // Pequeño ajuste para que no se queden "pegadas"
                        this.x += this.dx;
                        this.y += this.dy;
                    }
                }
            }
        }

        @Override
        public void run() {
            while (true) {
                x += dx;
                y += dy;

                // Rebote con paredes
                if (x < 0 || x > panel.getWidth() - RADIO) dx = -dx;
                if (y < 0 || y > panel.getHeight() - RADIO) dy = -dy;

                verificarColisiones();

                panel.repaint();

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}