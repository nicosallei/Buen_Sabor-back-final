package NetDevops.BuenSabor.dto.categoria;

import NetDevops.BuenSabor.dto.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubCategoriaListaDto extends BaseDto {

    private String denominacion;
    private String urlIcono;
}
