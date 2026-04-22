import java.util.Random;

class TaquillaSinSync implements Runnable {
    private int boletosDisponibles = 50;
    private final Random random = new Random();

    @Override
    public void run() {
        String nombre = Thread.currentThread().getName();
        int boletosAComprar = random.nextInt(4) + 1;

        System.out.println(nombre + " intenta comprar " + boletosAComprar + " boletos.");

        // Simulación de revisión y compra sin protección
        if (boletosDisponibles >= boletosAComprar) {
            System.out.println(nombre + " verificó: HAY boletos suficientes.");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}

            boletosDisponibles -= boletosAComprar;
            System.out.println("COMPRA EXITOSA: " + nombre + " compró " + boletosAComprar
                               + ". Restantes: " + boletosDisponibles);
        } else {
            System.out.println("VENTA FALLIDA: " + nombre + " no pudo comprar. Solo quedan: " + boletosDisponibles);
        }
    }
}

public class VentaBoletosSinSync {
    public static void main(String[] args) {
        TaquillaSinSync taquilla = new TaquillaSinSync();
        for (int i = 1; i <= 25; i++) {
            Thread t = new Thread(taquilla, "Cliente " + i);
            t.start();
        }
    }
}