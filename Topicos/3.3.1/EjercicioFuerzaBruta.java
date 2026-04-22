import java.util.Scanner;

/**
 * Hilo que intenta adivinar una contraseña de seis dígitos
 * metida por el usuario[cite: 14].
 */
class HiloFuerzaBruta extends Thread {
    private String passwordObjetivo;

    public HiloFuerzaBruta(String passwordObjetivo) {
        this.passwordObjetivo = passwordObjetivo;
    }

    @Override
    public void run() {
        System.out.println("\n[Hilo]: Iniciando búsqueda por fuerza bruta...");
        long tiempoInicio = System.currentTimeMillis();

        // Itera tratando todas las combinaciones posibles de 6 dígitos [cite: 14]
        for (int i = 0; i <= 999999; i++) {
            // Formatea el número para asegurar que siempre tenga 6 caracteres (ej. 000123)
            String intento = String.format("%06d", i);

            if (intento.equals(passwordObjetivo)) {
                long tiempoFin = System.currentTimeMillis();
                System.out.println("[Hilo]: ¡CONTRASEÑA ENCONTRADA!: " + intento);
                System.out.println("[Hilo]: Tiempo de ejecución: " + (tiempoFin - tiempoInicio) + " ms");
                return;
            }
        }
        System.out.println("[Hilo]: No se pudo encontrar la contraseña.");
    }
}

public class EjercicioFuerzaBruta {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        String entrada;

        // Validación de entrada para asegurar que sean 6 dígitos
        while (true) {
            System.out.print("Introduce una contraseña de 6 dígitos (ej. 123456): ");
            entrada = lector.nextLine();

            if (entrada.matches("\\d{6}")) {
                break;
            } else {
                System.out.println("Error: Debe ingresar exactamente 6 números.");
            }
        }

        System.out.println("Sistema: Contraseña capturada. Iniciando hilo de descifrado...");

        // Crear e iniciar el hilo usando la clase Thread
        HiloFuerzaBruta h1 = new HiloFuerzaBruta(entrada);
        h1.start();

        lector.close();
    }
}