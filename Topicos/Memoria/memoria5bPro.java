import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class memoria5bPro extends JFrame {

    private JButton primeraTarjeta;
    private int errores = 0;
    private int aciertos = 0;
    private int segundosTranscurridos = 0;
    private boolean esperando = true;
    private JLabel lblTimer;
    private Timer cronometro;
    private JButton[] tarjetasBtn = new JButton[16];

    public memoria5bPro() {
        inializarComponentes();
        animacionInicial();
    }

    private void inializarComponentes() {
        setTitle("Memoria Pro - Chivas Edition");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(25, 25, 25));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Superior
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        panelInfo.setBackground(new Color(40, 40, 40));
        lblTimer = new JLabel("¡PREPÁRATE!");
        lblTimer.setFont(new Font("Monospaced", Font.BOLD, 25));
        lblTimer.setForeground(Color.YELLOW);
        panelInfo.add(lblTimer);
        add(panelInfo, BorderLayout.NORTH);

        // Panel de Juego
        JPanel panelJuego = new JPanel(new GridLayout(4, 4, 15, 15));
        panelJuego.setBackground(new Color(25, 25, 25));
        panelJuego.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < tarjetasBtn.length; i++) {
            tarjetasBtn[i] = new JButton();
            tarjetasBtn[i].setFont(new Font("SansSerif", Font.BOLD, 18));
            tarjetasBtn[i].setFocusPainted(false);
            tarjetasBtn[i].setBackground(new Color(50, 50, 50));
            tarjetasBtn[i].setForeground(Color.WHITE);
            tarjetasBtn[i].setBorder(new LineBorder(new Color(70, 70, 70), 2));
            tarjetasBtn[i].setCursor(new Cursor(Cursor.HAND_CURSOR));

            tarjetasBtn[i].addActionListener(this::tarjetaClick);
            panelJuego.add(tarjetasBtn[i]);
        }
        add(panelJuego, BorderLayout.CENTER);

        // Llenado de datos
        String[] nombres = {"Tala Rangel", "Armando Gonzales", "Oso Gonzales", "Daniel Aguirre",
                            "Omar Govea", "Piojo Alvarado", "Ledezma", "Efrain Alvarez"};
        Random rand = new Random();
        for (String n : nombres) {
            for (int j = 0; j < 2; j++) {
                int index;
                do { index = rand.nextInt(16); } while (tarjetasBtn[index].getClientProperty("valor") != null);

                // GUARDAMOS EL TEXTO EN SECRETO
                tarjetasBtn[index].putClientProperty("valor", n);
                tarjetasBtn[index].setText(n); // Lo mostramos solo para la animación inicial
            }
        }
    }

    private void animacionInicial() {
        // 2 segundos para ver las respuestas
        Timer timerInicio = new Timer(2000, e -> {
            for (JButton btn : tarjetasBtn) {
                btn.setText(""); // BORRAMOS EL TEXTO (Se dan la vuelta)
                btn.setBackground(new Color(70, 70, 70)); // Color de "reverso"
            }
            esperando = false;
            iniciarCronometro();
        });
        timerInicio.setRepeats(false);
        timerInicio.start();
    }

    private void iniciarCronometro() {
        cronometro = new Timer(1000, e -> {
            segundosTranscurridos++;
            lblTimer.setForeground(Color.CYAN);
            lblTimer.setText(String.format("Tiempo: %02d:%02d", segundosTranscurridos/60, segundosTranscurridos%60));
        });
        cronometro.start();
    }

    private void tarjetaClick(ActionEvent evento) {
        if (esperando) return;
        JButton tarjetaActiva = (JButton) evento.getSource();
        if (tarjetaActiva == primeraTarjeta || !tarjetaActiva.isEnabled()) return;

        // "VOLTEAR" CARTA (Recuperar el texto secreto)
        String contenido = (String) tarjetaActiva.getClientProperty("valor");
        tarjetaActiva.setText(contenido);
        tarjetaActiva.setBackground(new Color(180, 0, 0)); // Rojo al mostrar

        if (primeraTarjeta == null) {
            primeraTarjeta = tarjetaActiva;
        } else {
            if (primeraTarjeta.getText().equals(tarjetaActiva.getText())) {
                // ACIERTO
                aciertos++;
                tarjetaActiva.setBackground(new Color(40, 150, 40));
                primeraTarjeta.setBackground(new Color(40, 150, 40));
                primeraTarjeta.setEnabled(false);
                tarjetaActiva.setEnabled(false);
                primeraTarjeta = null;
                if (aciertos == 8) {
                    cronometro.stop();
                    JOptionPane.showMessageDialog(this, "¡GANASTE!\nErrores: " + errores);
                }
            } else {
                // ERROR
                esperando = true;
                errores++;
                System.out.println("Errores: " + errores);
                tarjetaActiva.setBackground(Color.RED);

                Timer timerOcultar = new Timer(800, e -> {
                    primeraTarjeta.setText(""); // VOLVER A OCULTAR
                    tarjetaActiva.setText("");  // VOLVER A OCULTAR
                    primeraTarjeta.setBackground(new Color(70, 70, 70));
                    tarjetaActiva.setBackground(new Color(70, 70, 70));
                    primeraTarjeta = null;
                    esperando = false;
                });
                timerOcultar.setRepeats(false);
                timerOcultar.start();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new memoria5bPro().setVisible(true));
    }
}