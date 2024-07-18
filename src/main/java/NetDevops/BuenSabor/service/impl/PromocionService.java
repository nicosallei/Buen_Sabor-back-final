package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoCantidadDto;
import NetDevops.BuenSabor.dto.promocion.ArticuloPromocionDto;
import NetDevops.BuenSabor.dto.promocion.PromocionDetalleDto;
import NetDevops.BuenSabor.dto.promocion.PromocionDto;
import NetDevops.BuenSabor.entities.*;
import NetDevops.BuenSabor.repository.IPromocionDetalleRepository;
import NetDevops.BuenSabor.repository.IPromocionRepository;
import NetDevops.BuenSabor.service.IPromocionService;
import NetDevops.BuenSabor.service.funcionalidades.Funcionalidades;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PromocionService implements IPromocionService {
    @Autowired
    private IPromocionRepository promocionRepository;
    @Autowired
    private IPromocionDetalleRepository promocionDetalleRepository;
    @Autowired
    private Funcionalidades funcionalidades;

    @Override
    public PromocionDto save(Promocion promocion) throws Exception {
        try {
            if (promocion.getImagen() != null) {
                String rutaImagen = funcionalidades.guardarImagen(promocion.getImagen(), UUID.randomUUID().toString() + ".jpg");
                promocion.setImagen(rutaImagen);
            }

            // Establecer la relación bidireccional con PromocionDetalle
            for (PromocionDetalle detalle : promocion.getPromocionDetalles()) {
                detalle.setPromocion(promocion);
            }

            PromocionDto dto = convertToDto(promocionRepository.save(promocion));
            return dto;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Set<Promocion> getAll() throws Exception {
        try {
            return promocionRepository.findAll().stream().collect(Collectors.toSet());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public PromocionDto getById(Long id) throws Exception {
        try {
            Promocion promocion = promocionRepository.findById(id).get();
            return convertToDto(promocion);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public PromocionDto convertToDto(Promocion promocion) {
        PromocionDto dto = new PromocionDto();
        dto.setId(promocion.getId());
        dto.setEliminado(promocion.isEliminado());
        dto.setDenominacion(promocion.getDenominacion());
        dto.setFechaDesde(promocion.getFechaDesde());
        dto.setFechaHasta(promocion.getFechaHasta());
        dto.setHoraDesde(promocion.getHoraDesde());
        dto.setHoraHasta(promocion.getHoraHasta());
        dto.setDescripcionDescuento(promocion.getDescripcionDescuento());
        dto.setPrecioPromocional(promocion.getPrecioPromocional());
        dto.setTipoPromocion(promocion.getTipoPromocion());
        dto.setImagen(promocion.getImagen());
        for (PromocionDetalle detalle : promocion.getPromocionDetalles()) {
            dto.getPromocionDetallesDto().add(convertirDetalleToDto(detalle));
        }
        dto.setArticulosManufacturadosCantidad(calcularCantidadMaximaProducto(promocion));
        return dto;
    }


    public ArticuloPromocionDto convertirArticuloToDto(ArticuloManufacturado articuloManufacturado) {
        ArticuloPromocionDto dto = new ArticuloPromocionDto();
        dto.setId(articuloManufacturado.getId());
        dto.setEliminado(articuloManufacturado.isEliminado());
        dto.setDenominacion(articuloManufacturado.getDenominacion());
        dto.setDescripcion(articuloManufacturado.getDescripcion());
        dto.setPrecioVenta(articuloManufacturado.getPrecioVenta());
        dto.setTiempoEstimadoMinutos(articuloManufacturado.getTiempoEstimadoMinutos());
        dto.setPreparacion(articuloManufacturado.getPreparacion());
        dto.setImagenes(articuloManufacturado.getImagenes());
        dto.setCodigo(articuloManufacturado.getCodigo());
        dto.setUnidadMedida(articuloManufacturado.getUnidadMedida());
        return dto;
    }

    public PromocionDetalleDto convertirDetalleToDto(PromocionDetalle promocionDetalle) {
        PromocionDetalleDto dto = new PromocionDetalleDto();
        dto.setId(promocionDetalle.getId());
        dto.setEliminado(promocionDetalle.isEliminado());
        dto.setCantidad(promocionDetalle.getCantidad());
        dto.setArticuloManufacturadoDto(convertirArticuloToDto(promocionDetalle.getArticuloManufacturado()));
        dto.setImagenPromocion(promocionDetalle.getImagenPromocion());
        return dto;
    }


    @Override
    @Transactional
    public PromocionDto update(Long id, Promocion newPromocion) {
        try {
            if (promocionRepository.findById(id).isPresent()) {
                Promocion existingPromocion = promocionRepository.findById(id).get();

                if (newPromocion.getDenominacion() != null) {
                    existingPromocion.setDenominacion(newPromocion.getDenominacion());
                }
                if (newPromocion.getFechaDesde() != null) {
                    existingPromocion.setFechaDesde(newPromocion.getFechaDesde());
                }
                if (newPromocion.getFechaHasta() != null) {
                    existingPromocion.setFechaHasta(newPromocion.getFechaHasta());
                }
                if (newPromocion.getHoraDesde() != null) {
                    existingPromocion.setHoraDesde(newPromocion.getHoraDesde());
                }
                if (newPromocion.getHoraHasta() != null) {
                    existingPromocion.setHoraHasta(newPromocion.getHoraHasta());
                }
                if (newPromocion.getDescripcionDescuento() != null) {
                    existingPromocion.setDescripcionDescuento(newPromocion.getDescripcionDescuento());
                }
                if (newPromocion.getPrecioPromocional() != null) {
                    existingPromocion.setPrecioPromocional(newPromocion.getPrecioPromocional());
                }
                if (newPromocion.getTipoPromocion() != null) {
                    existingPromocion.setTipoPromocion(newPromocion.getTipoPromocion());
                }
                if (newPromocion.getImagen() != null ) {
                    // Eliminar la imagen antigua
                        if(existingPromocion.getImagen() != null){
                            funcionalidades.eliminarImagen(existingPromocion.getImagen());
                        }
                    // Guardar la nueva imagen
                    String rutaImagen = funcionalidades.guardarImagen(newPromocion.getImagen(), UUID.randomUUID().toString() + ".jpg");
                    existingPromocion.setImagen(rutaImagen);
                }


                // Marcar como eliminadas las PromocionDetalle que no están en newPromocion
                for (PromocionDetalle detalle : existingPromocion.getPromocionDetalles()) {
                    if (!newPromocion.getPromocionDetalles().contains(detalle)) {
                        detalle.setEliminado(true);
                    }
                }
                existingPromocion.setPromocionDetalles(newPromocion.getPromocionDetalles());
                   Promocion promocionAcutalizada = promocionRepository.save(existingPromocion);
                    PromocionDto dto = convertToDto(promocionAcutalizada);
                return dto;

            } else {
                throw new Exception("No existe la promoción con el id proporcionado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la promoción", e);
        }
    }


    @Override
    public boolean delete(Long id) throws Exception {
        try {
            if (promocionRepository.existsById(id)) {
                Promocion promocion = promocionRepository.findById(id).get();
                boolean nuevoEstado = !promocion.isEliminado();
                promocion.setEliminado(nuevoEstado);


                // Cambiar el estado de eliminado de las PromocionDetalle asociadas
                for (PromocionDetalle detalle : promocion.getPromocionDetalles()) {
                    detalle.setEliminado(nuevoEstado);
                }

                promocionRepository.save(promocion);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Set<Promocion> getAllNotDeleted() throws Exception {
        try {
            return promocionRepository.findByEliminadoFalse().stream().collect(Collectors.toSet());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            if (promocionRepository.existsById(id)) {
                Promocion promocion = promocionRepository.findById(id).get();
                if (promocion.isEliminado()) {
                    promocion.setEliminado(false);


                    // Reactivar las PromocionDetalle asociadas
                    for (PromocionDetalle detalle : promocion.getPromocionDetalles()) {
                        if (detalle.isEliminado()) {
                            detalle.setEliminado(false);
                        }
                    }

                    promocionRepository.save(promocion);
                    return true;
                } else {
                    throw new Exception("La promoción con el id proporcionado no está eliminada");
                }
            } else {
                throw new Exception("No existe la promoción con el id proporcionado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void deleteAllPromocionDetalles(Long promocionId) throws Exception {
        try {
            List<PromocionDetalle> detalles = promocionDetalleRepository.findByPromocionId(promocionId);
            for (PromocionDetalle detalle : detalles) {
                Promocion promocion = detalle.getPromocion();
                detalle.setPromocion(null);
                promocion.getPromocionDetalles().remove(detalle); // Elimina la referencia en promocion_promocion_detalles
                promocionDetalleRepository.save(detalle);
                promocionDetalleRepository.delete(detalle);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public PromocionDto getByIdBase64(Long id) throws Exception {
        try {

            Promocion promocion = promocionRepository.findById(id).get();
            if(promocion == null){
              throw new Exception("No existe la promoción con el id proporcionado");
            }
            if(promocion.getImagen() != null){
                promocion.setImagen(funcionalidades.convertirImagenABase64(promocion.getImagen()));
            }

            return convertToDto(promocion);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }


  public List<PromocionDto> obtenerPromocionesValidasPorSucursalYFecha(Long sucursalId) {
    LocalDate fechaActual = LocalDate.now();
    List<Promocion> promociones = promocionRepository.findPromocionesValidasPorSucursalYFecha(sucursalId, fechaActual);

    return promociones.stream()
            .filter(promocion -> tieneStockSuficiente(promocion))
            .map(promocion -> {
                PromocionDto dto = convertToDto(promocion);
                dto.setCantidadMaximaDisponible(calcularCantidadMaxima(promocion));
                return dto;
            })
            .collect(Collectors.toList());
}

private boolean tieneStockSuficiente(Promocion promocion) {
    for (PromocionDetalle detallePromocion : promocion.getPromocionDetalles()) {
        ArticuloManufacturado articuloManufacturado = detallePromocion.getArticuloManufacturado();
        for (ArticuloManufacturadoDetalle detalleManufacturado : articuloManufacturado.getArticuloManufacturadoDetalles()) {
            ArticuloInsumo insumo = detalleManufacturado.getArticuloInsumo();
            // La cantidad necesaria del insumo por cada unidad del artículo manufacturado
            int cantidadNecesariaPorUnidad = detalleManufacturado.getCantidad();
            // La cantidad total necesaria del insumo para la cantidad de la promoción
            int cantidadTotalNecesaria = cantidadNecesariaPorUnidad * detallePromocion.getCantidad();
            if (insumo.getStockActual() < cantidadTotalNecesaria) {
                return false; // No hay suficiente stock del insumo para esta promoción
            }
        }
    }
    return true; // Todos los insumos tienen suficiente stock
}

public int calcularCantidadMaxima(Promocion promocion) {
    int cantidadMaxima = Integer.MAX_VALUE;
    for (PromocionDetalle detallePromocion : promocion.getPromocionDetalles()) {
        ArticuloManufacturado articuloManufacturado = detallePromocion.getArticuloManufacturado();
        for (ArticuloManufacturadoDetalle detalleManufacturado : articuloManufacturado.getArticuloManufacturadoDetalles()) {
            ArticuloInsumo insumo = detalleManufacturado.getArticuloInsumo();
            int cantidadNecesariaPorUnidad = detalleManufacturado.getCantidad();
            int cantidadTotalNecesaria = cantidadNecesariaPorUnidad * detallePromocion.getCantidad();
            int cantidadMaximaPorInsumo = insumo.getStockActual() / cantidadTotalNecesaria;
            if (cantidadMaximaPorInsumo < cantidadMaxima) {
                cantidadMaxima = cantidadMaximaPorInsumo;
            }
        }
    }
    return cantidadMaxima == Integer.MAX_VALUE ? 0 : cantidadMaxima;
}

private List<ArticuloManufacturadoCantidadDto> calcularCantidadMaximaProducto(Promocion promocion) {
    List<ArticuloManufacturadoCantidadDto> cantidadesMaximas = new ArrayList<>();
    for (PromocionDetalle detallePromocion : promocion.getPromocionDetalles()) {
        ArticuloManufacturado articuloManufacturado = detallePromocion.getArticuloManufacturado();
        int cantidadMaxima = Integer.MAX_VALUE;
        for (ArticuloManufacturadoDetalle detalleManufacturado : articuloManufacturado.getArticuloManufacturadoDetalles()) {
            ArticuloInsumo insumo = detalleManufacturado.getArticuloInsumo();
            int cantidadNecesariaPorUnidad = detalleManufacturado.getCantidad();
            int cantidadTotalNecesaria = cantidadNecesariaPorUnidad * detallePromocion.getCantidad();
            int cantidadMaximaPorInsumo = insumo.getStockActual() / cantidadTotalNecesaria;
            if (cantidadMaximaPorInsumo < cantidadMaxima) {
                cantidadMaxima = cantidadMaximaPorInsumo;
            }
        }
        cantidadesMaximas.add(new ArticuloManufacturadoCantidadDto(articuloManufacturado.getId(), cantidadMaxima == Integer.MAX_VALUE ? 0 : cantidadMaxima));
    }
    return cantidadesMaximas;
}



}
