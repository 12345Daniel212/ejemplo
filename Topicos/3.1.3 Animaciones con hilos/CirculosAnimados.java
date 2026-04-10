import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class CirculosAnimados extends JFrame {

   //Botones para la ventana
   private JButton iniciarBoton;
   private JButton detenerBoton;

   //Panel para dibujar añadido al centro de la ventana
   PanelDibujo panel;

   //Lista de circulos
   private ArrayList<CirculoAnimado> circulos = new ArrayList<CirculoAnimado>();

   public CirculosAnimados() {
      inicializarComponentes();
   }

   public void inicializarComponentes(){
      //Configuración inicial del JFrame
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400, 400);
      setTitle("Hilos");
      setLayout(new BorderLayout());

      //Panel para dibujar añadido al centro de la ventana
      panel = new PanelDibujo();
      add(panel, BorderLayout.CENTER);

      //Panel para los botones añadido al sur de la ventana
      JPanel panelBotones = new JPanel();
      add(panelBotones, BorderLayout.SOUTH);

      //Creación de botones
      detenerBoton = new JButton("Detener");
      iniciarBoton = new JButton("Iniciar");

      //Botones añadidios al panel inferior
      panelBotones.add(iniciarBoton);
      panelBotones.add(detenerBoton);

      iniciarBoton.addActionListener(e -> {

        CirculoAnimado c = new CirculoAnimado(panel);
            circulos.add(c);

            Thread hilo = new Thread(c);

            hilo.start();
         });

      detenerBoton.addActionListener(e -> {
          circulos.clear();
          panel.repaint(); // Limpiar el panel al detener la animación
        });
   }

      //Clase anidada PanelDibujo
   public class PanelDibujo extends JPanel{
      @Override
      public void paintComponent(Graphics g){
         super.paintComponent(g);

         //Se define el color del círculo
         g.setColor(Color.RED);

         //Se recorre todo el arreglo de círculos, y se dibuja cada uno de éstos
         for (int i = 0; i < circulos.size(); i ++){
            int tamano = circulos.get(i).getTamano();
            g.drawOval(this.getWidth()/2-(tamano/2), this.getHeight()/2-(tamano/2), tamano, tamano);
         }

         //Agregar aquí la llamada al método repaint() una vez que hayas añadido hilos.
         repaint(); // Esto hará que el panel se vuelva a dibujar cada vez que se actualice el tamaño de los círculos

      }
   }//Fin clase PanelDibujo

   public static void main(String args[]){
      new CirculosAnimados().setVisible(true);
   }
}


//////////////////////////////////////////////////////////////////////////
// Clase modificada para implementar Runnable
class CirculoAnimado implements Runnable {

   //Variable para el tamaño del círculo
   private int tamano;

   //Panel donde se dibuja el cículo
   private JPanel panel;

   //Constructor del círculo
   public CirculoAnimado(JPanel panel){
      this.panel = panel;
      tamano = 10;
   }

   //Método que retorna el tamaño de la circunferencia del círculo
   public int getTamano(){
      return tamano;
   }
   // El método run es el punto de entrada para el hilo
   @Override
   public void run() {
       animar();
   }

   public void animar(){
      //incremento del tamaño del círculo
      int inc = 1;

      //Tamaño inicial del círculo
      tamano = 10;

      //Se realiza la animación mediante el ciclo while infinito
      while (true){
         // Esto es más seguro y eficiente en aplicaciones gráficas
         //panel.repaint();

         try {
             Thread.sleep(2); // Aumenté a 10ms para que sea más fluido y menos pesado
         } catch(Exception ex) {
             break; // Si el hilo se interrumpe, salimos del bucle
         }

         if (tamano < 10 || tamano > panel.getHeight()){
            inc = -inc;
         }

         tamano += inc;
      }
   }
}