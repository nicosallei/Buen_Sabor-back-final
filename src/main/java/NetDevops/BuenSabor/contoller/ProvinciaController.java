package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.Provincia;
import NetDevops.BuenSabor.service.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// GET http://localhost:8080/api/provincia/

@RestController
@RequestMapping("/api/provincia")
public class ProvinciaController {
    @Autowired
    private IProvinciaService provinciaService;

    //region CRUD Basico

        @GetMapping("/traer-todo/")
        public ResponseEntity<?> mostrarLista(){
            try {
                return ResponseEntity.ok(provinciaService.buscarTodos());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        @GetMapping("/{id}")
        public ResponseEntity<?> buscarPorId(@PathVariable Long id){
            try {
                return ResponseEntity.ok(provinciaService.buscarPorId(id));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        @PutMapping("/{id}")
        public ResponseEntity<?> actualizar(@PathVariable Long id,@RequestBody Provincia provincia){
            try {
                return ResponseEntity.ok(provinciaService.modificar(id, provincia));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        @PostMapping("/")
        public ResponseEntity<?> cargar(@RequestBody Provincia provincia){
            try {
                return ResponseEntity.ok(provinciaService.guardar(provincia));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteById(@PathVariable Long id){
            try {
                return ResponseEntity.ok(provinciaService.eliminar(id));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        @PostMapping("/reactivate/{id}")
        public ResponseEntity<?> reactivar(@PathVariable Long id){
            try {
                return ResponseEntity.ok(provinciaService.reactivar(id));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }


    //endregion
}
