package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.UnidadMedida;
import NetDevops.BuenSabor.repository.IUnidadMedidaRepository;
import NetDevops.BuenSabor.service.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UnidadMedidaService implements IUnidadMedidaService {

    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    @Override
    public UnidadMedida buscarPorId(Long id) throws Exception {
        try {
            return unidadMedidaRepository.findByIdAndEliminadoFalse(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

  @Override
public UnidadMedida cargar(UnidadMedida unidadMedida) throws Exception {
    try {
        if (unidadMedida.getDenominacion() == null || unidadMedida.getDenominacion().isEmpty()){
            throw new Exception("El nombre de la unidad de medida no puede ser nulo");
        }
        if (unidadMedidaRepository.existsByDenominacionIgnoreCase(unidadMedida.getDenominacion().toLowerCase())){
            throw new Exception("Ya existe una unidad de medida con ese nombre");
        }
        return unidadMedidaRepository.save(unidadMedida);
    }catch (Exception e){
        throw new Exception(e.getMessage());
    }
}
@Override
public UnidadMedida actualizar(Long id, UnidadMedida unidadMedida) throws Exception {
    try {
        if (!unidadMedidaRepository.existsById(id)){
            throw new Exception("La unidad de medida no puede ser nula");
        }
        if (unidadMedida.getDenominacion() == null || unidadMedida.getDenominacion().isEmpty()){
            throw new Exception("El nombre de la unidad de medida no puede ser nulo");
        }
        if (unidadMedidaRepository.existsByDenominacionIgnoreCase(unidadMedida.getDenominacion())){
            throw new Exception("Ya existe una unidad de medida con ese nombre");
        }
        UnidadMedida unidadMedidaExistente = unidadMedidaRepository.findById(id).get();
        unidadMedidaExistente.setDenominacion(unidadMedida.getDenominacion());
        // Aquí puedes agregar más campos de UnidadMedida para actualizar si los hay
        return unidadMedidaRepository.save(unidadMedidaExistente);
    }catch (Exception e){
        throw new Exception(e.getMessage());
    }
}


    @Override
    public boolean deleteById(Long id) throws Exception {
        try {
            UnidadMedida unidadMedida = unidadMedidaRepository.findByIdAndEliminadoFalse(id);
            if (unidadMedida == null){
                throw new Exception("La unidad de medida no puede ser nula");
            }
            unidadMedida.setEliminado(true);
            unidadMedidaRepository.save(unidadMedida);
            return true;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Set<UnidadMedida> mostrarLista() throws Exception {

        try {
            return unidadMedidaRepository.findByEliminadoFalse();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            if (unidadMedidaRepository.existsById(id)) {
                UnidadMedida unidadMedida = unidadMedidaRepository.findById(id).get();
                if (unidadMedida.isEliminado()) {
                    unidadMedida.setEliminado(false);
                    unidadMedidaRepository.save(unidadMedida);
                    return true;
                } else {
                    throw new Exception("La UnidadMedida con el id proporcionado no está eliminada");
                }
            } else {
                throw new Exception("No existe la UnidadMedida con el id proporcionado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public List<UnidadMedida> traerTodo() throws Exception {
        try {
            return unidadMedidaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

public boolean toggleActive(Long id) throws Exception {
    try {
        if (unidadMedidaRepository.existsById(id)) {
            UnidadMedida unidadMedida = unidadMedidaRepository.findById(id).get();
            if (unidadMedida.isEliminado()) {
                unidadMedida.setEliminado(false);
            } else {
                unidadMedida.setEliminado(true);
            }
            unidadMedidaRepository.save(unidadMedida);
            return true;
        } else {
            throw new Exception("No existe la UnidadMedida con el id proporcionado");
        }
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}
}
