package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.Pais;
import NetDevops.BuenSabor.service.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// GET http://localhost:8080/api/pais/

@RestController
@RequestMapping("/api/pais")
public class PaisController {
    @Autowired
    private IPaisService paisService;

    //region CRUD Basico

    @GetMapping("/traer-todo/")
    public ResponseEntity<?> mostrarLista(){
        try {
            return ResponseEntity.ok(paisService.buscarTodos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(paisService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Pais pais){
        try {
            return ResponseEntity.ok(paisService.guardar(pais));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody Pais pais){
        try {
            return ResponseEntity.ok(paisService.modificar(id, pais));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(paisService.eliminar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
@PostMapping("/reactivate/{id}")
    public ResponseEntity<?> reactivar(@PathVariable Long id){
        try {
            return ResponseEntity.ok(paisService.reactivar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //endregion

}
