package webcam;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.awt.EventQueue;

public class Camera {

    ServerConexion serverConexion = new ServerConexion();

    private VideoCapture capture;
    private Mat image;

    /**
     * Metodo honek kamera bat hasi eta irudiak bidaltzen ditu tcp bidez
     */
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
            serverConexion.sendImage(imageData);

        }
    }

}