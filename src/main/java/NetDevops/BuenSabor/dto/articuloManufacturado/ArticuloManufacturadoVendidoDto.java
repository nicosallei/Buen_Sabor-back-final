package NetDevops.BuenSabor.dto.articuloManufacturado;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloManufacturadoVendidoDto {
    private String denominacion;
    private Long cantidadVendida;


}