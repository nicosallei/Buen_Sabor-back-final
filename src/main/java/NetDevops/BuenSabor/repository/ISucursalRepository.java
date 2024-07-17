package NetDevops.BuenSabor.repository;

import NetDevops.BuenSabor.entities.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISucursalRepository extends JpaRepository<Sucursal, Long> {
    List<Sucursal> findByEmpresaIdAndEliminadoFalse(Long empresaId);
    List<Sucursal> findByEmpresaId(Long empresaId);
    boolean findByNombre(String nombre);
    boolean existsByIdAndEliminadoFalse(long id);



    List<Sucursal> findByEliminadoFalse();

    @Query("SELECT COUNT(s) > 0 FROM Sucursal s WHERE s.nombre = :nombre AND s.id <> :id")
    boolean existsByNombreAndNotId(@Param("nombre") String nombre, @Param("id") Long id);
}
