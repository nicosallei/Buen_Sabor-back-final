package NetDevops.BuenSabor.dto.compraProducto;

import NetDevops.BuenSabor.dto.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoDetalleDto extends BaseDto {
    private Integer cantidad;
    private Long pedidoId;
    private CompraProductoDto producto;
}
