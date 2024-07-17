package NetDevops.BuenSabor.dto.categoria;
import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.dto.sucursal.SucursalSimpleDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubCategoriaDto extends BaseDto {

    private String denominacion;
    private String urlIcono;
    private Long idCategoriaPadre;
    private Set<SubCategoriaDto> subSubCategoriaDtos = new HashSet<>();
    private Set<SucursalSimpleDto> sucursales = new HashSet<>();



}