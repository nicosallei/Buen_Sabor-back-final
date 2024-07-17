package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoTablaDto;
import NetDevops.BuenSabor.entities.ArticuloInsumo;
import NetDevops.BuenSabor.entities.ArticuloManufacturado;
import NetDevops.BuenSabor.service.impl.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/local")
public class LocalController {
    @Autowired
    private LocalService localService;

    //region Categoria

    @PostMapping("/agregarSucursalACategoria/{categoriaId}/{sucursalId}")
    public ResponseEntity<?> agregarSucursalACategoria(@PathVariable Long categoriaId, @PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(localService.agregarSucursalACategoria(categoriaId, sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/desasociarSucursalDeCategoria/{categoriaId}/{sucursalId}")
    public ResponseEntity<?> desasociarSucursalDeCategoria(@PathVariable Long categoriaId, @PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(localService.desasociarSucursalDeCategoria(categoriaId, sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/traerTodoCategoria/{sucursalId}")
    public ResponseEntity<?> traerTodoCategoria(@PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(localService.traerTodo(sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/traerTodo/{sucursalId}")
    public ResponseEntity<?> traerTodo(@PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok(localService.traerTodo(sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

   @GetMapping("/traerCategoriasNoAsociadasASucursal/{sucursalId}/{empresaId}")
public ResponseEntity<?> traerCategoriasNoAsociadasASucursal(@PathVariable Long sucursalId, @PathVariable Long empresaId){
    try {
        return ResponseEntity.ok(localService.traerCategoriasNoAsociadasASucursal(sucursalId, empresaId));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


    //endregion

    //region Promociones

    @CrossOrigin(origins = "https://ecommerce-buen-sabor.vercel.app")
    @GetMapping("/promocion/activas")
    public ResponseEntity<?> traerTodo(){
        try {
            return ResponseEntity.ok().body(localService.buscarPromocionesActivas());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/promocion/sucursal/{sucursalId}")
    public ResponseEntity<?> traerPromocionesPorSucursal(@PathVariable Long sucursalId){
        try {
            return ResponseEntity.ok().body(localService.buscarPromocionesPorSucursal(sucursalId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/promocion/detalle/{promocionId}")
    public ResponseEntity<?> traerPromocionDetallePorPromocion(@PathVariable Long promocionId){
        try {
            return ResponseEntity.ok().body(localService.buscarDetallesPorPromocion(promocionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/promocion/traer/{promocionId}")
    public ResponseEntity<?> traerPromocionPorId(@PathVariable Long promocionId){
        try {
            return ResponseEntity.ok().body(localService.getById(promocionId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //endregion

    //region ArticuloInsumo
    @GetMapping("/articulo/insumo/sucursal/{sucursalId}")
public ResponseEntity<?> traerArticulosInsumoPorSucursal(@PathVariable Long sucursalId){
    try {
        return ResponseEntity.ok(localService.traerArticulosInsumoPorSucursal(sucursalId));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @PutMapping("articulo/insumo/aumentarStock/{id}")
    public ResponseEntity<ArticuloInsumo> aumentarStock(@PathVariable Long id, @RequestParam Integer cantidad, @RequestParam Double nuevoPrecioVenta, @RequestParam Double nuevoPrecioCompra) {
        try {
            ArticuloInsumo articuloInsumo = localService.aumentarStock(id, cantidad, nuevoPrecioVenta, nuevoPrecioCompra);
            return ResponseEntity.ok(articuloInsumo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    //endregion

    //region ArticuloManufacturado

    @GetMapping("/articulo/manufacturado/sucursal/{sucursalId}")
    public ResponseEntity<List<ArticuloManufacturadoTablaDto>> buscarArticulosPorSucursal(@PathVariable Long sucursalId) {
        try {
            List<ArticuloManufacturadoTablaDto> articulos = localService.buscarArticulosPorSucursal(sucursalId);
            return ResponseEntity.ok(articulos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //endregion
}