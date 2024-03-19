import webcam.Camera;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.core.Core;

public class Main {

    public static void main(String[] args) {
        try {
            // Sistema erabile komprobatu eta oren arabela clase fitxategia kargatu
            String osName = System.getProperty("os.name").toLowerCase();
            String opencvName = osName.startsWith("win") ? "opencv_java320.dll" : "libopencv_core.so.4.9.0";

            if (osName.startsWith("win")) {

                // libreria errekurtso bezala kargatu
                InputStream in = Main.class.getResourceAsStream("/" + opencvName);
                // artxibo temporala sortu eta libreria bertara kopiatu
                File tempLib = File.createTempFile("lib", opencvName);
                try (FileOutputStream out = new FileOutputStream(tempLib)) {
                    byte[] buffer = new byte[1024];
                    int readBytes;
                    while ((readBytes = in.read(buffer)) != -1) {
                        out.write(buffer, 0, readBytes);
                    }
                }
                // Libreria argatu artxibo temporaltik
                System.load(tempLib.getAbsolutePath());
            } else {
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
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
    }
}
