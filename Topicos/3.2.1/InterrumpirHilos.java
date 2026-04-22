import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

class InterrumpirHilos extends JFrame {

   private List<Thread> listaHilos = new ArrayList<>();

   public InterrumpirHilos() {
      this.setSize(250, 200);
      this.setTitle("Control de Hilos");
      this.setLayout(new FlowLayout());
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JButton iniciarBoton = new JButton("Iniciar hilo");
      this.add(iniciarBoton);

      JButton detenerBoton = new JButton("Detener todos");
      this.add(detenerBoton);

      iniciarBoton.addActionListener(e -> {
            Hilo h = new Hilo();
            Thread t = new Thread(h);
            listaHilos.add(t);
            t.start();
         }
      );

      // Evento para interrumpir todos los hilos en la lista
      detenerBoton.addActionListener(e -> {
            for (Thread t : listaHilos) {
                t.interrupt();
            }
            listaHilos.clear();
            System.out.println("Se ha enviado señal de parada a todos los hilos.");
         }
      );
   }

   public static void main(String args[]) {
      new InterrumpirHilos().setVisible(true);
   }
}

///////////////////////////////////////////////////////////////////////
class Hilo implements Runnable {

   private static int contadorGlobal = 0;
   private int numeroHilo;

   public Hilo() {
      contadorGlobal++;
      numeroHilo = contadorGlobal;
   }

   public int getNumeroHilo() {
       return numeroHilo;
   }

   @Override
   public void run() {
      while (!Thread.currentThread().isInterrupted()) {
         try {
            System.out.println("Hilo " + numeroHilo + " en ejecución...");
            Thread.sleep(500);
         } catch (InterruptedException e) {
            System.out.println("Hilo " + numeroHilo + " ha sido interrumpido.");
            Thread.currentThread().interrupt();
         }
      }
      System.out.println("Hilo " + numeroHilo + " finalizado con éxito.");
   }
}