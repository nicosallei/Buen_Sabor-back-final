package NetDevops.BuenSabor.mapeosDto;

import NetDevops.BuenSabor.dto.articuloInsumo.ArticuloInsumoDto;
import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDetalleDto;
import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDto;
import NetDevops.BuenSabor.dto.categoria.CategoriaDto;
import NetDevops.BuenSabor.entities.ArticuloInsumo;
import NetDevops.BuenSabor.entities.ArticuloManufacturado;
import NetDevops.BuenSabor.entities.ArticuloManufacturadoDetalle;
import NetDevops.BuenSabor.entities.Categoria;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class MapeoDto {


    public ArticuloManufacturadoDto convertManufacturadoDto(ArticuloManufacturado articuloManufacturado) {
        ArticuloManufacturadoDto dto = new ArticuloManufacturadoDto();
        dto.setId(articuloManufacturado.getId());
        dto.setDenominacion(articuloManufacturado.getDenominacion());
        dto.setDescripcion(articuloManufacturado.getDescripcion());
        dto.setPrecioVenta(articuloManufacturado.getPrecioVenta());
        dto.setCodigo(articuloManufacturado.getCodigo());
        dto.setImagenes(articuloManufacturado.getImagenes());
        dto.setUnidadMedida(articuloManufacturado.getUnidadMedida());
        dto.setTiempoEstimadoMinutos(articuloManufacturado.getTiempoEstimadoMinutos());
        dto.setPreparacion(articuloManufacturado.getPreparacion());
        dto.setCategoria(convertCategoriaDto(articuloManufacturado.getCategoria()));


        // Convert ArticuloManufacturadoDetalle to ArticuloManufacturadoDetalleDto
        Set<ArticuloManufacturadoDetalleDto> detallesDto = new HashSet<>();
        for (ArticuloManufacturadoDetalle detalle : articuloManufacturado.getArticuloManufacturadoDetalles()) {
            ArticuloManufacturadoDetalleDto detalleDto = new ArticuloManufacturadoDetalleDto();
            detalleDto.setId(detalle.getId());
            detalleDto.setCantidad(detalle.getCantidad());
            detalleDto.setArticuloInsumoDto(convertInsumoDto(detalle.getArticuloInsumo())); //
            detallesDto.add(detalleDto);
        }
        dto.setArticuloManufacturadoDetallesDto(detallesDto);

        return dto;
    }
    public ArticuloInsumoDto convertInsumoDto(ArticuloInsumo articuloInsumo) {
        ArticuloInsumoDto dto = new ArticuloInsumoDto();
        dto.setId(articuloInsumo.getId());
        dto.setDenominacion(articuloInsumo.getDenominacion());
        dto.setDescripcion(articuloInsumo.getDescripcion());
        dto.setPrecioCompra(articuloInsumo.getPrecioCompra());
        dto.setPrecioVenta(articuloInsumo.getPrecioVenta());
        dto.setStockActual(articuloInsumo.getStockActual());
        dto.setStockMaximo(articuloInsumo.getStockMaximo());
        dto.setEsParaElaborar(articuloInsumo.getEsParaElaborar());
        dto.setStockMinimo(articuloInsumo.getStockMinimo());
        dto.setCodigo(articuloInsumo.getCodigo());
        //dto.setImagenes(articuloInsumo.getImagenes());
        dto.setUnidadMedida(articuloInsumo.getUnidadMedida());

        return dto;
    }
    public CategoriaDto convertCategoriaDto(Categoria categoria) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setDenominacion(categoria.getDenominacion());
        //dto.setUrlIcono(categoria.getUrlIcono());
        //dto.setSucursales(categoria.getSucursales());
        //dto.setSubCategoriaDtos(categoria.getSubCategoriaDtos());
        return dto;
    }


}
