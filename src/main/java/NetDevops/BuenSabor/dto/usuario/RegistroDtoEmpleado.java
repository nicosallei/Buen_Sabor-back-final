package NetDevops.BuenSabor.dto.usuario;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.Empleado;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistroDtoEmpleado extends BaseDto {
    private String username;
    private String password;
    private Empleado empleado;
}
