import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class gym {

    private String musculo;

    public void preguntarMusculo() {
        System.out.println("¿Qué músculo quieres trabajar? (pecho, espalda, piernas, hombros, biceps, triceps, abdominales)");
        musculo = System.console().readLine().toLowerCase();
    }

    // Este método recibe la línea "sucia" y la limpia para mostrarla bien
    public void imprimirEjercicio(String linea) {
        String limpio = linea.replace("\"", "")    // Quita comillas
                             .replace("[", "")     // Quita inicio de array
                             .replace("]", "")     // Quita fin de array
                             .replace(",", "")     // Quita comas
                             .replace("exercise:", "") // Quita la etiqueta si existe
                             .trim();              // Quita espacios sobrantes

        if (!limpio.isEmpty()) {
            System.out.println(" - " + limpio);
        }
    }

    public void leerJson() {
    try {
        List<String> lineas = Files.readAllLines(Paths.get("exersice.json"));
        boolean imprimiendo = false;

        for (String linea : lineas) {

            // 1. ¿Esta línea tiene el músculo que busco?
            if (linea.contains("\"musculo\": \"" + musculo + "\"")) {
                imprimiendo = true; // ¡Lo encontré! Empiezo a imprimir lo que sigue
                System.out.println("\nEjercicios para " + musculo.toUpperCase() + ":");
            }

            // 2. Si la luz está encendida, imprimo la línea
            else if (imprimiendo) {
                // Si llegamos al final del bloque de este músculo, apagamos la luz
                if (linea.contains("}")) {
                    imprimiendo = false;
                } else {
                    imprimirEjercicio(linea); // Mandamos la línea a limpiar e imprimir
                }
            }
        }
       } catch (Exception e) {
         System.out.println("Error al leer el archivo");
      }
    }

    public static void main(String[] args) {
        gym miGym = new gym();
        miGym.preguntarMusculo();
        miGym.leerJson();
    }
}