package NetDevops.BuenSabor.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited
public class UsuarioEmpleado extends Base{
    private String username;
    private String password;

    private Empleado empleado;
}
