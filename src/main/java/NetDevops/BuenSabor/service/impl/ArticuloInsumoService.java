package NetDevops.BuenSabor.service.impl;

import NetDevops.BuenSabor.entities.ArticuloInsumo;
import NetDevops.BuenSabor.entities.ImagenArticulo;
import NetDevops.BuenSabor.repository.IAriticuloInsumoRepository;
import NetDevops.BuenSabor.repository.IArticuloManufacturadoDetalleRepository;
import NetDevops.BuenSabor.repository.ImagenArticuloRepository;
import NetDevops.BuenSabor.service.IArticuloInsumoService;
import NetDevops.BuenSabor.service.funcionalidades.Funcionalidades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ArticuloInsumoService implements IArticuloInsumoService {
    private final Path root = Paths.get("images");

    @Autowired
    private IAriticuloInsumoRepository articuloInsumoRepository;
    @Autowired
    private ImagenArticuloRepository imagenRepository;
    @Autowired
    private IArticuloManufacturadoDetalleRepository articuloManufacturadoRepository;
    @Autowired
    Funcionalidades funcionalidades;

    //region Eliminacion
    @Override
    public boolean deleteById(Long id) throws Exception {

        try {
            ArticuloInsumo articuloInsumo = articuloInsumoRepository.findById(id).orElseThrow(() -> new Exception("No se encontro el articulo"));

            // Verificar si el ArticuloInsumo está asignado a algún ArticuloManufacturado
            if (articuloManufacturadoRepository.existsByArticuloInsumo_IdAndEliminadoFalse(id)) {
                throw new Exception("No se puede eliminar el articulo porque está asignado a un ArticuloManufacturado");
            }

            // Marcar las imágenes correspondientes al ArticuloInsumo como eliminadas
            Set<ImagenArticulo> imagenes = articuloInsumo.getImagenes();
            if (imagenes != null) {
                for (ImagenArticulo imagen : imagenes) {
                    imagen.setEliminado(true);
                    imagenRepository.save(imagen);
                }
            }

            articuloInsumo.setEliminado(true);
            articuloInsumoRepository.save(articuloInsumo);
            return true;

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    //endregion

    //region Mostrar Lista
    @Override
public List<ArticuloInsumo> mostrarLista() throws Exception {
    try {
        List<ArticuloInsumo> articulos = articuloInsumoRepository.findByEliminadoFalse();
        for (ArticuloInsumo articulo : articulos) {
            if (articulo.getImagenes() != null) {
                for (ImagenArticulo imagen : articulo.getImagenes()) {
                    String imagenBase64 = funcionalidades.convertirImagenABase64(imagen.getUrl());
                    imagen.setUrl(imagenBase64); // Actualizar el campo url en ImagenArticulo con la imagen en base64
                }
            }
        }
        return articulos;
    } catch (Exception e) {
        throw new Exception(e);
    }
}


//    @Override
//    public List<ArticuloInsumo> mostrarLista() throws Exception {
//        try {
//            List<ArticuloInsumo> articulos = articuloInsumoRepository.findByEliminadoFalse();
//            for (ArticuloInsumo articulo : articulos) {
//                if (articulo.getImagenes() != null) {
//                    for (ImagenArticulo imagen : articulo.getImagenes()) {
//                        try {
//                            String imagenBase64 = funcionalidades.convertirImagenABase64(imagen.getUrl());
//                            imagen.setUrl(imagenBase64); // Actualizar el campo url en ImagenArticulo con la imagen en base64
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//            }
//            return articulos;
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
//    }
    //endregion

    //region Cargar
   @Override
public ArticuloInsumo cargar(ArticuloInsumo articuloInsumo) throws Exception {
    try {
        Long sucursalId = articuloInsumo.getSucursal().getId();

        if(articuloInsumoRepository.existsByCodigoAndSucursal_Id(articuloInsumo.getCodigo(), sucursalId)){
            throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
        }
        if (articuloInsumoRepository.existsByDenominacionAndSucursal_Id(articuloInsumo.getDenominacion(), sucursalId)){
            throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
        }


        if (articuloInsumo.getImagenes() != null) {
            for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                // Generar un nombre de archivo único para cada imagen
                String filename = UUID.randomUUID().toString() + ".jpg";

                // Utilizar la función guardarImagen de Funcionalidades para guardar la imagen
                String ruta = funcionalidades.guardarImagen(imagen.getUrl(), filename);

                // Actualizar el campo url en ImagenArticulo
                imagen.setUrl(ruta);
                imagen.setArticulo(articuloInsumo);
            }
        }

        articuloInsumoRepository.save(articuloInsumo);
        return articuloInsumoRepository.findById(articuloInsumo.getId()).get();

    } catch (Exception e) {
        throw new Exception(e);
    }
}

    //endregion

    //region Buscar Por Id
    @Override
public ArticuloInsumo buscarPorId(Long id) throws Exception {
    try {
        ArticuloInsumo articuloInsumo = articuloInsumoRepository.findByIdAndEliminadoFalse(id);
        if (articuloInsumo == null) {
            throw new Exception("No se encontro el articulo");
        }

        if (articuloInsumo.getImagenes() != null) {
            for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                String imagenBase64 = funcionalidades.convertirImagenABase64(imagen.getUrl());
                imagen.setUrl(imagenBase64); // Actualizar el campo url en ImagenArticulo con la imagen en base64
            }
        }

        return articuloInsumo;
    } catch (Exception e) {
        throw new Exception(e);
    }
}

    //endregion

    //region Actualizar
 @Override
public ArticuloInsumo actualizar(Long id, ArticuloInsumo articuloInsumo) throws Exception {
    try {
        if (!articuloInsumoRepository.existsById(id)){
            throw new Exception("No se encontro el articulo");
        }

        ArticuloInsumo articuloInsumoViejo = articuloInsumoRepository.findById(id).get();
        Long sucursalId = articuloInsumoViejo.getSucursal().getId();

        if(articuloInsumoRepository.existsByCodigoAndSucursal_Id(articuloInsumo.getCodigo(), sucursalId) && !articuloInsumo.getCodigo().equals(articuloInsumoViejo.getCodigo())){
            throw new Exception("Ya existe un articulo con ese codigo en la misma sucursal");
        }
        if (articuloInsumoRepository.existsByDenominacionAndSucursal_Id(articuloInsumo.getDenominacion(), sucursalId) && !articuloInsumo.getDenominacion().equals(articuloInsumoViejo.getDenominacion())){
            throw new Exception("Ya existe un articulo con esa denominacion en la misma sucursal");
        }


        //region Logica para eliminar Imagenes
        Set<ImagenArticulo> imagenesViejas = imagenRepository.findByArticulo_Id(id);
        Set<ImagenArticulo> imagenesNuevas = articuloInsumo.getImagenes();

        imagenesViejas.forEach(imagenVieja -> {
            if (!imagenesNuevas.contains(imagenVieja)) {
                imagenVieja.setEliminado(true);
                imagenVieja.setArticulo(null);
                imagenRepository.save(imagenVieja);
                funcionalidades.eliminarImagen(imagenVieja.getUrl());
                imagenRepository.delete(imagenVieja);
            }
        });

        if (articuloInsumo.getImagenes() != null) {
            for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                // Utilizar la función guardarImagen de Funcionalidades para guardar la imagen
                String filename = UUID.randomUUID().toString() + ".jpg";
                try {
                    String rutaImagen = funcionalidades.guardarImagen(imagen.getUrl(), filename);
                    imagen.setUrl(rutaImagen); // Actualizar el campo url en ImagenArticulo
                    imagen.setArticulo(articuloInsumo); // Asignar el artículo a la imagen
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Verificar si la imagen ya existe en el conjunto de imágenes viejas
                boolean exists = imagenesViejas.stream().anyMatch(oldImage -> oldImage.getUrl().equals(imagen.getUrl()));

                // Si la imagen no existe en las imágenes viejas y existe en las nuevas, guardarla
                if (!exists && imagenesNuevas.contains(imagen)) {
                    imagenRepository.save(imagen);
                }
            }
        }
        articuloInsumo.setPrecioCompra(articuloInsumoViejo.getPrecioCompra());
        articuloInsumo.setPrecioVenta(articuloInsumoViejo.getPrecioVenta());
        articuloInsumo.setStockActual(articuloInsumoViejo.getStockActual());
        articuloInsumo.setSucursal(articuloInsumoViejo.getSucursal());

        return articuloInsumoRepository.save(articuloInsumo);

    } catch (Exception e) {
        throw new Exception(e);
    }
}
    //endregion



    @Override
    public boolean reactivate(Long id) throws Exception {
        try {
            if (articuloInsumoRepository.existsById(id)) {
                ArticuloInsumo articuloInsumo = articuloInsumoRepository.findById(id).get();
                if (articuloInsumo.isEliminado()) {
                    articuloInsumo.setEliminado(false);

                    // Reactivar las ImagenArticulo asociadas
                    for (ImagenArticulo imagen : articuloInsumo.getImagenes()) {
                        if (imagen.isEliminado()) {
                            imagen.setEliminado(false);
                        }
                    }

                    articuloInsumoRepository.save(articuloInsumo);
                    return true;
                } else {
                    throw new Exception("El ArticuloInsumo con el id proporcionado no está eliminado");
                }
            } else {
                throw new Exception("No existe el ArticuloInsumo con el id proporcionado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ArticuloInsumo> traerTodo() throws Exception {
        try {
            return articuloInsumoRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


}
