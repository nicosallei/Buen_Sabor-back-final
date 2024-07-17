package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.Factura;
import NetDevops.BuenSabor.service.impl.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// GET http://localhost:8080/api/factura/

@RestController
@RequestMapping("/api/factura")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    public ResponseEntity<?> lista() {
        try {
            return ResponseEntity.ok().body(facturaService.TraerFacturas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(facturaService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Factura factura) {
        try {
            return ResponseEntity.ok().body(facturaService.crearFactura(factura));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Factura factura) {
        try {
            return ResponseEntity.ok().body(facturaService.actualizarFactura(id,factura));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(facturaService.eliminarFactura(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
