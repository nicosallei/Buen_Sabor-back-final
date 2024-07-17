package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.dto.promocion.PromocionDto;
import NetDevops.BuenSabor.entities.Promocion;

import java.util.Set;

public interface IPromocionService {

    public PromocionDto save(Promocion promocion) throws Exception;
    public Set<Promocion> getAll() throws Exception;
    //public Promocion getById(Long id) throws Exception;
    public PromocionDto update(Long id, Promocion promocion) throws Exception;
    public boolean delete(Long id) throws Exception;
    public Set<Promocion> getAllNotDeleted() throws Exception;
    public boolean reactivate(Long id) throws Exception;
    public PromocionDto getById(Long id) throws Exception;
    public PromocionDto getByIdBase64(Long id) throws Exception;
}
