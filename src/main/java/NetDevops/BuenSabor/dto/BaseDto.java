package NetDevops.BuenSabor.dto;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseDto implements Serializable {
    private Long id;
    private boolean eliminado;
}
