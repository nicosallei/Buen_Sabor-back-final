package NetDevops.BuenSabor.dto.articuloManufacturado;

import NetDevops.BuenSabor.dto.BaseDto;


import NetDevops.BuenSabor.dto.articuloInsumo.ArticuloInsumoDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ArticuloManufacturadoDetalleDto extends BaseDto {

    private ArticuloInsumoDto articuloInsumoDto;
    private Integer cantidad;
    private ArticuloManufacturadoDto articuloManufacturadoDto;
}
