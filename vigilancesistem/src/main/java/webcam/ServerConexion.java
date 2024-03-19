package webcam;

import java.io.OutputStream;
import java.net.Socket;

public class ServerConexion {

    final String SERVER_IP = "127.0.0.1";
    final int SERVER_PORT = 8888;

    Socket socket;

    public ServerConexion() {
        try {
            // Socket bat sortu klasea sortzean
            socket = new Socket(SERVER_IP, SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo honek irudi bat tcp bidez bidaltzen du
     * 
     * @param imageData irudia byte array bezala formateatuta
     */
    public void sendImage(byte[] imageData) {
        try {

            // Datuak bidaltzeko OutputStream motako aldagaila sortu
            OutputStream outputStream = socket.getOutputStream();

            // Irudia byte moduan bidali
            outputStream.write(imageData);

            // OutputStream itxi
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}