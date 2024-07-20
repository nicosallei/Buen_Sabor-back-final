package NetDevops.BuenSabor.contoller;


import NetDevops.BuenSabor.dto.categoria.CategoriaDto;
import NetDevops.BuenSabor.dto.categoria.CategoriaEmpresaDTO;

import NetDevops.BuenSabor.dto.categoria.SubCategoriaConEmpresaDTO;
import NetDevops.BuenSabor.entities.Categoria;
import NetDevops.BuenSabor.errores.ApiError;
import NetDevops.BuenSabor.service.ICategoriaService;
import NetDevops.BuenSabor.service.impl.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

// GET http://localhost:8080/api/categorias/

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControllador {
    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping("/")
    public ResponseEntity<?> lista() {
        try {
            return ResponseEntity.ok().body(categoriaService.lista());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(categoriaService.buscar(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> cargar(@RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.cargar(categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")

    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.Actualizar(id, categoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")

    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(categoriaService.eliminar(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //agrega una subcategoria a una categoria
    @PostMapping("/agregar/subcategoria/{id}")

    public ResponseEntity<?> crearSubCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.agregarSubCategoria(id, categoria));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    //agrega un articulo a una categoria
//    @PutMapping("/agregar/articulo/")
//    public ResponseEntity<?> agregarArticulo(@PathVariable Long id, @RequestBody Articulo articulo) {
//        try {
//            return ResponseEntity.ok().body(categoriaService.agregarArticulo(id, articulo));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

   @PostMapping("/agregar/articulo")

public ResponseEntity<?> agregarArticulo(@RequestParam("idCategoria") Long idCategoria, @RequestParam("idArticulo") Long idArticulo) {
    try {
        return ResponseEntity.ok().body(categoriaService.agregarArticulo(idCategoria, idArticulo));
    } catch (Exception e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

    //obtiene todas las categorias con subcategorias
    @GetMapping("/categoriasConSubcategorias/")
    public ResponseEntity<?> obtenerCategoriasConSubCategorias() {
        try {
            return ResponseEntity.ok().body(categoriaService.obtenerCategoriasConSubCategorias());
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @DeleteMapping("/eliminar/articulo/{idSubCategoria}/{idArticulo}")

    public ResponseEntity<?> eliminarArticuloDeSubCategoria(@PathVariable Long idSubCategoria, @PathVariable Long idArticulo) {
        try {
            return ResponseEntity.ok().body(categoriaService.eliminarArticuloDeSubCategoria(idSubCategoria, idArticulo));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @PutMapping("/actualizar/subcategoria/{idSubCategoria}")

    public ResponseEntity<?> actualizarSubCategoria(@PathVariable Long idSubCategoria, @RequestBody Categoria nuevaSubCategoria) {
        try {
            return ResponseEntity.ok().body(categoriaService.actualizarSubCategoria(idSubCategoria, nuevaSubCategoria));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @DeleteMapping("/eliminar/subcategoria")

public ResponseEntity<?> eliminarSubCategoria(@RequestParam("idCategoria") Long idCategoria, @RequestParam("idSubCategoria") Long idSubCategoria) {
    try {
        return ResponseEntity.ok().body(categoriaService.eliminarSubCategoria(idCategoria, idSubCategoria));
    } catch (Exception e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

    @PostMapping("/reactivate/{id}")

    public ResponseEntity<?> reactivate(@PathVariable Long id) {
        try {
            categoriaService.reactivate(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @GetMapping("/traer-todo/")
    public ResponseEntity<?> traerTodo() {
        try {
            return ResponseEntity.ok(categoriaService.traerTodo());
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    //obtiene todas las subcategorias
    @GetMapping("/subcategorias/{id}")
    public ResponseEntity<?> obtenerSubCategorias(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(categoriaService.obtenerSubCategorias(id));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
   @GetMapping("/categoriasPadre/{sucursalId}")
public ResponseEntity<?> obtenerCategoriasPadre(@PathVariable Long sucursalId) {
    try {
        return ResponseEntity.ok().body(categoriaService.traerCategoriaPadre(sucursalId));
    } catch (Exception e) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

    @GetMapping("/{id}/tieneSubCategorias")
    public ResponseEntity<?> tieneSubCategorias(@PathVariable Long id) {
        try {
            boolean tieneSubCategorias = categoriaService.tieneSubCategorias(id);
            return ResponseEntity.ok().body(tieneSubCategorias);
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }



    //---------------------Categoria por Empresa------------------------------------------------------------
    @Autowired
    private CategoriaService catService;

    @PostMapping("/porEmpresa")

    public ResponseEntity<?> crearCategoriaporEmpresa(@RequestBody CategoriaEmpresaDTO categoriaDTO) throws Exception {
        try {
            Categoria nuevaCategoria = catService.crearCategoriaporEmpresa(categoriaDTO);
            return ResponseEntity.ok(nuevaCategoria);

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());

        }
    }

    @PutMapping("/{id}/denominacion")
    public ResponseEntity<?> actualizarDenominacion(@PathVariable Long id, @RequestBody CategoriaDto dto) throws Exception {
        try {
            CategoriaDto categoriaActualizada = catService.actualizarDenominacion(id, dto.getDenominacion(), dto.getUrlIcono());
            return ResponseEntity.ok(categoriaActualizada);

        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    @PutMapping("/{id}/eliminado")

    public ResponseEntity<Categoria> cambiarEstadoEliminado(@PathVariable Long id) {
        Categoria categoriaActualizada = catService.cambiarEstadoEliminado(id);
        return ResponseEntity.ok(categoriaActualizada);
    }


    @PostMapping("/subcategoriaConEmpresa")

    public ResponseEntity<?> crearSubCategoriaConEmpresa(@RequestBody SubCategoriaConEmpresaDTO subCategoriaDTO) throws IOException {

        return ResponseEntity.ok(catService.crearSubCategoriaConEmpresa(subCategoriaDTO));
    }

    //------------------
    @GetMapping("/porEmpresa/{idEmpresa}")
    public ResponseEntity<?> obtenerCategoriasPorIdEmpresa(@PathVariable Long idEmpresa) {
        try {
            return ResponseEntity.ok(catService.traerTodo2(idEmpresa));
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }
    @CrossOrigin(origins = "https://ecommerce-buen-sabor.vercel.app")
    @GetMapping("/traer-categoria-padre")
    public ResponseEntity<?> traerCategoriaPadre() {
        try {
            return ResponseEntity.ok(catService.traerCategoriasPadres());
        } catch (Exception e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

//-----------------------
}
