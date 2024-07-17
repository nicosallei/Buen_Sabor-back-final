package NetDevops.BuenSabor.service.util;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImagenService {

    private final Path root = Paths.get("src/main/resources/images");

    public String saveImage(MultipartFile imageFile) throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        Path destinationFile = root.resolve(Paths.get(imageFile.getOriginalFilename()))
                .normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(root.toAbsolutePath())) {
            throw new IllegalStateException("Cannot store file outside current directory.");
        }

        imageFile.transferTo(destinationFile);
        return imageFile.getOriginalFilename();
    }


    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public String guardarImagenNueva(String base64Image, String fileName) throws IOException {
        // Decodificar el string base64 a un array de bytes
        byte[] decodedBytes = Base64.decodeBase64(base64Image);

        // Crear un archivo en la carpeta designada con el nombre de archivo proporcionado
        File file = new File(uploadDir + File.separator + fileName);
        FileOutputStream fop = new FileOutputStream(file);

        // Escribir el array de bytes en el archivo
        fop.write(decodedBytes);
        fop.flush();
        fop.close();

        // Aquí puedes guardar el nombre del archivo en la base de datos y setearlo en la clase correspondiente

        return fileName;
    }

    public String convertirImagenABase64Nueva(String rutaImagen) throws IOException {
        // Leer el archivo de la imagen en un array de bytes
        byte[] bytesImagen = Files.readAllBytes(Paths.get(rutaImagen));

        // Codificar el array de bytes a un string en formato base64
        String imagenBase64 = Base64.encodeBase64String(bytesImagen);

        return imagenBase64;
    }










}