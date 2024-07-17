package NetDevops.BuenSabor.dto.categoria;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.dto.sucursal.SucursalSimpleDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoriaDto extends BaseDto {

    private String denominacion;
    private String urlIcono;
    private Set<SucursalSimpleDto> sucursales = new HashSet<>();
    private Set<SubCategoriaDto> subCategoriaDtos = new HashSet<>();
}

