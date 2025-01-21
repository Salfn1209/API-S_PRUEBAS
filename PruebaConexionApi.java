import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class PruebaConexionApi {
    public static void main(String[] args) {
        try{
            URL url = new URL("https://rickandmortyapi.com/api/character/1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//Abre la conexión
            conn.setRequestMethod("GET"); // Método de solicitud GET
            conn.connect();

            int status = conn.getResponseCode();

            if (status == 200) {
                System.out.println("Conexion exitosa, respuesta de conexion : "+status);
                System.out.println("Recuperando Información.......");

                System.out.println("-------------------------------------------------------------------------------------------");
                Scanner sc = new Scanner(url.openStream());//Crea un objeto Scanner para leer la respuesta de la API
                String respuesta = new String();//Crea una cadena para guardar la respuesta
                while(sc.hasNext()){
                    respuesta += sc.nextLine();//Lee la respuesta de la API
                }
                sc.close();//Cierra el objeto Scanner

                System.out.println(respuesta);//Imprime la respuesta de la API
                System.out.println("-------------------------------------------------------------------------------------------");
                System.out.println("Información recuperda exitosamente.......");

            }else{
                System.out.println("Error de conexion, respuesta de conexion: " +status);
            }

        }catch(MalformedURLException e){
            throw new RuntimeException ("Error en la conexión codigo de estatus: " + e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
