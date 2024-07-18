package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.promocion.PromocionDto;
import NetDevops.BuenSabor.entities.Promocion;
import NetDevops.BuenSabor.errores.ApiError;
import NetDevops.BuenSabor.repository.IPromocionDetalleRepository;
import NetDevops.BuenSabor.service.IPromocionService;
import NetDevops.BuenSabor.service.impl.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
                return new ResponseEntity<>(apiError, apiError.getStatus());
            }
        }
@GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.getById(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @GetMapping("/base64/{id}")
    public ResponseEntity getByIdBase64(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.getByIdBase64(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity save(@RequestBody Promocion promocion) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.save(promocion));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Promocion promocion) {
        try {

            return ResponseEntity.status(HttpStatus.OK).body(promocionService.update(id, promocion));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(promocionService.delete(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PostMapping("/reactivar/{id}")
    public ResponseEntity<?> reactivate(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(promocionService.reactivate(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @DeleteMapping("/eliminar-detalles/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO_COCINA') or hasAuthority('ADMINISTRADOR')")
public ResponseEntity deleteAllPromocionDetalles(@PathVariable Long id) {
    try {
        promocionService.deleteAllPromocionDetalles(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

    @GetMapping("/validas/sucursal/{sucursalId}")
    public ResponseEntity<?> obtenerPromocionesValidasPorSucursalYFecha(@PathVariable Long sucursalId) {
        try {
            List<PromocionDto> promociones = promocionService.obtenerPromocionesValidasPorSucursalYFecha(sucursalId);
            return ResponseEntity.ok(promociones);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }


}

