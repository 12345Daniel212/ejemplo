public class TemporizadorPrueba extends JFrame{
  public static void main(String[] args){
    Runnable r1 = new Temporizador(5, 1);
    Thread t1 = new Thread(r1);
    t1.start();

    Runnable r2 = new Temporizador(10, 2);
    Thread t2 = new Thread(r2);
    t2.start();
  }
}
