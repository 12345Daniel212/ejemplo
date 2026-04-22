import java.util.Random;

// Hereda directamente de Thread
class Sumador extends Thread {
  private int[] arreglo;
  private int id;

  public Sumador(int[] arreglo, int id) {
    this.arreglo = arreglo;
    this.id = id;
  }

  @Override
  public void run() {
    long suma = 0;
    for (int n : arreglo) {
      suma += n;
    }
    System.out.println("Sumatoria parte " + id + ": " + suma);
  }
}

public class Ejercicio1_Thread {
  public static void main(String[] args) {
    int[] datos = new int[50000];
    Random r = new Random();

    for (int i = 0; i < datos.length; i++) {
      datos[i] = r.nextInt(101);
    }

    int tam = 10000;
    for (int i = 0; i < 5; i++) {
      int[] parte = new int[tam];
      System.arraycopy(datos, i * tam, parte, 0, tam);
      // Se inicia como un objeto de la clase que extiende Thread
      new Sumador(parte, i + 1).start();
    }
  }
}