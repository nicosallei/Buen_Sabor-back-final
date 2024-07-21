package NetDevops.BuenSabor.dto.usuario;

import NetDevops.BuenSabor.enums.Rol;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponseDto  {
    private String username;
    private Rol rol;
    private Long idUsuario;
    private long idSucursal;
    private long idEmpresa;
    private long idCliente;
}
