package NetDevops.BuenSabor.dto.usuario;


import NetDevops.BuenSabor.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDtoLogin {
    private Long id;
    private String email;
    private Rol rol;
    private Long empresaId;
    private Long sucursalId;




}
