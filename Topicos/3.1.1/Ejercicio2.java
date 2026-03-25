import java.util.Random;

public class Ejercicio2 {
    public static void main(String[] args) {
        int[] a1 = new int[30000];
        int[] a2 = new int[30000];
        Random r = new Random();

        for (int i = 0; i < a1.length; i++) {
            a1[i] = r.nextInt(100000);
        }
        System.arraycopy(a1, 0, a2, 0, a1.length);

        Thread h1 = new Thread(() -> {
            burbuja(a1);
            System.out.println("Algoritmo de la burbuja");
        });

        Thread h2 = new Thread(() -> {
            quickSort(a2, 0, a2.length - 1);
            System.out.println("Algoritmo quicksort");
        });

        h1.start();
        h2.start();
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

    public static void quickSort(int[] array, int i, int j) {
        int pivote = array[i];
        int inicio = i;
        int fin = j;
        do {
            while (array[i] < pivote) i++;
            while (array[j] > pivote) j--;
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        } while (i <= j);
        if (inicio < j) quickSort(array, inicio, j);
        if (i < fin) quickSort(array, i, fin);
    }
}