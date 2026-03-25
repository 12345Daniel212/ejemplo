import java.util.Random;

class SumadorThread extends Thread {
    private int[] subArreglo;
    private int id;

    public SumadorThread(int[] subArreglo, int id) {
        this.subArreglo = subArreglo;
        this.id = id;
    }

    @Override
    public void run() {
        long suma = 0;
        for (int num : subArreglo) {
            suma += num;
        }
        System.out.println("Hilo " + id + " finalizado. Sumatoria: " + suma);
    }
}

public class EjercicioHilos1 {
    public static void main(String[] args) {
        int[] original = new int[50000];
        Random rnd = new Random();

        for (int i = 0; i < original.length; i++) {
            original[i] = rnd.nextInt(101);
        }

        int tamañoParte = 10000;
        for (int i = 0; i < 5; i++) {
            int[] destino = new int[tamañoParte];
            System.arraycopy(original, i * tamañoParte, destino, 0, tamañoParte);

            SumadorThread hilo = new SumadorThread(destino, i + 1);
            hilo.start();
        }
    }
}