package testServer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerControler {

    final int SERVER_PORT = 8888;

    private boolean cliked = false;

    private byte[] buffer = new byte[65508];

    private DatagramSocket socket;

    private JFrame frame = new JFrame("Server Controler");

    private JLabel cameraScreen;

    private JButton btnCapture;

    public ServerControler() {

        frame.setLayout(null);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        frame.add(cameraScreen);

        btnCapture = new JButton("Capture");
        btnCapture.setBounds(300, 480, 80, 40);
        frame.add(btnCapture);

        btnCapture.addActionListener(e -> {
            cliked = true;
        });

        frame.setSize(640, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        try {
            socket = new DatagramSocket(SERVER_PORT);
            // Cerrar el socket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImageIcon lortuIrudia() {
        try {
            DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
    
            // Ponerse a la escucha y recibir el paquete
            socket.receive(paquete);
    
            ByteArrayInputStream bais = new ByteArrayInputStream(paquete.getData());
            BufferedImage imagen = ImageIO.read(bais);
    
            // Comprobar si la imagen es nula antes de crear el ImageIcon
            if (imagen != null) {
                return new ImageIcon(imagen);
            } else {
                System.err.println("La imagen recibida es nula");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        ServerControler irudiak = new ServerControler();

        while (true) {
            ImageIcon icon = irudiak.lortuIrudia();
            irudiak.cameraScreen.setIcon(icon);
        }
    }
}