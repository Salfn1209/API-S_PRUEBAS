import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.json.JSONArray;
import org.json.JSONObject;

public class PruebaApi3 {
    public static void main(String[] args) {
        HttpURLConnection conexion = null;
        Scanner sc = null;

        Scanner sc2 = new Scanner(System.in);


        //Ingresar el nombre del jefe que se desea buscar
        System.out.print("Introduce el nombre del jefe que deseas buscar: ");
        String jefe = sc2.nextLine();
        sc2.close();

        try {
            // URL de la API de Elden Ring
            URL url = new URL("https://eldenring.fanapis.com/api/bosses?name=" + jefe);

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
                /*
                 * StringBuilder es una clase que permite construir cadenas de texto de forma eficiente, especialmente cuando se están concatenando muchas cadenas. 
                 * Usar StringBuilder es más eficiente que usar concatenación con el operador + cuando estás construyendo una cadena de texto de manera 
                dinámica.
                 */

                while (sc.hasNext()) {//Lee si hay tokens disponible aún y mientras haya seguira buscando 
                    respuesta.append(sc.nextLine());
                    /*
                     * agrega la línea leída al objeto StringBuilder respuesta. 
                     * Esto va concatenando todas las líneas del contenido de la URL a la variable respuesta.
                     */
                }

                sc.close(); // Cierra el scanner

                // Convierte la respuesta JSON a un objeto JSONObject
                JSONObject jsonResponse = new JSONObject(respuesta.toString());

                // Accede al arreglo "data"
                JSONArray dataArray = jsonResponse.getJSONArray("data");

                // Itera sobre el arreglo "data" y extrae los elementos de cada jefe
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject boss = dataArray.getJSONObject(i);

                    // Extrae los campos específicos de cada jefe
                    String id = boss.getString("id");
                    String nombre = boss.getString("name");
                    String region = boss.getString("region");
                    String location = boss.getString("location");
                    JSONArray dropsArray = boss.getJSONArray("drops"); // Array de recompensas
                    String drops = dropsArray.join(", "); // Convierte el arreglo en una cadena
                    String imagenUrl = boss.getString("image");

                    // Imprime los datos seleccionados para cada jefe
                    System.out.println("Id: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Región: " + region);
                    System.out.println("Ubicación: " + location);
                    System.out.println("Recompensas: " + drops);
                    System.out.println("URL de la imagen: " + imagenUrl);
                    System.out.println("--------------------------------------------------");

                    // Mostrar la imagen del jefe en una ventana
                    showImage(imagenUrl);
                }

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
            JFrame frame = new JFrame("Imagen del Jefe");
            JLabel label = new JLabel(imageIcon);
            frame.add(label);
            frame.setSize(600, 600);  // Tamaño de la ventana
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores si no se puede cargar la imagen
        }
    }
}
