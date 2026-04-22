import java.util.Random;

public class Ejercicio2_Thread {

    // Clase para el hilo de Burbuja
    static class HiloBurbuja extends Thread {
        private int[] array;
        public HiloBurbuja(int[] array) { this.array = array; }

        @Override
        public void run() {
            burbuja(array);
            System.out.println("Algoritmo de la burbuja finalizado.");
        }
    }

    // Clase para el hilo de QuickSort
    static class HiloQuickSort extends Thread {
        private int[] array;
        public HiloQuickSort(int[] array) { this.array = array; }

        @Override
        public void run() {
            quickSort(array, 0, array.length - 1);
            System.out.println("Algoritmo QuickSort finalizado.");
        }
    }

    public static void main(String[] args) {
        int[] a1 = new int[30000];
        int[] a2 = new int[30000];
        Random r = new Random();

        for (int i = 0; i < a1.length; i++) {
            a1[i] = r.nextInt(100000);
        }
        System.arraycopy(a1, 0, a2, 0, a1.length);

        // Instanciación de las clases que heredan de Thread
        new HiloBurbuja(a1).start();
        new HiloQuickSort(a2).start();
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
        if (i >= j) return;
        int pivote = array[i], inicio = i, fin = j;
        do {
            while (array[i] < pivote) i++;
            while (array[j] > pivote) j--;
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++; j--;
            }
        } while (i <= j);
        if (inicio < j) quickSort(array, inicio, j);
        if (i < fin) quickSort(array, i, fin);
    }
}