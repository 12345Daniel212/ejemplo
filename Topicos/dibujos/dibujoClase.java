import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class dibujoClase extends JFrame {

    JButton circBtn;
    JButton cuadBtn;
    JButton azulBtn;
    JButton rojoBtn;
    JButton verdeBtn;
    JButton borrarBtn;
    int x, y;
    ArrayList<Point> circulos = new ArrayList<>();
    Color color = Color.BLACK;

    public dibujoClase() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        JPanel herramientasPnl = new JPanel(new GridLayout(6, 1, 10, 10));
        this.add(herramientasPnl, BorderLayout.WEST);

        circBtn = new JButton("Circulo");
        cuadBtn = new JButton("Cuadrado");
        azulBtn = new JButton("Azul");
        rojoBtn = new JButton("Rojo");
        verdeBtn = new JButton("Verde");
        borrarBtn = new JButton("Borrar");

        herramientasPnl.add(circBtn);
        herramientasPnl.add(cuadBtn);
        herramientasPnl.add(azulBtn);
        herramientasPnl.add(rojoBtn);
        herramientasPnl.add(verdeBtn);
        herramientasPnl.add(borrarBtn);

        PanelDibujo panelDibujo = new PanelDibujo();
        panelDibujo.setBackground(java.awt.Color.WHITE);
        this.add(panelDibujo, BorderLayout.CENTER);

        panelDibujo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                x = evt.getX();
                y = evt.getY();
                circulos.add(new Point(x, y));
                repaint();
            }
        });

        azulBtn.addActionListener(e -> color = Color.BLUE);
        rojoBtn.addActionListener(e -> color = Color.RED);
        verdeBtn.addActionListener(e -> color = Color.GREEN);
        borrarBtn.addActionListener(e -> {
            circulos.clear();
            repaint();
        });
    }

    class PanelDibujo extends JPanel {
        @Override
        protected void paintComponent(java.awt.Graphics a) {
            super.paintComponent(a);
            a.setColor(color);
            for(Point circulo : circulos) {
                a.fillRect((int) circulo.getX() - 25, (int) circulo.getY() - 25, 50, 50);
            }
        }
    }

    public static void main(String[] args) {
        new dibujoClase().setVisible(true);
    }
}