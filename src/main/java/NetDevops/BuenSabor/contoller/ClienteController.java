package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.entities.Cliente;
import NetDevops.BuenSabor.entities.Empleado;
import NetDevops.BuenSabor.service.impl.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// GET http://localhost:8080/api/cliente/

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok().body(clienteService.TraerClientes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(clienteService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok().body(clienteService.crearCliente(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok().body(clienteService.actualizarCliente(id, cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize(" hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(clienteService.eliminarCliente(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getEmpleadoByEmail(@PathVariable String email) {
        Cliente cliente = clienteService.buscarPorEmail(email);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
