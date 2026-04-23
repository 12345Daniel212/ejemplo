import java.net.URI;
import java.util.Scanner;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class equipos{
    private static String equipoSelected;
    @SuppressWarnings("UseSpecificCatch")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¡Bienvenido a la API de Deportes! Vamos a obtener información sobre un equipo de cualquier liga de fútbol.");
        System.out.print("Por favor, ingresa el nombre del equipo que quieres consultar (ejemplo: Arsenal, Chelsea, Liverpool): ");
        equipoSelected = scanner.nextLine();
        // La URL se construye con tu llave y el equipo seleccionado de la premier league
        String url = "https://www.thesportsdb.com/api/v1/json/123/searchteams.php?t=" + equipoSelected;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        System.out.println("Conectando con la API para ver la información del equipo " + equipoSelected + "...");

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("\n--- INFORMACIÓN DEL EQUIPO ---");
                System.out.println(response.body());
                System.out.println("---------------------");
                System.out.println("\nSi ves un texto con llaves {}, ¡felicidades! Has recibido la información del equipo en formato JSON.");
            } else {
                System.out.println("Error del servidor: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("Error de red: " + e.getMessage());
        }
    }
}