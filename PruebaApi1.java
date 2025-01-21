import java.util.Scanner;
import java.io.IOException;
import java.net.*;


//API ELDEN RING JEFES: https://eldenring.fanapis.com/api/bosses?name=Malenia
//API ELDEN RING CENIZAS DE GUERRA: https://eldenring.fanapis.com/api/ashes?name=Ash%20Of%20War:%20Prelate%27s%20Charge
//API RICK AND MORTY: https://rickandmortyapi.com/api/character/306
//API POKEMON: https://pokeapi.co/api/v2/pokemon/1




public class PruebaApi1 {
    public static void main(String[] args) {
        Scanner sc2 = new Scanner(System.in);

        //Ingresar el nombre del jefe que se desea buscar
        System.out.print("Introduce el nombre del jefe que deseas buscar: ");
        String jefe = sc2.nextLine();
        sc2.close();

        try{
            URL url = new URL ("https://eldenring.fanapis.com/api/bosses?name="+jefe);//Crea un objeto URL con la dirección de la API y se concatena con el nombre del jefe que se desea buscar
            //objeto URL con la dirección de la API
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();//Abre la conexión
            conexion.setRequestMethod("GET");//Establece el método de petición
            conexion.connect();//Conecta con la API

            System.out.println("Código de respuesta: " + conexion.getResponseCode());//Imprime el código de respuesta
            int codigo = conexion.getResponseCode();//Guarda el código de respuesta en una variable

            if (codigo != 200) {
                throw new RuntimeException("Error al realizar la petición, Status de conexion: " + codigo);//Manejo de errores por petición fallida
            }else{
                System.out.println("Conexión exitosa Status de conexion: " +codigo);//Imprime mensaje de conexión exitosa
                Scanner sc = new Scanner(url.openStream());//Crea un objeto Scanner para leer la respuesta de la API
                String respuesta = new String();//Crea una cadena para guardar la respuesta
                while(sc.hasNext()){
                    respuesta += sc.nextLine();//Lee la respuesta de la API
                }
                sc.close();//Cierra el objeto Scanner

                System.out.println(respuesta);//Imprime la respuesta de la API
            }

        }catch(MalformedURLException e){
            throw new RuntimeException("Error al conectarse a la API", e);//Manejo de errores por conexión fallida
        } catch (IOException e) {//Manejo de errores por entrada/salida
            // TODO Auto-generated catch block
            e.printStackTrace();//Imprime el error
        }
        
    }
}