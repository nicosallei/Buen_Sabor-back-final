package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.entities.Factura;

import java.util.List;

public interface IFacturaService {
    public Factura crearFactura(Factura factura) throws Exception;
    public Factura buscarPorId(Long id) throws Exception;
    public Factura actualizarFactura(Long idFcatura,Factura factura) throws Exception;
    public boolean eliminarFactura(Long id) throws Exception;
    public List<Factura> TraerFacturas() throws Exception;

}
