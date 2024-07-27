package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoTablaDto;
import NetDevops.BuenSabor.dto.categoria.CategoriaDto;
import NetDevops.BuenSabor.dto.categoria.SubCategoriaDto;
import NetDevops.BuenSabor.dto.promocion.ArticuloPromocionDto;
import NetDevops.BuenSabor.dto.promocion.PromocionDetalleDto;
import NetDevops.BuenSabor.dto.promocion.PromocionDto;
import NetDevops.BuenSabor.dto.sucursal.SucursalSimpleDto;
import NetDevops.BuenSabor.entities.*;
import NetDevops.BuenSabor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class LocalService {
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private ISucursalRepository sucursalRepository;
    @Autowired
    private IAriticuloInsumoRepository articuloInsumoRepository;
    @Autowired
    private IPromocionRepository promocionRepository;
    @Autowired
    private IPromocionDetalleRepository promocionDetalleRepository;
    @Autowired
    private IArticuloManufacturadoRepository articuloManufacturadoRepository;
    @Autowired
    private IEmpresaRepository empresaRepository;

//region  Categoria
public Set<CategoriaDto> traerTodoCategoria(Long sucursalId) throws Exception {
    try {
        Set<Categoria> listaCategoriaOriginal = categoriaRepository.findBySucursales_Id(sucursalId);
        Set<CategoriaDto> listaDto = new HashSet<>();
        for (Categoria categoria : listaCategoriaOriginal) {
            // Solo agregar a la lista las categorías que no tienen una categoría padre
            if (categoria.getCategoriaPadre() == null) {
                CategoriaDto categoriaDto = new CategoriaDto();
                categoriaDto.setDenominacion(categoria.getDenominacion());
                categoriaDto.setUrlIcono(categoria.getUrlIcono());
                categoriaDto.setId(categoria.getId());
                categoriaDto.setEliminado(categoria.isEliminado());


                for (Sucursal sucursal : categoria.getSucursales()) {
                    SucursalSimpleDto sucursalSimpleDto = new SucursalSimpleDto();
                    sucursalSimpleDto.setNombre(sucursal.getNombre());
                    sucursalSimpleDto.setId(sucursal.getId());
                    categoriaDto.getSucursales().add(sucursalSimpleDto);
                }

                Set<Categoria> subCategorias = categoriaRepository.findByCategoriaPadre_Id(categoria.getId());
                for (Categoria subCategoria : subCategorias) {
                    if (subCategoria.getCategoriaPadre() != null && subCategoria.getCategoriaPadre().getId().equals(categoria.getId())) {
                        for (Sucursal sucursal : subCategoria.getSucursales()){
                            if (sucursal.getId().equals(sucursalId)){
                                SubCategoriaDto subCategoriaDto = agregarSubCategoriasRecursivamente(subCategoria, sucursalId);
                                categoriaDto.getSubCategoriaDtos().add(subCategoriaDto);
                            }

                        }
                    }
                }
                listaDto.add(categoriaDto);
            }
        }
        return listaDto;
    } catch (Exception e) {
        throw new Exception(e);
    }
}



   public Set<CategoriaDto> traerTodo(Long sucursalId) throws Exception {
    try {
        Set<Categoria> listaCategoriaOriginal = categoriaRepository.findBySucursales_Id(sucursalId);
        Set<CategoriaDto> listaDto = new HashSet<>();
        for (Categoria categoria : listaCategoriaOriginal) {


                CategoriaDto categoriaDto = new CategoriaDto();
                categoriaDto.setDenominacion(categoria.getDenominacion());
                categoriaDto.setUrlIcono(categoria.getUrlIcono());
                categoriaDto.setId(categoria.getId());
                categoriaDto.setEliminado(categoria.isEliminado());


                for (Sucursal sucursal : categoria.getSucursales()) {
                    SucursalSimpleDto sucursalSimpleDto = new SucursalSimpleDto();
                    sucursalSimpleDto.setNombre(sucursal.getNombre());
                    sucursalSimpleDto.setId(sucursal.getId());
                    categoriaDto.getSucursales().add(sucursalSimpleDto);
                }

                Set<Categoria> subCategorias = categoriaRepository.findByCategoriaPadre_Id(categoria.getId());
                for (Categoria subCategoria : subCategorias) {
                    if (subCategoria.getCategoriaPadre() != null && subCategoria.getCategoriaPadre().getId().equals(categoria.getId())) {
                        for (Sucursal sucursal : subCategoria.getSucursales()){
                            if (sucursal.getId().equals(sucursalId)){
                                SubCategoriaDto subCategoriaDto = agregarSubCategoriasRecursivamente(subCategoria, sucursalId);
                                categoriaDto.getSubCategoriaDtos().add(subCategoriaDto);
                            }

                        }
                    }
                }
                listaDto.add(categoriaDto);
            }

        return listaDto;
    } catch (Exception e) {
        throw new Exception(e);
    }
}

private SubCategoriaDto agregarSubCategoriasRecursivamente(Categoria categoria, Long sucursalId) {
    SubCategoriaDto subCategoriaDto = new SubCategoriaDto();
    subCategoriaDto.setDenominacion(categoria.getDenominacion());
    subCategoriaDto.setUrlIcono(categoria.getUrlIcono());
    subCategoriaDto.setId(categoria.getId());
    subCategoriaDto.setIdCategoriaPadre(categoria.getCategoriaPadre() != null ? categoria.getCategoriaPadre().getId() : null);
    subCategoriaDto.setEliminado(categoria.isEliminado());

    for (Sucursal sucursal : categoria.getSucursales()) {
        SucursalSimpleDto sucursalSimpleDto = new SucursalSimpleDto();
        sucursalSimpleDto.setId(sucursal.getId());
        sucursalSimpleDto.setNombre(sucursal.getNombre());
        subCategoriaDto.getSucursales().add(sucursalSimpleDto);
    }

    Set<Categoria> subCategorias = categoriaRepository.findByCategoriaPadre_IdAndSucursales_Id(categoria.getId(), sucursalId);
    for (Categoria subCategoria : subCategorias) {
        if (subCategoria.getCategoriaPadre() != null && subCategoria.getCategoriaPadre().getId().equals(categoria.getId())) {
            SubCategoriaDto subSubCategoriaDto = agregarSubCategoriasRecursivamente(subCategoria, sucursalId);
            subCategoriaDto.getSubSubCategoriaDtos().add(subSubCategoriaDto);
        }
    }
    return subCategoriaDto;
}

   public Categoria agregarSucursalACategoria(Long categoriaId, Long sucursalId) throws Exception {
    Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
    Sucursal sucursal = sucursalRepository.findById(sucursalId).orElse(null);

    if (categoria == null || sucursal == null) {
        throw new Exception("La categoría o la sucursal no existen");
    }

    categoria.getSucursales().add(sucursal);
    agregarSucursalASubcategorias(categoria, sucursal);

    return categoriaRepository.save(categoria);
}

private void agregarSucursalASubcategorias(Categoria categoria, Sucursal sucursal) {
    for (Categoria subCategoria : categoria.getSubCategorias()) {
        subCategoria.getSucursales().add(sucursal);

        agregarSucursalASubcategorias(subCategoria, sucursal);
    }
}

public CategoriaDto desasociarSucursalDeCategoria(Long categoriaId, Long sucursalId) throws Exception {
    Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
    Sucursal sucursal = sucursalRepository.findById(sucursalId).orElse(null);

    if (categoria == null || sucursal == null) {
        throw new Exception("La categoría o la sucursal no existen");
    }

    categoria.getSucursales().remove(sucursal);

    desasociarSucursalDeSubcategorias(categoria, sucursal);
    categoriaRepository.save(categoria);
    CategoriaDto categoriaDto = new CategoriaDto();
    categoriaDto.setDenominacion(categoria.getDenominacion());
    categoriaDto.setUrlIcono(categoria.getUrlIcono());
    categoriaDto.setId(categoria.getId());
    categoriaDto.setEliminado(categoria.isEliminado());
    return categoriaDto;

}

private void desasociarSucursalDeSubcategorias(Categoria categoria, Sucursal sucursal) {
    for (Categoria subCategoria : categoria.getSubCategorias()) {
        subCategoria.getSucursales().remove(sucursal);

        desasociarSucursalDeSubcategorias(subCategoria, sucursal);
    }
}

public Set<CategoriaDto> traerCategoriasNoAsociadasASucursal(Long sucursalId, Long empresaId) throws Exception {
    try {
        Sucursal sucursal = sucursalRepository.findById(sucursalId).orElse(null);
        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
        if (sucursal == null || empresa == null) {
            throw new Exception("La sucursal o la empresa no existen");
        }

        Set<Categoria> listaCategoriaOriginal = categoriaRepository.findBySucursalesNotContainsAndEmpresa(sucursal, empresa);
        Set<CategoriaDto> listaDto = new HashSet<>();
        for (Categoria lista: listaCategoriaOriginal){
            if (lista.getCategoriaPadre() == null) {
                CategoriaDto categoriadto = new CategoriaDto();
                categoriadto.setDenominacion(lista.getDenominacion());
                categoriadto.setUrlIcono(lista.getUrlIcono());
                categoriadto.setId(lista.getId());
                categoriadto.setEliminado(lista.isEliminado());

                Set<Categoria> subCategorias = categoriaRepository.findByCategoriaPadre_IdAndSucursalesNotContainsAndEmpresa(lista.getId(), sucursal, empresa);

                for (Categoria subCategoria : subCategorias) {
                    SubCategoriaDto subCategoriaDto = agregarSubCategoriasNoAsociadasASucursalRecursivamente(subCategoria, sucursalId, empresaId);
                    categoriadto.getSubCategoriaDtos().add(subCategoriaDto);
                }
                listaDto.add(categoriadto);
            }
        }
        return listaDto;
    } catch (Exception e) {
        throw new Exception(e);
    }
}

private SubCategoriaDto agregarSubCategoriasNoAsociadasASucursalRecursivamente(Categoria categoria, Long sucursalId, Long empresaId) throws Exception {
    Sucursal sucursal = sucursalRepository.findById(sucursalId).orElse(null);
    Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
    if (sucursal == null || empresa == null) {
        throw new Exception("La sucursal o la empresa no existen");
    }

    SubCategoriaDto subCategoriaDto = new SubCategoriaDto();
    subCategoriaDto.setDenominacion(categoria.getDenominacion());
    subCategoriaDto.setUrlIcono(categoria.getUrlIcono());
    subCategoriaDto.setId(categoria.getId());
    subCategoriaDto.setIdCategoriaPadre(categoria.getCategoriaPadre() != null ? categoria.getCategoriaPadre().getId() : null);
    subCategoriaDto.setEliminado(categoria.isEliminado());

    Set<Categoria> subCategorias = categoriaRepository.findByCategoriaPadre_IdAndSucursalesNotContainsAndEmpresa(categoria.getId(), sucursal, empresa);
    for (Categoria subCategoria : subCategorias) {
        if (subCategoria.getCategoriaPadre() != null && subCategoria.getCategoriaPadre().getId().equals(categoria.getId())) {
            SubCategoriaDto subSubCategoriaDto = agregarSubCategoriasNoAsociadasASucursalRecursivamente(subCategoria, sucursalId, empresaId);
            subCategoriaDto.getSubSubCategoriaDtos().add(subSubCategoriaDto);
        }
    }
    return subCategoriaDto;
}
//endregion

//region Promociones
public List<PromocionDto> buscarPromocionesActivas() throws Exception {
    try {
        LocalDate hoy = LocalDate.now();
        List<Promocion> promociones = promocionRepository.findByEliminadoFalseAndFechaHastaAfter(hoy);
        List<PromocionDto> dtos = new ArrayList<>();
        for (Promocion promocion : promociones) {
            dtos.add(convertToDto(promocion));
        }
        return dtos;
    } catch (Exception e) {
        throw new Exception("Error al buscar promociones activas: " + e.getMessage(), e);
    }
}


    public List<PromocionDto> buscarPromocionesPorSucursal(Long sucursalId) throws Exception{
    try{
        List<Promocion> promociones = promocionRepository.findBySucursales_Id(sucursalId);
        List<PromocionDto> dtos = new ArrayList<>();
        for (Promocion promocion : promociones) {

           if (promocion.getImagen() != null && !promocion.getImagen().isEmpty()) {
                String imagePath = promocion.getImagen();
                    imagePath = imagePath.replace("src\\main\\resources\\images\\", "");
                    promocion.setImagen(imagePath);
           }


            dtos.add(convertToDto(promocion));
        }
        return dtos;
    } catch (Exception e) {
        throw new Exception(e.getMessage() + "No se pudo encontrar la promocion");
    }
}
public List<PromocionDetalleDto> buscarDetallesPorPromocion(Long promocionId) {
    List<PromocionDetalle> promocionDetalles = promocionDetalleRepository.findByPromocion_Id(promocionId);
    List<PromocionDetalleDto> dtos = new ArrayList<>();
    for (PromocionDetalle promocionDetalle : promocionDetalles) {
        if (!promocionDetalle.isEliminado()) {
            dtos.add(convertToDto(promocionDetalle));
        }
    }
    return dtos;
}

    public PromocionDto getById(Long id) throws Exception {
        try {
            Promocion promocion = promocionRepository.findById(id).get();
            return convertToDto(promocion);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

//region Convertir a DTO
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
    if(promocion.getImagen() != null){
        dto.setImagen(promocion.getImagen());
    }

    //dto.setSucursales(promocion.getSucursales());
    for (PromocionDetalle promocionDetalle : promocion.getPromocionDetalles()) {
        dto.getPromocionDetallesDto().add(convertToDto(promocionDetalle));
    }
    return dto;
}


public ArticuloPromocionDto convertToDto(ArticuloManufacturado articuloManufacturado) {
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

public PromocionDetalleDto convertToDto(PromocionDetalle promocionDetalle) {
    PromocionDetalleDto dto = new PromocionDetalleDto();
    dto.setId(promocionDetalle.getId());
    dto.setEliminado(promocionDetalle.isEliminado());
    dto.setCantidad(promocionDetalle.getCantidad());
    dto.setArticuloManufacturadoDto(convertToDto(promocionDetalle.getArticuloManufacturado()));
    dto.setImagenPromocion(promocionDetalle.getImagenPromocion());
    return dto;
}
//endregion

//endregion

//region Articulos Insumos
    public Set<ArticuloInsumo> traerArticulosInsumoPorSucursal(Long sucursalId) throws Exception {
       try {
           return articuloInsumoRepository.findBySucursal_Id(sucursalId);
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }
    }

public ArticuloInsumo aumentarStock(Long id, Integer cantidad, Double nuevoPrecioVenta, Double nuevoPrecioCompra) throws Exception {
    try {
        // Buscar el ArticuloInsumo en la base de datos
        Optional<ArticuloInsumo> optionalArticuloInsumo = articuloInsumoRepository.findById(id);
        if (!optionalArticuloInsumo.isPresent()) {
            throw new Exception("No se encontró el ArticuloInsumo con id " + id);
        }

        // Aumentar el stockActual y actualizar los precios
        ArticuloInsumo articuloInsumo = optionalArticuloInsumo.get();
        articuloInsumo.setStockActual(articuloInsumo.getStockActual() + cantidad);
        articuloInsumo.setPrecioVenta(nuevoPrecioVenta);
        articuloInsumo.setPrecioCompra(nuevoPrecioCompra);

        // Guardar el ArticuloInsumo actualizado en la base de datos
        return articuloInsumoRepository.save(articuloInsumo);
    } catch (Exception e) {
        throw new Exception("Error al aumentar el stock y actualizar los precios: " + e.getMessage());
    }
}





    //endregion

//region Articulo Manufacturado

   public List<ArticuloManufacturadoTablaDto> buscarArticulosPorSucursal(Long sucursalId) throws Exception {
    try {

        List<ArticuloManufacturado> articulos = articuloManufacturadoRepository.findBySucursal_Id(sucursalId);
        if (articulos.isEmpty()) {
            throw new Exception("No se encontraron ArticulosManufacturados para la Sucursal con id " + sucursalId);
        }
        List<ArticuloManufacturadoTablaDto> articulosDto = new ArrayList<>();
        for (ArticuloManufacturado articulo : articulos) {
            articulosDto.add(convertirArticuloManufacturadoToDto(articulo));
        }
        return articulosDto;
    } catch (Exception e) {
        throw new Exception("Error al buscar los ArticulosManufacturados: " + e.getMessage());
    }
}

    //region Convertir a DTO
   public ArticuloManufacturadoTablaDto convertirArticuloManufacturadoToDto(ArticuloManufacturado articulo) {
    ArticuloManufacturadoTablaDto dto = new ArticuloManufacturadoTablaDto();
    dto.setId(articulo.getId());
    dto.setCodigo(articulo.getCodigo());
    dto.setDenominacion(articulo.getDenominacion());
    dto.setPreparacion(articulo.getPreparacion());

    dto.setEliminado(articulo.isEliminado());
    if (!articulo.getImagenes().isEmpty()) {
        ImagenArticulo primeraImagen = articulo.getImagenes().iterator().next();
        String imagePath = primeraImagen.getUrl();
        imagePath = imagePath.replace("src\\main\\resources\\images\\", "");
        dto.setImagen(imagePath);
    }

    dto.setPrecioVenta(articulo.getPrecioVenta());
    dto.setDescripcion(articulo.getDescripcion());
    dto.setTiempoEstimadoCocina(articulo.getTiempoEstimadoMinutos());
    return dto;
}

    //endregion


//endregion


}



