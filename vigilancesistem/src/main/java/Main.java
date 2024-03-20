import webcam.Camera;
import webcam.DependeciLoader;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        try {
            // Sistema erabile komprobatu eta oren arabela clase fitxategia kargatu
            String osName = System.getProperty("os.name").toLowerCase();

            if ( osName.startsWith("lin")){
                DependeciLoader.linuxCreateLibrary();
            } else if (osName.startsWith("win")) {
                DependeciLoader.windowsLoadDependecie();
            } else {
                throw new RuntimeException("Sistema erabilgarriak soilik Windows eta Linux dira");
                
            }
             

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
