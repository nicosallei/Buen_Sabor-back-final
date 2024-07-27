package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.ArticuloInsumo;
import NetDevops.BuenSabor.errores.ApiError;
import NetDevops.BuenSabor.service.IArticuloInsumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


// GET http://localhost:8080/api/articulos/insumos/
@RestController
@RequestMapping("/api/articulos/insumos")
public class ArticuloInsumoController {

    @Autowired
    private IArticuloInsumoService articuloInsumoService;

    //region CRUD Basico
    @GetMapping("/")
    public ResponseEntity<?> buscarTodos() {
        try {
            return ResponseEntity.ok(articuloInsumoService.mostrarLista());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articuloInsumoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<?> guardar(@RequestBody ArticuloInsumo articuloInsumo) {
        try {
           ArticuloInsumo insumo = articuloInsumoService.cargar(articuloInsumo);

            return ResponseEntity.ok(articuloInsumoService.buscarPorId(insumo.getId()));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ArticuloInsumo articuloInsumo) {
        try {
            return ResponseEntity.ok(articuloInsumoService.actualizar(id, articuloInsumo));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articuloInsumoService.deleteById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//endregion


    @PostMapping("/reactivate/{id}")
    //@PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> reactivate(@PathVariable Long id) {
        try {
            articuloInsumoService.reactivate(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/taer-todo/")
    public ResponseEntity<?> traerTodo() {
        try {
            return ResponseEntity.ok(articuloInsumoService.traerTodo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
