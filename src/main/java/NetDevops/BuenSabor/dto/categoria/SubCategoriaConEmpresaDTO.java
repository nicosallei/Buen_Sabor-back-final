package NetDevops.BuenSabor.dto.categoria;
import NetDevops.BuenSabor.dto.BaseDto;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubCategoriaConEmpresaDTO extends BaseDto {
    private String denominacion;
    private String urlIcono;
    private Long idCategoriaPadre;
    private Long idEmpresaCategoriaPadre;
    private Set<SubCategoriaConEmpresaDTO> subSubCategoriaDtos = new HashSet<>();

}