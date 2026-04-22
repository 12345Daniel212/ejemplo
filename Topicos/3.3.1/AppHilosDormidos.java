import java.awt.*;
import javax.swing.*;

/**
 * Aplicación que demuestra cómo interrumpir un hilo que está "dormido"
 * (Thread.sleep).
 * Basado en el comportamiento descrito en la presentación[cite: 1, 2].
 */
public class AppHilosDormidos extends JFrame {

  private Thread hiloTrabajador;
  private JLabel lblEstado;

  public AppHilosDormidos() {
    // Configuración básica de la ventana [cite: 11]
    setTitle("Interrumpir Hilos Dormidos");
    setSize(400, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new FlowLayout());

    lblEstado = new JLabel("Estado: Hilo detenido");
    JButton btnIniciar = new JButton("Iniciar");
    JButton btnDetener = new JButton("Detener");

    // Acción para el botón Iniciar [cite: 10]
    btnIniciar.addActionListener(e -> iniciarProceso());

    // Acción para el botón Detener [cite: 10]
    btnDetener.addActionListener(e -> detenerProceso());

    add(lblEstado);
    add(btnIniciar);
    add(btnDetener);
  }

  private void iniciarProceso() {
    if (hiloTrabajador == null || !hiloTrabajador.isAlive()) {
      hiloTrabajador = new Thread(() -> {
        try {
          // El hilo corre mientras no sea interrumpido
          while (!Thread.currentThread().isInterrupted()) {
            actualizarEtiqueta("Hilo trabajando...");

            Thread.sleep(10000);
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          actualizarEtiqueta("¡Excepcion capturada! Deteniendo...");
        } finally {
          actualizarEtiqueta("Estado: Hilo finalizado");
        }
      });

      hiloTrabajador.start();
    }
  }

  private void detenerProceso() {
    if (hiloTrabajador != null) {
      hiloTrabajador.interrupt();
    }
  }

  private void actualizarEtiqueta(String texto) {
    SwingUtilities.invokeLater(() -> lblEstado.setText(texto));
    System.out.println(texto);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new AppHilosDormidos().setVisible(true);
    });
  }
}