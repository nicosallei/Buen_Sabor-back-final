package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILocalidadRepository extends JpaRepository<Localidad, Long> {
    List<Localidad> findByProvinciaId(Long provinciaId);
}
