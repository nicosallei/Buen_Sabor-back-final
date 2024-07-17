package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.sucursal.SucursalDto;
import NetDevops.BuenSabor.entities.*;
import NetDevops.BuenSabor.repository.*;
import NetDevops.BuenSabor.service.ISucursalService;
import NetDevops.BuenSabor.service.funcionalidades.Funcionalidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private ISucursalRepository sucursalRepository;
    @Autowired
    private IEmpresaRepository empresaRepository;
    @Autowired
    private IDomicilioRepository domicilioRepository;
    @Autowired
    private ILocalidadRepository localidadRepository;
    @Autowired
    private IProvinciaRepository provinciaRepository;
    @Autowired
    private IPaisRepository paisRepository;
    @Autowired
    private Funcionalidades funcionalidades;
  @Override
public Sucursal save(Sucursal sucursal) throws Exception {
    try {

        if (sucursalRepository.findByNombre(sucursal.getNombre())){
            throw new Exception("Ya existe una sucursal con el nombre proporcionado");
        }
        if (sucursal.getImagen() != null) {
            String rutaImagen = funcionalidades.guardarImagen(sucursal.getImagen(), UUID.randomUUID().toString() + ".jpg");
            sucursal.setImagen(rutaImagen);
        }
        return sucursalRepository.save(sucursal);
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}

    @Override
    public boolean delete(Long id) throws Exception {
        try {
                Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la sucursal con el id proporcionado"));
                if(sucursalRepository.existsByIdAndEliminadoFalse(id)){
                    sucursal.setEliminado(true);
                }else{
                    sucursal.setEliminado(false);
                }

                sucursalRepository.save(sucursal);
                return true;
        } catch (Exception e) {
        throw new Exception(e.getMessage());
        }
    }

@Override
public Sucursal updateDto(Long id, SucursalDto sucursalDto) throws Exception {
    try {
        Sucursal sucursalExistente = sucursalRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la sucursal con el id proporcionado"));

        // Verificar si existe otra sucursal con el mismo nombre pero diferente ID
        if(sucursalRepository.existsByNombreAndNotId(sucursalDto.getNombre(), id)){
            throw new Exception("Ya existe una sucursal con el nombre proporcionado");
        }

        // Actualizar los campos de la sucursal existente con los valores del SucursalDto
        sucursalExistente.setNombre(sucursalDto.getNombre());
        // Aquí deberías actualizar otros campos relevantes de SucursalDto, excepto aquellos que implican relaciones con otras entidades
        sucursalExistente.setHoraApertura(sucursalDto.getHoraApertura());
        sucursalExistente.setHoraCierre(sucursalDto.getHoraCierre());
        sucursalExistente.getDomicilio().setNumero(Integer.valueOf(sucursalDto.getNumero()));
        sucursalExistente.getDomicilio().setCalle(sucursalDto.getCalle());
        sucursalExistente.getDomicilio().setCp(Integer.valueOf(sucursalDto.getCp()));

        // Si se proporciona una nueva imagen, guardarla y actualizar el campo de imagen
        if (sucursalDto.getImagen() != null && !sucursalDto.getImagen().isEmpty()) {
            // Eliminar la imagen antigua si existe
            if(sucursalExistente.getImagen() != null){
                funcionalidades.eliminarImagen(sucursalExistente.getImagen());
            }
            // Guardar la nueva imagen
            String rutaImagen = funcionalidades.guardarImagen(sucursalDto.getImagen(), UUID.randomUUID().toString() + ".jpg");
            sucursalExistente.setImagen(rutaImagen);
        } else if (sucursalDto.getImagen() != null) {
            String rutaImagen = funcionalidades.guardarImagen(sucursalDto.getImagen(), UUID.randomUUID().toString() + ".jpg");
            sucursalExistente.setImagen(rutaImagen);
        }


        // Guardar la sucursal actualizada en el repositorio
        return sucursalRepository.save(sucursalExistente);
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}

    @Override
    public List<Sucursal> traerTodo() throws Exception {
        try {
            return sucursalRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Sucursal traerPorId(Long id) throws Exception {
        try {
            return sucursalRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la sucursal con el id proporcionado"));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Sucursal reactivate(Long id) throws Exception {
        try {
            Sucursal sucursal = sucursalRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la sucursal con el id proporcionado"));
            sucursal.setEliminado(false);
            return sucursalRepository.save(sucursal);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Sucursal> traerTodoPorEmpresaId(Long empresaId) throws Exception {
        try {
            return sucursalRepository.findByEmpresaId(empresaId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Override
public List<Sucursal> traerPorEmpresaId(Long empresaId) throws Exception {
    try {
        // Utiliza el nuevo método que filtra por empresaId y que no estén eliminadas
        return sucursalRepository.findByEmpresaIdAndEliminadoFalse(empresaId);
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}

    @Override
    public Sucursal guardarSucursalDto(SucursalDto sucursalDto) throws Exception {
        try {

            Empresa empresa = empresaRepository.findById(Long.valueOf(String.valueOf(sucursalDto.getIdEmpresa()))).orElseThrow(() -> new Exception("No se encontró la empresa con el id proporcionado"));
            Localidad localidad = localidadRepository.findById(Long.valueOf(sucursalDto.getLocalidad())).orElseThrow(() -> new Exception("No se encontró la localidad con el id proporcionado"));
            Provincia provincia = provinciaRepository.findById(Long.valueOf(sucursalDto.getProvincia())).orElseThrow(() -> new Exception("No se encontró la provincia con el id proporcionado"));
            Pais pais = paisRepository.findById(Long.valueOf(sucursalDto.getPais())).orElseThrow(() -> new Exception("No se encontró el país con el id proporcionado"));
            Domicilio domicilio = new Domicilio();
            Sucursal sucursal = new Sucursal();


            provincia.setPais(pais);
            localidad.setProvincia(provincia);
            domicilio.setLocalidad(localidad);
            sucursal.setEmpresa(empresa);

            domicilio.setCalle(sucursalDto.getCalle());
            domicilio.setNumero(Integer.valueOf(sucursalDto.getNumero()));
            domicilio.setCp(Integer.valueOf(sucursalDto.getCp()));
            domicilioRepository.save(domicilio);

            sucursal.setNombre(sucursalDto.getNombre());
            sucursal.setHoraApertura(sucursalDto.getHoraApertura());
            sucursal.setHoraCierre(sucursalDto.getHoraCierre());
            sucursal.setDomicilio(domicilio);

            if (sucursalDto.getImagen() != null) {
                String rutaImagen = funcionalidades.guardarImagen(sucursalDto.getImagen(), UUID.randomUUID().toString() + ".jpg");
                sucursal.setImagen(rutaImagen);
            }

            return sucursalRepository.save(sucursal);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public List<Sucursal> obtenerSucursalesActivas() throws Exception {
       try {
           return sucursalRepository.findByEliminadoFalse();
       } catch (Exception e) {
           throw new Exception(e.getMessage());
       }
    }


}
