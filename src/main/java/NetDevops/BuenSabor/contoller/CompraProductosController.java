package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.compraProducto.CompraPedidoDto;
import NetDevops.BuenSabor.entities.Pedido;
import NetDevops.BuenSabor.errores.ApiError;
import NetDevops.BuenSabor.service.impl.CompraProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


// GET http://localhost:8080/api/compra/productos/

@RestController
@RequestMapping("/api/compra/productos")
public class CompraProductosController {
    @Autowired
    private CompraProductosService compraProductosService;



    //region CRUD Basico

    @CrossOrigin(origins = "https://ecommerce-buen-sabor.vercel.app")
    @GetMapping("/{categoriaId}")
    public ResponseEntity<?> findArticulosByCategoria(@PathVariable Long categoriaId) {
        try {
            return ResponseEntity.ok().body(compraProductosService.findArticulosByCategoria(categoriaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/buscar-articulo")
    public ResponseEntity<?> buscarArticulo(@RequestBody Map<String, Long> body) {
        try {
            Long id = body.get("id");
            return ResponseEntity.ok().body(compraProductosService.buscarArticuloPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/crear-pedido")
    public ResponseEntity<?> crearPedido(@RequestBody CompraPedidoDto compraPedidoDto) {
        try {
            CompraPedidoDto pedido = compraProductosService.crearPedido(compraPedidoDto);
            return ResponseEntity.ok().body(pedido);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //endregion
}
