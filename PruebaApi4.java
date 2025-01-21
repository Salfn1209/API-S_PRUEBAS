import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;

public class PruebaApi4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);//Crea un objeto Scanner para leer la entrada del usuario

        System.out.println("Introduce el ID del personaje que deseas buscar: ");//Pide al usuario que ingrese el ID del personaje
        int personajeId = sc.nextInt();//Guarda el ID digitado en la variable personajeId
        sc.close();//Cierra el objeto Scanner

        try{
            URL url = new URL("https://rickandmortyapi.com/api/character/" + personajeId);//Crea un objeto URL con la dirección de la API y se concatena con el ID del personaje que se desea buscar
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();//Abre la conexión
            conexion.setRequestMethod("GET");//Establece el método de petición
            conexion.connect();//Conecta con la API

            System.out.println("Código de respuesta: " + conexion.getResponseCode());//Imprime el código de respuesta

            int statusConexion = conexion.getResponseCode();//Guarda el código de respuesta en una variable
            
            if (statusConexion != 200) {
                throw new RuntimeException("Error al realizar la petición, Status de conexion: " + statusConexion);//Manejo de errores por petición fallida
            }else{
                sc = new Scanner(url.openStream()); // Escanea la respuesta
                StringBuilder respuesta = new StringBuilder();//Crea un objeto StringBuilder para guardar la respuesta

                while (sc.hasNext()) {//Mientras haya una respuesta
                    respuesta.append(sc.nextLine());//Agrega la respuesta al objeto StringBuilder
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
            }


        }catch(IOException e){
            throw new RuntimeException("Error al conectarse a la API Codigo Status", e);//Manejo de errores por conexión fallida
        }
    }
}
