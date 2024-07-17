package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.ImagenEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImagenEmpleadoRepository extends JpaRepository<ImagenEmpleado, Long> {
}
