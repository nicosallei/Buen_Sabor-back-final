package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.pedido.PedidoDto;
import NetDevops.BuenSabor.entities.Pedido;
import NetDevops.BuenSabor.entities.UsuarioEmpleado;
import NetDevops.BuenSabor.enums.Estado;
import NetDevops.BuenSabor.errores.ApiError;
import NetDevops.BuenSabor.service.impl.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// GET http://localhost:8080/api/pedidos/

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping("traer-lista/{sucursalId}")
public ResponseEntity<?> lista(@PathVariable Long sucursalId) {
    try {
        return ResponseEntity.ok().body(pedidoService.traerPedidos(sucursalId));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(pedidoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            return ResponseEntity.ok().body(pedidoService.actualizarPedido(id,pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Pedido pedido) {
        try {
            return ResponseEntity.ok().body(pedidoService.crearPedido(pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(pedidoService.eliminarPedido(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> traerPedidos2(HttpSession session) throws Exception {
        try {
            UsuarioEmpleado usuario = (UsuarioEmpleado) session.getAttribute("usuario");
            return ResponseEntity.ok().body(pedidoService.traerPedidos2(usuario));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

@PutMapping("/estado/{id}")
public ResponseEntity<?> cambiarEstadoPedido(@PathVariable Long id, @RequestBody String nuevoEstado) {
    try {
        Estado estado = Estado.valueOf(nuevoEstado.toUpperCase());
        PedidoDto pedidoActualizado = pedidoService.cambiarEstadoPedido(id, estado);
        return ResponseEntity.ok().body(pedidoActualizado);
    } catch (Exception e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

}
