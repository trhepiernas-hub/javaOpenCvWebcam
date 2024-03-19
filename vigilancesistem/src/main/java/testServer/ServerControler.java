package testServer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerControler {

    final int SERVER_PORT = 8888;

    private boolean cliked = false;

    private byte[] buffer = new byte[65508];

    private ServerSocket serverSocket;

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
            serverSocket = new ServerSocket(SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImageIcon lortuIrudia() {
        try {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            inputStream.read(buffer);
    
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            BufferedImage imagen = ImageIO.read(bais);
    
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