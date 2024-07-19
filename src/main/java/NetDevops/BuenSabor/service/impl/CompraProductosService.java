package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.compraProducto.CompraPedidoDto;
import NetDevops.BuenSabor.dto.compraProducto.CompraProductoDto;
import NetDevops.BuenSabor.dto.compraProducto.PedidoDetalleDto;
import NetDevops.BuenSabor.entities.*;
import NetDevops.BuenSabor.enums.Estado;
import NetDevops.BuenSabor.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class CompraProductosService {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private IArticuloManufacturadoRepository articuloManufacturadoRepository;

    @Autowired
    private IAriticuloInsumoRepository articuloInsumoRepository;
    @Autowired
    private IArticuloRepository articuloRepository;
    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private IClienteRepository clienteRepository;

  public List<CompraProductoDto> findArticulosByCategoria(Long categoriaId) {
    // Obtener los artículos directamente asignados a la categoría padre
    List<ArticuloManufacturado> articulosManufacturadosPadre = articuloManufacturadoRepository.findByCategoriaIdAndEliminadoFalse(categoriaId);
    List<ArticuloInsumo> articulosInsumosPadre = articuloInsumoRepository.findByCategoriaIdAndEliminadoFalse(categoriaId);

    List<CompraProductoDto> articulos = new ArrayList<>();

    // Procesar los artículos de la categoría padre
    for (ArticuloManufacturado articulo : articulosManufacturadosPadre) {
        CompraProductoDto dto = convertToDto(articulo);
        articulos.add(dto);
    }
    for (ArticuloInsumo articulo : articulosInsumosPadre) {
        CompraProductoDto dto = convertToDto(articulo);
        articulos.add(dto);
    }

    // Obtener y procesar los artículos de las subcategorías
    Set<Categoria> subcategorias = categoriaRepository.findByCategoriaPadre_IdAndEliminadoFalse(categoriaId);
    for (Categoria subcategoria : subcategorias) {
        List<ArticuloManufacturado> articulosManufacturados = articuloManufacturadoRepository.findByCategoriaIdAndEliminadoFalse(subcategoria.getId());
        List<ArticuloInsumo> articulosInsumos = articuloInsumoRepository.findByCategoriaIdAndEliminadoFalse(subcategoria.getId());

        for (ArticuloManufacturado articulo : articulosManufacturados) {
            boolean canBeCreated = true;
            for (ArticuloManufacturadoDetalle detalle : articulo.getArticuloManufacturadoDetalles()) {
                ArticuloInsumo insumo = detalle.getArticuloInsumo();
                if (insumo.getStockActual() < detalle.getCantidad()) {
                    canBeCreated = false;
                    break;
                }
            }
            if (canBeCreated) {
                CompraProductoDto dto = convertToDto(articulo);
                articulos.add(dto);
            }
        }

        for (ArticuloInsumo articulo : articulosInsumos) {
            CompraProductoDto dto = convertToDto(articulo);
            articulos.add(dto);
        }
    }

    return articulos;
}

private CompraProductoDto convertToDto(Articulo articulo) {
    ArticuloManufacturado producto = articuloManufacturadoRepository.findById(articulo.getId()).orElse(null);
    CompraProductoDto dto = new CompraProductoDto();
    dto.setId(articulo.getId());
    dto.setDenominacion(articulo.getDenominacion());
    dto.setDescripcion(articulo.getDescripcion());
    dto.setCodigo(articulo.getCodigo());
    dto.setPrecioVenta(articulo.getPrecioVenta());
    if(producto != null){
        dto.setPreparacion(producto.getPreparacion());
        dto.setTiempoEstimadoMinutos(producto.getTiempoEstimadoMinutos());
        // Calcular la cantidad máxima de compra basada en el stock de insumos
        Long cantidadMaximaCompra = Long.valueOf(producto.getArticuloManufacturadoDetalles().stream()
                .map(detalle -> detalle.getArticuloInsumo().getStockActual() / detalle.getCantidad())
                .min(Long::compare).orElse(0));
        dto.setCantidadMaximaCompra(cantidadMaximaCompra);
    }


    List<ImagenArticulo> processedImages = new ArrayList<>();
    for (ImagenArticulo imagen : articulo.getImagenes()) {
        String imagePath = imagen.getUrl();
        imagePath = imagePath.replace("src\\main\\resources\\images\\", "");
        imagen.setUrl(imagePath);
        processedImages.add(imagen);
    }
    dto.setImagenes(processedImages); // Convert Set to List

    dto.setCategoriaId(articulo.getCategoria().getId());
    if (articulo.getSucursal() != null) {
        dto.setSucursalId(articulo.getSucursal().getId());
    }
    return dto;
}

    public CompraProductoDto buscarArticuloPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Articulo no encontrado con id: " + id));

        CompraProductoDto dto = new CompraProductoDto();
        dto.setId(articulo.getId());
        dto.setDenominacion(articulo.getDenominacion());
        dto.setDescripcion(articulo.getDescripcion());
        dto.setCodigo(articulo.getCodigo());
        dto.setPrecioVenta(articulo.getPrecioVenta());
        dto.setImagenes(new ArrayList<>(articulo.getImagenes()));
        dto.setCategoriaId(articulo.getCategoria().getId());
        if (articulo.getSucursal() != null) {
            dto.setSucursalId(articulo.getSucursal().getId());
        }
        return dto;

    }




    public CompraPedidoDto crearPedido(CompraPedidoDto compraPedidoDto) throws Exception {
    try {

        Map<Long, Integer> insumosNecesarios = new HashMap<>();
        int tiempoTotalEspera = 0;
        // Calcular la cantidad total necesaria de cada insumo
        for (PedidoDetalleDto detalleDto : compraPedidoDto.getPedidoDetalle()) {
            Articulo articulo = articuloRepository.findById(detalleDto.getProducto().getId())
                    .orElseThrow(() -> new NoSuchElementException("Articulo no encontrado con id: " + detalleDto.getProducto().getId()));

            if (articulo instanceof ArticuloInsumo) {
                insumosNecesarios.merge(articulo.getId(), detalleDto.getCantidad(), Integer::sum);
            } else if (articulo instanceof ArticuloManufacturado) {
                ArticuloManufacturado articuloManufacturado = (ArticuloManufacturado) articulo;
                // Paso 4: Sumar el tiempo estimado de preparación del ArticuloManufacturado
                tiempoTotalEspera += articuloManufacturado.getTiempoEstimadoMinutos() * detalleDto.getCantidad();
                for (ArticuloManufacturadoDetalle detalle : ((ArticuloManufacturado) articulo).getArticuloManufacturadoDetalles()) {
                    insumosNecesarios.merge(detalle.getArticuloInsumo().getId(), detalle.getCantidad() * detalleDto.getCantidad(), Integer::sum);
                }
            }
        }

        // Verificar que hay suficiente stock para cada insumo
        for (Map.Entry<Long, Integer> entry : insumosNecesarios.entrySet()) {
            ArticuloInsumo insumo = articuloInsumoRepository.findById(entry.getKey())
                    .orElseThrow(() -> new NoSuchElementException("Insumo no encontrado con id: " + entry.getKey()));
            if (insumo.getStockActual() < entry.getValue()) {
                throw new Exception("Stock insuficiente para el insumo: " + insumo.getDenominacion());
            }
        }


        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDate.now());
        pedido.setHora(LocalTime.now());
        pedido.setTotal(compraPedidoDto.getTotal());
        pedido.setDomicilio(compraPedidoDto.getDomicilio());
        pedido.setTipoEnvio(compraPedidoDto.getTipoEnvio());
        pedido.setFormaPago(compraPedidoDto.getFormaPago());
        pedido.setCliente(clienteRepository.findById(compraPedidoDto.getCliente().getId())
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con id: " + compraPedidoDto.getCliente().getId())));

        List<PedidoDetalle> pedidoDetalles = new ArrayList<>();
        for (PedidoDetalleDto detalleDto : compraPedidoDto.getPedidoDetalle()) {
            Articulo articulo = articuloRepository.findById(detalleDto.getProducto().getId())
                    .orElseThrow(() -> new NoSuchElementException("Articulo no encontrado con id: " + detalleDto.getProducto().getId()));

            if (articulo instanceof ArticuloInsumo) {
                ArticuloInsumo articuloInsumo = (ArticuloInsumo) articulo;
                // Subtract the quantity from the stock of the ArticuloInsumo
                articuloInsumo.setStockActual(articuloInsumo.getStockActual() - detalleDto.getCantidad());
                // Save the updated ArticuloInsumo in the database
                articuloRepository.save(articuloInsumo);
            } else if (articulo instanceof ArticuloManufacturado) {
                ArticuloManufacturado articuloManufacturado = (ArticuloManufacturado) articulo;
                for (ArticuloManufacturadoDetalle detalle : articuloManufacturado.getArticuloManufacturadoDetalles()) {
                    ArticuloInsumo articuloInsumo = detalle.getArticuloInsumo();
                    // Subtract the quantity needed for the manufacturing from the stock of the ArticuloInsumo
                    articuloInsumo.setStockActual(articuloInsumo.getStockActual() - detalle.getCantidad());
                    // Save the updated ArticuloInsumo in the database
                    articuloRepository.save(articuloInsumo);
                }
            }

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setArticulo(articulo);
            detalle.setCantidad(detalleDto.getCantidad());
            detalle.setPedido(pedido);
            pedidoDetalles.add(detalle);
        }
        pedido.setPedidoDetalle(pedidoDetalles);
        pedido.setSucursal(pedidoDetalles.get(0).getArticulo().getSucursal());
        pedido.setEstado(Estado.PENDIENTE);
        pedidoRepository.save(pedido);
        CompraPedidoDto pedidoDto = modelMapper.map(pedido, CompraPedidoDto.class);
        pedidoDto.setTiempoEspera(tiempoTotalEspera);
        return pedidoDto;
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}


}
