package NetDevops.BuenSabor.dto.pedido;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.Domicilio;
import NetDevops.BuenSabor.enums.Estado;
import NetDevops.BuenSabor.enums.FormaPago;
import NetDevops.BuenSabor.enums.TipoEnvio;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PedidoDto extends BaseDto {
    private Long id;
    private LocalTime hora;
    private Double total;
    private Double totalCostoProduccion;
    private Estado estado;
    private FormaPago formaPago;
    private TipoEnvio tipoEnvio;
    private LocalDate fechaPedido;
    private String preferenceMPId;
    private List<PedidoDetalleDto> pedidoDetalleDto;
    private Domicilio domicilio;


}



