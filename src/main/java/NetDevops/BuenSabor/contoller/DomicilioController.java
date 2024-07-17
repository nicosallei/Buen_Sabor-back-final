package NetDevops.BuenSabor.contoller;

import NetDevops.BuenSabor.service.IDomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/domicilio")
public class DomicilioController {
    @Autowired
    private IDomicilioService domicilioService;


    //region CRUD Basico



    //endregion


}
