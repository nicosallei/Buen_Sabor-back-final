package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.Factura;
import NetDevops.BuenSabor.repository.IFacturaRepository;
import NetDevops.BuenSabor.service.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService implements IFacturaService {
    @Autowired
    private IFacturaRepository facturaRepository;

    @Override
    public Factura crearFactura(Factura factura) throws Exception {
        try {
            return facturaRepository.save(factura);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Factura buscarPorId(Long id) throws Exception {
        try {
            return facturaRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Factura actualizarFactura(Long idFcatura, Factura factura) throws Exception {
        try {

            Factura facturaActual = facturaRepository.findById(idFcatura).orElse(null);
            if (facturaActual == null) {
                return null;
            }
            factura.setId(idFcatura);
            return facturaRepository.save(factura);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean eliminarFactura(Long id) throws Exception {
        try {
            facturaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Factura> TraerFacturas() throws Exception {
        try {
            return facturaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
