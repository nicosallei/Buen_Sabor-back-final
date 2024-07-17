package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticuloRepository extends JpaRepository<Articulo, Long> {

    Articulo findById(long id);

}
