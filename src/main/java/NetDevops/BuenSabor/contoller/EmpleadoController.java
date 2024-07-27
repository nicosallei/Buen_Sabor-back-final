package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.Empleado;
import NetDevops.BuenSabor.service.impl.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// GET http://localhost:8080/api/empleado/

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/")
    public ResponseEntity<?> lista() {
        try {
            return ResponseEntity.ok().body(empleadoService.traerEmpleados());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(empleadoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok().body(empleadoService.crearEmpleado(empleado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok().body(empleadoService.actualizarEmpleado(id,empleado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(empleadoService.eliminarEmpleado(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<?> listaPorSucursal(@PathVariable Long sucursalId) {
        try {
            return ResponseEntity.ok().body(empleadoService.traerEmpleadosPorSucursal(sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarActualizando(@PathVariable Long id) {
        try {
            Empleado empleado = empleadoService.eliminarEmpleadoActualizando(id);
            if (empleado != null) {
                return ResponseEntity.ok().body(empleado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @GetMapping("/email/{email}")
    public ResponseEntity<Empleado> getEmpleadoByEmail(@PathVariable String email) {
        Empleado empleado = empleadoService.buscarPorEmail(email);
        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        } else {
            return null;
        }
    }
}
