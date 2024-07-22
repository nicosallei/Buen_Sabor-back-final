package NetDevops.BuenSabor.dto.compraProducto;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.*;
import NetDevops.BuenSabor.enums.Estado;
import NetDevops.BuenSabor.enums.FormaPago;
import NetDevops.BuenSabor.enums.TipoEnvio;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompraPedidoDto extends BaseDto {
    private String hora;
    private Double total;
    private Double totalCosto;
    private String fechaPedido;
    private String preferenceMPId;
    private Sucursal sucursal;
    private Domicilio domicilio;
    private Cliente cliente;
    private List<PedidoDetalleDto> pedidoDetalle;
    private Factura factura;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private int tiempoEspera;
    private Long sucursalId;

}
