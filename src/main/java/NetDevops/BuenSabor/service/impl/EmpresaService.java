package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.Empresa;
import NetDevops.BuenSabor.entities.Sucursal;
import NetDevops.BuenSabor.repository.IEmpresaRepository;
import NetDevops.BuenSabor.repository.ISucursalRepository;
import NetDevops.BuenSabor.service.IEmpresaService;
import NetDevops.BuenSabor.service.funcionalidades.Funcionalidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository empresaRepository;
    @Autowired
    private ISucursalRepository sucursalRepository;
    @Autowired
    private Funcionalidades funcionalidades;
    @Override
    public Empresa save(Empresa empresa) throws Exception {
        try {
            if (empresaRepository.existsByCuil(empresa.getCuil())) {
                throw new Exception("Ya existe una empresa con el CUIL proporcionado");
            }
            if (empresaRepository.existsByNombre(empresa.getNombre())) {
                throw new Exception("Ya existe una empresa con el nombre proporcionado");
            }

            if (empresa.getImagen() != null) {
                String rutaImagen = funcionalidades.guardarImagen(empresa.getImagen(), UUID.randomUUID().toString() + ".jpg");
                empresa.setImagen(rutaImagen);
            }
            return empresaRepository.save(empresa);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Empresa update(Long id, Empresa empresa) throws Exception {
        try {
            if (empresaRepository.existsByNombreAndNotId(empresa.getNombre(), id)) {
                throw new Exception("Ya existe una empresa con el CUIL proporcionado");
            }
            Empresa existingEmpresa = empresaRepository.findById(id).orElse(null);
            empresa.setId(id);

            if (empresa.getImagen() != null ) {
                // Eliminar la imagen antigua
                if(existingEmpresa.getImagen() != null){
                    funcionalidades.eliminarImagen(existingEmpresa.getImagen());
                }
                // Guardar la nueva imagen
                String rutaImagen = funcionalidades.guardarImagen(empresa.getImagen(), UUID.randomUUID().toString() + ".jpg");
                existingEmpresa.setImagen(rutaImagen);
                empresa.setImagen(rutaImagen);
            }else {
                empresa.setImagen(existingEmpresa.getImagen());
            }


            return empresaRepository.save(empresa);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


@Override
public boolean delete(Long id) throws Exception {
    try {
        Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la empresa con el id proporcionado"));

        // Verificar si la empresa tiene alguna sucursal asociada activa
        List<Sucursal> sucursalesActivas = sucursalRepository.findByEmpresaIdAndEliminadoFalse(id);
        if (!sucursalesActivas.isEmpty()) {
            throw new Exception("No se puede modificar el estado de la empresa porque tiene sucursales asociadas activas");
        }

        // Cambiar el estado de eliminado a no eliminado y viceversa
        empresa.setEliminado(!empresa.isEliminado());
        empresaRepository.save(empresa);
        return true;
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}

    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            if (empresaRepository.existsById(id)) {
                Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new Exception("No se encontró la empresa con el id proporcionado"));
                if (empresa.isEliminado()) {
                    empresa.setEliminado(false);
                    empresaRepository.save(empresa);
                    return true;
                } else {
                    throw new Exception("La Empresa con el id proporcionado no está eliminada");
                }
            } else {
                throw new Exception("No existe la Empresa con el id proporcionado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Empresa> traerTodo() throws Exception {
        try {
            return empresaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    @Override
    public List<Empresa> traerTodoNoEliminado() throws Exception {
        try {
            return empresaRepository.findByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Empresa traerPorId(Long id) throws Exception {
        try {
            return empresaRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
