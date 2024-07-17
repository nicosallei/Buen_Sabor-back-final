package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.Domicilio;
import NetDevops.BuenSabor.repository.IDomicilioRepository;
import NetDevops.BuenSabor.service.IDomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomicilioService implements IDomicilioService {
    @Autowired
    private IDomicilioRepository domicilioRepository;


    @Override
    public Boolean eliminar(Long id) throws Exception {
        try {
            Domicilio domicilio = domicilioRepository.findById(id).get();
            domicilio.setEliminado(true);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Domicilio guardar(Domicilio entity) throws Exception {
        try {
            return domicilioRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Domicilio modificar(Long id, Domicilio entity) throws Exception {
        try {
            Domicilio domicilio = domicilioRepository.findById(id).get();
            domicilio.setCalle(entity.getCalle());
            return domicilioRepository.save(domicilio);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Domicilio buscarPorId(Long id) throws Exception {
        try {
            return domicilioRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Domicilio> buscarTodos() throws Exception {
        try {
            return domicilioRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Boolean reactivar(Long id) throws Exception {
        try {
            Domicilio domicilio = domicilioRepository.findById(id).get();
            domicilio.setEliminado(false);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
