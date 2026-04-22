import java.util.Random;

class TaquillaConSync implements Runnable {
    private int boletosDisponibles = 50;
    private final Random random = new Random();

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        int boletosAComprar = random.nextInt(4) + 1;
        realizarCompra(nombre, boletosAComprar);
    }

    private synchronized void realizarCompra(String nombre, int cantidad) {
        System.out.println(nombre + " está revisando disponibilidad para " + cantidad + " boletos...");

        if (boletosDisponibles >= cantidad) {
            System.out.println(nombre + " encontró boletos. Procesando pago...");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}

            boletosDisponibles -= cantidad;
            System.out.println("TICKET EMITIDO: " + nombre + " adquirió " + cantidad
                               + " boletos. Disponibles: " + boletosDisponibles);
        } else {
            System.out.println("LO SENTIMOS: " + nombre + " no pudo comprar. Boletos insuficientes (" + boletosDisponibles + ")");
        }
    }
}

public class VentaBoletosConSync {
    public static void main(String[] args) {
        TaquillaConSync taquilla = new TaquillaConSync();
        for (int i = 1; i <= 25; i++) {
            Thread t = new Thread(taquilla, "Cliente " + i);
            t.start();
        }
    }
}