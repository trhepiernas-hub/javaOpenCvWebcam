package webcam;
import java.io.OutputStream;
import java.net.Socket;

public class ServerConexion {

    final String SERVER_IP = "127.0.0.1";
    final int SERVER_PORT = 8888;

    public void sendImage(byte[] imageData) {
        try {
            // Crear un socket TCP
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
    
            // Obtener el OutputStream para enviar los datos
            OutputStream outputStream = socket.getOutputStream();
    
            // Enviar la imagen
            outputStream.write(imageData);
    
            // Cerrar el OutputStream y el socket
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}