import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.json.JSONObject;

public class PruebaApi2 {
    public static void main(String[] args) {
        HttpURLConnection conexion = null;
        Scanner sc = null;
        Scanner sc2 = new Scanner(System.in);

        // Ingresar el ID del personaje (en lugar del nombre)
        System.out.print("Introduce el ID del personaje que deseas buscar: ");
        int personajeId = sc2.nextInt(); // Asumimos que el usuario ingresa el ID del personaje
        sc2.close();

        try {
            // URL de la API con el ID del personaje
            URL url = new URL("https://rickandmortyapi.com/api/character/" + personajeId);

            // Abre la conexión
            conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET"); // Método de solicitud GET
            conexion.connect(); // Conecta a la API

            // Verifica el código de respuesta
            int codigo = conexion.getResponseCode();
            System.out.println("Código de respuesta: " + codigo);

            // Si la respuesta es exitosa (código 200)
            if (codigo == 200) {
                sc = new Scanner(url.openStream()); // Escanea la respuesta
                StringBuilder respuesta = new StringBuilder();

                while (sc.hasNext()) {
                    respuesta.append(sc.nextLine());
                }
                sc.close(); // Cierra el scanner

                // Convierte la respuesta JSON a un objeto JSONObject
                JSONObject jsonResponse = new JSONObject(respuesta.toString());

                // Extrae los campos específicos del JSON
                String nombre = jsonResponse.getString("name"); // Nombre del personaje
                String estatus = jsonResponse.getString("status"); // Estatus del personaje
                String especie = jsonResponse.getString("species"); // Especie del personaje
                String genero = jsonResponse.getString("gender"); // Género del personaje
                String imagenUrl = jsonResponse.getString("image"); // URL de la imagen

                // Imprime solo los datos seleccionados
                System.out.println("Nombre: " + nombre);
                System.out.println("Estatus: " + estatus);
                System.out.println("Especie: " + especie);
                System.out.println("Género: " + genero);
                System.out.println("URL de la imagen: " + imagenUrl);

                // Mostrar la imagen en una ventana
                showImage(imagenUrl);

            } else {
                System.out.println("Error en la solicitud, código de respuesta: " + codigo);
            }

        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores generales
        } finally {
            if (conexion != null) {
                conexion.disconnect(); // Cierra la conexión
            }
        }
    }

    // Método para mostrar la imagen en una ventana JFrame
    private static void showImage(String imageUrl) {
        try {
            // Cargar la imagen desde la URL
            URL url = new URL(imageUrl);
            ImageIcon imageIcon = new ImageIcon(url);

            // Crear una ventana para mostrar la imagen
            JFrame frame = new JFrame("Imagen del Personaje");
            JLabel label = new JLabel(imageIcon);
            frame.add(label);
            frame.setSize(600, 600);  // Tamaño de la ventana
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
