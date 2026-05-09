import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClimaJava {
    public static void main(String[] args) {
        // TU LLAVE DE WEATHERAPI
        String apiKey = "f1ad5dfda7c0499cab0201203261104";
        String ciudad = "Tepic"; // Puedes cambiarla por Xalisco si gustas

        // La URL se construye con tu llave y la ciudad
        String url = "https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + ciudad + "&lang=es";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        System.out.println("Conectando con la API para ver el clima de " + ciudad + "...");

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("\n--- CLIMA ACTUAL ---");
                System.out.println(response.body());
                System.out.println("---------------------");
                System.out.println("\nSi ves un texto con llaves {}, ¡felicidades! Ya conectaste tu primer API.");
            } else {
                System.out.println("Error del servidor: " + response.statusCode());
                System.out.println("Verifica que tu API Key esté activa en el dashboard de WeatherAPI.");
            }

        } catch (Exception e) {
            System.err.println("Error de red: " + e.getMessage());
        }
    }
}