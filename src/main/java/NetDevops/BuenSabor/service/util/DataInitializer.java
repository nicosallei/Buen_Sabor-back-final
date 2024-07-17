package NetDevops.BuenSabor.service.util;

import NetDevops.BuenSabor.entities.Pais;
import NetDevops.BuenSabor.entities.Provincia;
import NetDevops.BuenSabor.repository.ILocalidadRepository;
import NetDevops.BuenSabor.repository.IPaisRepository;
import NetDevops.BuenSabor.repository.IProvinciaRepository;
import NetDevops.BuenSabor.service.impl.LocalidadService;
import NetDevops.BuenSabor.service.impl.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer {

    @Autowired
    private IProvinciaRepository provinciaRepository;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private ProvinciaService serviceProvincia;
    @Autowired
    private ILocalidadRepository localidadRepository;
    @Autowired
    private IPaisRepository paisRepository;

    //@PostConstruct
    public void initData() {
        Optional<Pais> paisOptional = paisRepository.findByNombre("Argentina");
        Pais argentina = paisOptional.orElseGet(() -> {
            Pais nuevoPais = new Pais();
            nuevoPais.setId(0L); // Establece el ID a 0
            nuevoPais.setNombre("Argentina");
            return paisRepository.save(nuevoPais);
        });


        if (provinciaRepository.count() == 0) {
            try {
                serviceProvincia.saveProvinciasFromApi();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Suponiendo que tienes un método similar para verificar si ya existen localidades
        if (localidadRepository.findAll().isEmpty()) {
            List<Provincia> provincias = provinciaRepository.findAll();
            provincias.forEach(provincia -> localidadService.guardarLocalidadesDeProvincia(String.valueOf(provincia.getId())));
        }
    }
}