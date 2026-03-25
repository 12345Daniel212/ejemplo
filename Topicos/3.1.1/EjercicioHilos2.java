import java.util.Random;

public class EjercicioHilos2 {

    public static void main(String[] args) {
        int[] arreglo1 = new int[30000];
        int[] arreglo2 = new int[30000];
        Random rnd = new Random();

        for (int i = 0; i < arreglo1.length; i++) {
            arreglo1[i] = rnd.nextInt(100000);
        }
        System.arraycopy(arreglo1, 0, arreglo2, 0, arreglo1.length);

        Thread hiloBurbuja = new Thread(() -> {
            burbuja(arreglo1);
            System.out.println("-> Algoritmo de Burbuja ha terminado.");
        });

        Thread hiloQuick = new Thread(() -> {
            quickSort(arreglo2, 0, arreglo2.length - 1);
            System.out.println("-> Algoritmo Quicksort ha terminado.");
        });

        hiloBurbuja.start();
        hiloQuick.start();
    }

    public static void burbuja(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static void quickSort(int[] array, int inicio, int fin) {
        int pivote = array[inicio];
        int i = inicio;
        int j = fin;

        do {
            while (array[i] < pivote)
                i++;
            while (array[j] > pivote)
                j--;

            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        } while (i <= j);

        if (inicio < j)
            quickSort(array, inicio, j);
        if (i < fin)
            quickSort(array, i, fin);
    }
}