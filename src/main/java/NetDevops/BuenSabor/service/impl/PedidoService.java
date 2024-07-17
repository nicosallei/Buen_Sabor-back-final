package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.dto.pedido.ArticuloManufacturadoDto;
import NetDevops.BuenSabor.dto.pedido.PedidoDetalleDto;
import NetDevops.BuenSabor.dto.pedido.PedidoDto;
import NetDevops.BuenSabor.entities.*;
import NetDevops.BuenSabor.enums.Estado;
import NetDevops.BuenSabor.enums.Rol;
import NetDevops.BuenSabor.repository.IArticuloManufacturadoRepository;
import NetDevops.BuenSabor.repository.IClienteRepository;
import NetDevops.BuenSabor.repository.IPedidoRepository;
import NetDevops.BuenSabor.service.IPedidoService;
import NetDevops.BuenSabor.service.util.EmailService;
import NetDevops.BuenSabor.service.util.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService implements IPedidoService {
    @Autowired
    private IPedidoRepository pedidoRepository;
@Autowired
private IArticuloManufacturadoRepository articuloManufacturadoRepository;
@Autowired
private PdfService pdfService;
@Autowired
private EmailService emailService;
@Autowired
private IClienteRepository clienteRepository;

    @Override
    public Pedido crearPedido(Pedido pedido) throws Exception {
        try {
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido actualizarPedido(Long id,Pedido pedido) throws Exception {
        try {
            Pedido pedidoActual = pedidoRepository.findById(id).orElse(null);
            if (pedidoActual == null) {
                return null;
            }
            pedido.setId(id);
            return pedidoRepository.save(pedido);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Pedido buscarPorId(Long id) throws Exception {
        try {
            return pedidoRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
    }

    @Override
    public boolean eliminarPedido(Long id) throws Exception {
        try {
            pedidoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

@Override
public List<PedidoDto> traerPedidos(Long sucursalId) throws Exception{
    try {
        List<Pedido> pedidos = pedidoRepository.findBySucursal_Id(sucursalId);
        return pedidos.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}

private PedidoDto convertToDto(Pedido pedido) {
    PedidoDto pedidoDto = new PedidoDto();
    pedidoDto.setId(pedido.getId());
    pedidoDto.setHora(pedido.getHora());
    pedidoDto.setTotal(pedido.getTotal());
    pedidoDto.setTotalCostoProduccion(pedido.getTotalCostoProduccion());
    pedidoDto.setEstado(pedido.getEstado());
    pedidoDto.setFormaPago(pedido.getFormaPago());
    pedidoDto.setTipoEnvio(pedido.getTipoEnvio());
    pedidoDto.setFechaPedido(pedido.getFechaPedido());
    pedidoDto.setPreferenceMPId(pedido.getPreferenceMPId());
    pedidoDto.setPedidoDetalleDto(pedido.getPedidoDetalle().stream()
        .map(this::convertDetalleToDto)
        .collect(Collectors.toList()));
    return pedidoDto;
}

private PedidoDetalleDto convertDetalleToDto(PedidoDetalle detalle) {
    PedidoDetalleDto detalleDto = new PedidoDetalleDto();
    detalleDto.setId(detalle.getId());
    detalleDto.setCantidad(detalle.getCantidad());
    detalleDto.setArticulo(convertArticuloToDto(articuloManufacturadoRepository.findById(detalle.getArticulo().getId()).orElse(null)));
    return detalleDto;
}

private ArticuloManufacturadoDto convertArticuloToDto(ArticuloManufacturado articulo) {
    ArticuloManufacturadoDto articuloDto = new ArticuloManufacturadoDto();
    articuloDto.setId(articulo.getId());
    articuloDto.setDenominacion(articulo.getDenominacion());
    return articuloDto;
}

    @Override
public List<Pedido> traerPedidos2(UsuarioEmpleado usuario) throws Exception{
    try {
        Rol rolActual = usuario.getEmpleado().getRol();
        List<Pedido> todosLosPedidos = pedidoRepository.findAll();

        switch (rolActual) {
            case ADMINISTRADOR:
                // El administrador puede ver todos los pedidos
                return todosLosPedidos;

            case EMPLEADO_CAJA:
                // El empleado de caja puede ver solo los pedidos de su sucursal
                return todosLosPedidos.stream()
                    .filter(pedido -> pedido.getSucursal().equals(usuario.getEmpleado().getSucursal()))
                    .collect(Collectors.toList());

            case EMPLEADO_REPARTIDOR:
                // El repartidor puede ver solo los pedidos de su empresa
                return todosLosPedidos.stream()
                    .filter(pedido -> pedido.getSucursal().getEmpresa().equals(usuario.getEmpleado().getSucursal().getEmpresa()))
                    .collect(Collectors.toList());

            default:
                // Otros roles no pueden ver ningún pedido
                return new ArrayList<>();
        }
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}


public PedidoDto cambiarEstadoPedido(Long id, Estado nuevoEstado) throws Exception {
    try {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        //Cliente cliente = clienteRepository.findById(pedido.getCliente().getId()).orElse(null);
        Cliente cliente = clienteRepository.findById(7L).orElse(null);
        if (pedido == null) {
            throw new Exception("Pedido no encontrado");
        }

        // Comprobar si el estado actual es FACTURADO
        if (pedido.getEstado() == Estado.ENTREGADO) {
            throw new Exception("No se puede cambiar el estado de un pedido ya facturado");
        } else if (pedido.getEstado() == Estado.CONFIRMADO && nuevoEstado == Estado.PENDIENTE) {

            throw new Exception("No se puede cambiar el estado de un pedido confirmado a pendiente");
        } else if(pedido.getEstado() == Estado.CANCELADO){
            throw new Exception("No se puede cambiar el estado de un pedido cancelado");
        }else if(nuevoEstado == Estado.ENTREGADO){

            // Generate PDF
            byte[] pdf = pdfService.createPdfPedido(pedido,cliente);
            // Send email
            String to = cliente.getEmail(); // replace with the customer's email
            String subject = "Pedido creado";
            String content = "Su pedido ha sido creado con éxito. Encuentra adjunta la factura.";
            emailService.sendEmailWithAttachment(to, subject, content, pdf);
        }

        pedido.setEstado(nuevoEstado);
        Pedido savedPedido = pedidoRepository.save(pedido);

        return convertToDto(savedPedido); // Convertir a PedidoDto antes de devolver
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }
}
}
