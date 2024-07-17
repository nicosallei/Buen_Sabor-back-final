package NetDevops.BuenSabor.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
//@Audited
public class UsuarioCliente extends Base{
    @Column(unique = true)
    private String username;
    private String password;

}
