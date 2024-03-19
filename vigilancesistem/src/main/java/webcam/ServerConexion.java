package webcam;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerConexion {

    final String SERVER_IP = "127.0.0.1";
    final int SERVER_PORT = 8888;

    

    public void sendImage(byte[] imageData) {
        try {
            // Crear un socket UDP
            DatagramSocket socket = new DatagramSocket();
    
            // Dividir la imagen en múltiples paquetes
            int packetSize = 65507;
            byte[] buffer = new byte[packetSize];
    
            for (int i = 0; i < imageData.length; i += packetSize) {
                int bytesLeft = imageData.length - i;
                int bufferSize = Math.min(bytesLeft, packetSize);
    
                // Copiar los bytes de la imagen al buffer
                System.arraycopy(imageData, i, buffer, 0, bufferSize);
    
                // Crear un paquete para enviar la imagen
                InetAddress direccionServidor = InetAddress.getByName(SERVER_IP);
                DatagramPacket paquete = new DatagramPacket(buffer, bufferSize, direccionServidor, SERVER_PORT);
    
                // Enviar el paquete
                socket.send(paquete);
            }
    
            // Cerrar el socket
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
