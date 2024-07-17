package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.Pais;
import NetDevops.BuenSabor.repository.IPaisRepository;
import NetDevops.BuenSabor.service.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisService implements IPaisService {
    @Autowired
    private IPaisRepository paisRepository;


    @Override
    public Boolean eliminar(Long id) throws Exception {
        try {
            Pais pais = paisRepository.findById(id).get();
            //pais.setEliminado(true);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pais guardar(Pais entity) throws Exception {
        try {
            return paisRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pais modificar(Long id, Pais entity) throws Exception {
        try {
            Pais pais = paisRepository.findById(id).get();
            pais.setNombre(entity.getNombre());
            return paisRepository.save(pais);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pais buscarPorId(Long id) throws Exception {
        try {
            return paisRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public List<Pais> buscarTodos() throws Exception {
        try {
            return paisRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Boolean reactivar(Long id) throws Exception {
        try {
            Pais pais = paisRepository.findById(id).get();
           // pais.setEliminado(false);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
