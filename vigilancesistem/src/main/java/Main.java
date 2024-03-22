import webcam.Camera;

import java.awt.EventQueue;

public class Main {
    
    public static void main(String[] args) {  
        try {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
