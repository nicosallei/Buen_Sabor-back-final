package NetDevops.BuenSabor.dto.usuario;

import NetDevops.BuenSabor.dto.BaseDto;
import NetDevops.BuenSabor.entities.Cliente;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistroDto extends BaseDto {
    private String username;
    private String password;
    private Cliente cliente;
}
