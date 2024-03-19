package webcam;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.awt.EventQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Camera{

    ServerConexion serverConexion = new ServerConexion();

    private VideoCapture capture;
    private Mat image;

    byte[] imageData;

    ExecutorService executor = Executors.newFixedThreadPool(5);

    public Camera() {
    }

    // create camera
    public void startCamera() {
        capture = new VideoCapture(0);
        image = new Mat();


        while (true) {
            // read image to matrix
            capture.read(image);

            // convert matrix to byte array
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            final byte[] imageData = buf.toArray();
            
            // send image to server
            executor.execute(() -> {
                serverConexion.sendImage(imageData);
            });
           
           
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