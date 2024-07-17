package NetDevops.BuenSabor.contoller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


// GET http://localhost:8080/api/imagenes/

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {


    private final Path root = Paths.get("images");

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            Path path = this.root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), path);
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/")
                    .path(file.getOriginalFilename())
                    .toUriString();
            return ResponseEntity.ok(uri);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

   @GetMapping("/{filename:.+}")
public ResponseEntity<?> get(@PathVariable String filename) {
    try {
        Path file = this.root.resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    } catch (MalformedURLException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

}
