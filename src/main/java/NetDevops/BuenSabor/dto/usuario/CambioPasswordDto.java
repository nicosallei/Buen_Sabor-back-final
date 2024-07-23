package NetDevops.BuenSabor.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CambioPasswordDto {
    private Long id;
    private String username;
    private String passwordActual;
    private String nuevaPassword;
}
