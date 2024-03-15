package webcam;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends JFrame {

    private JLabel cameraScreen;

    private JButton btnCapture;

    private VideoCapture capture;
    private Mat image;

    private boolean cliked = false;

    public Camera() {
        // desing UI
        setLayout(null);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        btnCapture = new JButton("Capture");
        btnCapture.setBounds(300, 480, 80, 40);
        add(btnCapture);

        btnCapture.addActionListener(e -> {
            cliked = true;
        });

        setSize(640, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // create camera
    public void startCamera() {
        capture = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        while (true) {
            // read image to matrix
            capture.read(image);

            // convert matrix to byte array
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            imageData = buf.toArray();
            // add to JLabel
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
            // capturar y guardar en fichero

            if (cliked) {
                String name = JOptionPane.showInputDialog(this, "Mete el nombre de la imagen");
                if (name == null) {
                    name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                }
                // Convertir la imagen a un fichero
                Imgcodecs.imwrite("./src/main/resources/irudiak/" + name + ".jpg", image);
                cliked = false;
            }

        }
    }

    public static void main(String[] args) {
        // opencv library kargatu
        ClassLoader classLoader = Camera.class.getClassLoader();
        String filePath = classLoader.getResource("opencv_java320.dll").getPath();
        System.load(filePath);

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Camera camera = new Camera();
                // iniciar camara en un nuevo hilo
                new Thread(() -> {
                    camera.startCamera();
                }).start();
            }
        });
    }

}