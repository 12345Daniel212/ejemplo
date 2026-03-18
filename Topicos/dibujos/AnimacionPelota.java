import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AnimacionPelota extends JPanel implements ActionListener {
    private int x = 0;
    private int y = 0;

    private int velX = 5;
    private int velY = 5;

    private final int DIAMETRO = 30;

    private Timer timer;

    public AnimacionPelota() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la pelota
        g.setColor(Color.RED);
        g.fillOval(x, y, DIAMETRO, DIAMETRO);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += velX;
        y += velY;

        if (x <= 0 || x >= getWidth() - DIAMETRO) {
            velX = -velX; // Invierte la dirección horizontal
        }

        if (y <= 0 || y >= getHeight() - DIAMETRO) {
            velY = -velY; // Invierte la dirección vertical
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Animación: Rebote en 2D");
        AnimacionPelota panel = new AnimacionPelota();

        ventana.add(panel);
        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}