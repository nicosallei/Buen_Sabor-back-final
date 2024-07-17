package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.localidad.LocalidadDTO;
import NetDevops.BuenSabor.entities.Localidad;
import NetDevops.BuenSabor.entities.Municipio;
import NetDevops.BuenSabor.entities.Provincia;
import NetDevops.BuenSabor.repository.ILocalidadRepository;
import NetDevops.BuenSabor.repository.IProvinciaRepository;
import NetDevops.BuenSabor.service.ILocalidadService;
import NetDevops.BuenSabor.service.util.ApiClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadService implements ILocalidadService {
    @Autowired
    private ILocalidadRepository localidadRepository;
    @Autowired
    private ApiClienteService apiClienteService;
    @Autowired
    private IProvinciaRepository provinciaRepository;


    @Override
    public Boolean eliminar(Long id) throws Exception {
        try {
            Localidad localidad = localidadRepository.findById(id).get();
            //localidad.setEliminado(true);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Localidad guardar(Localidad entity) throws Exception {
        try {
            return localidadRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Localidad modificar(Long id, Localidad entity) throws Exception {
        try {
            Localidad localidad = localidadRepository.findById(id).get();
            localidad.setNombre(entity.getNombre());
            return localidadRepository.save(localidad);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Localidad buscarPorId(Long id) throws Exception {
        try {
            return localidadRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Localidad> buscarTodos() throws Exception {
        try {
            return localidadRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Boolean reactivar(Long id) throws Exception {
        try {
            Localidad localidad = localidadRepository.findById(id).get();
            //localidad.setEliminado(false);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void guardarLocalidadesDeProvincia(String provinciaId) {
        Provincia provincia = provinciaRepository.findById(Long.parseLong(provinciaId)).get();
        List<Municipio> municipios = apiClienteService.obtenerLocalidadesPorProvincia(provinciaId);
        municipios.forEach(dto -> {
            Localidad localidad = new Localidad();
            localidad.setId(Long.parseLong(dto.getId()));
            localidad.setNombre(dto.getNombre());
            localidad.setProvincia(provincia);
            localidadRepository.save(localidad);
        });
    }

    public List<Localidad> getLocalidadesByProvinciaId(Long provinciaId) {
        return localidadRepository.findByProvinciaId(provinciaId);
    }

    }



