package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.dto.compraProducto.CompraPedidoDto;
import NetDevops.BuenSabor.entities.Pedido;
import NetDevops.BuenSabor.entities.mercadoPago.PreferenceMP;
import NetDevops.BuenSabor.service.impl.PedidoService;
import NetDevops.BuenSabor.service.mercadoPago.MercadoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/MercadoPago")
public class MercadoPagoController {
    @Autowired
    private MercadoPagoService mercadoPagoService;
    @Autowired
    private PedidoService pedidoService;


    @PostMapping("/crear_preference_mp")
    public PreferenceMP crearPreferenceMP(@RequestBody CompraPedidoDto pedido) {
        try {
            Pedido pedidoActualizado = pedidoService.buscarPorId(pedido.getId());
            PreferenceMP preferenceMP = mercadoPagoService.getPreferenciaIdMercadoPago(pedidoActualizado);
            pedidoActualizado.setPreferenceMPId(preferenceMP.getId());
            pedidoService.actualizarPedido(pedido.getId(), pedidoActualizado);
            return preferenceMP;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}