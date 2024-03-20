package webcam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

public class DependeciLoader {
    
    public static void linuxCreateLibrary() {
        // Obtenemos la URL usando la clase DependeciLoader en lugar de getClass()
        URL dirUrl = DependeciLoader.class.getResource("/lib");

        // Crea el directorio 'lib' en la ubicación actual
        File libDir = new File("./lib");
        if (!libDir.exists()) {
            libDir.mkdir();
        }

        // Copia todos los archivos del directorio 'lib' del JAR al nuevo directorio 'lib'
        try (FileSystem fs = FileSystems.newFileSystem(dirUrl.toURI(), Collections.emptyMap())) {
            Path sourceDir = fs.getPath("/lib");
            Files.walk(sourceDir).forEach(sourcePath -> {
                try {
                    // Calcula la ruta de destino
                    Path targetPath = Paths.get("./lib", sourcePath.toString());

                    // Crea los directorios padre si no existen
                    Files.createDirectories(targetPath.getParent());

                    // Copia el archivo
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                    if (Files.isRegularFile(targetPath) && sourcePath.toString().contains(".so")) {
                        System.load(targetPath.toAbsolutePath().toString());
                    }
                
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void windowsLoadDependecie () {
        // libreria errekurtso bezala kargatu
        try {
            InputStream in = DependeciLoader.class.getResourceAsStream("/opencv_java490.dll");
            // artxibo temporala sortu eta libreria bertara kopiatu
            File tempLib = File.createTempFile("lib", "opencv_java490.dll");
            try (FileOutputStream out = new FileOutputStream(tempLib)) {
                byte[] buffer = new byte[1024];
                int readBytes;
                while ((readBytes = in.read(buffer)) != -1) {
                    out.write(buffer, 0, readBytes);
                }
            }
            // Libreria argatu artxibo temporaltik
            System.load(tempLib.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
            

        
    }
}