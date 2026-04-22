    import java.awt.Color;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class CarreraHilos extends JFrame {
    private JButton btnIniciar;
    private JLabel[] corredores;
    private boolean hayGanador = false;

    public CarreraHilos() {
        setTitle("3.1.4 Carrera de Hilos");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        btnIniciar = new JButton("Iniciar Carrera");
        btnIniciar.setBounds(225, 20, 150, 30);
        add(btnIniciar);

        int totalCorredores = 5;
        corredores = new JLabel[totalCorredores];

        for (int i = 0; i < totalCorredores; i++) {
            corredores[i] = new JLabel("Corredor " + (i + 1));
            corredores[i].setBounds(10, 80 + (i * 50), 100, 30);
            corredores[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(corredores[i]);
        }

        btnIniciar.addActionListener(e -> iniciarCarrera());
    }

    private void iniciarCarrera() {
        btnIniciar.setEnabled(false);
        hayGanador = false;

        for (int i = 0; i < corredores.length; i++) {
            corredores[i].setLocation(10, corredores[i].getY());
            new HiloCorredor(corredores[i], i + 1).start();
        }
    }

    class HiloCorredor extends Thread {
        private JLabel etiqueta;
        private int numero;
        private Random random = new Random();

        public HiloCorredor(JLabel etiqueta, int numero) {
            this.etiqueta = etiqueta;
            this.numero = numero;
        }

        @Override
        public void run() {
            for (int x = 10; x <= 480; x += 5) {
                etiqueta.setLocation(x, etiqueta.getY());
                try {
                    Thread.sleep(random.nextInt(100) + 10);
                } catch (InterruptedException e) {}
            }

            synchronized (CarreraHilos.this) {
                if (!hayGanador) {
                    hayGanador = true;
                    JOptionPane.showMessageDialog(null, "Ganador: Corredor " + numero);
                    btnIniciar.setEnabled(true);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarreraHilos().setVisible(true));
    }
}