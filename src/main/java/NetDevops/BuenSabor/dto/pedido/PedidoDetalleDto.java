package NetDevops.BuenSabor.dto.pedido;

import NetDevops.BuenSabor.dto.BaseDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoDetalleDto extends BaseDto {
    private ArticuloManufacturadoDto articulo;
    private Integer cantidad;

}
