package NetDevops.BuenSabor.service;

import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDto;
import NetDevops.BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoTablaDto;
import NetDevops.BuenSabor.entities.ArticuloManufacturado;

import java.util.List;
import java.util.Set;

public interface IArticuloManufacturadoService {
public ArticuloManufacturado cargarArticuloManufacturado(ArticuloManufacturado articuloManufacturado) throws Exception;
public ArticuloManufacturado buscarPorId(Long id) throws Exception;
public Set<ArticuloManufacturado> listaArticuloManufacturado() throws Exception;
public boolean eliminarArticuloManufacturado(Long id) throws Exception;
public ArticuloManufacturadoDto actualizarArticuloManufacturado(Long id, ArticuloManufacturado articuloManufacturado) throws Exception;
public Set<ArticuloManufacturadoTablaDto> tablaArticuloManufacturado() throws Exception;
public boolean reactivate(Long id) throws Exception;
public List<ArticuloManufacturado> traerTodos() throws Exception;
    public ArticuloManufacturadoDto traerArticuloBase64(Long id) throws Exception;
}
