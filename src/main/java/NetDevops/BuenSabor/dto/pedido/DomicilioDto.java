package NetDevops.BuenSabor.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomicilioDto {
    private Long id;
    private String calle;
    private Integer numero;
    private Integer cp;
    private String localidad;
    private String provincia;
}
