package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.Promocion;
import NetDevops.BuenSabor.repository.IPromocionDetalleRepository;
import NetDevops.BuenSabor.service.IPromocionService;
import NetDevops.BuenSabor.service.impl.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//     http://localhost:8080/api/promociones/

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {
    @Autowired
    private PromocionService promocionService;
    @Autowired
    private IPromocionDetalleRepository promocionDetalleRepository;

    @GetMapping("/traer-todo/")
    public ResponseEntity getAll() {
            try {
                return ResponseEntity.ok().body(promocionService.getAll());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
@GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/base64/{id}")
    public ResponseEntity getByIdBase64(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.getByIdBase64(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity save(@RequestBody Promocion promocion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.save(promocion));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Promocion promocion) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(promocionService.update(id, promocion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(promocionService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reactivar/{id}")
    public ResponseEntity<?> reactivate(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.reactivate(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar-detalles/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
public ResponseEntity deleteAllPromocionDetalles(@PathVariable Long id) {
    try {
        promocionService.deleteAllPromocionDetalles(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


}

