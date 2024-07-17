package NetDevops.BuenSabor.dto.categoria;

import NetDevops.BuenSabor.dto.BaseDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoriaEmpresaDTO extends BaseDto {

    private String denominacion;
    private boolean eliminado;
    private Long empresaId;
    private String urlIcono;

    private Set<SubCategoriaConEmpresaDTO> subCategoriaDtos = new HashSet<>();
}
