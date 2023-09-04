package vacini.com.vacini.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import vacini.com.vacini.model.VacinaModel;
import vacini.com.vacini.repository.VacinaRepository;

@RestController
@RequestMapping("/api/vacinicriancas")
public class VacinaController {

    @Autowired
    private VacinaRepository action;

    @CrossOrigin(origins = "*")
    @PostMapping("/vacinas")
    public VacinaModel cadastrar(@RequestBody VacinaModel vacina){
        return action.save(vacina);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/vacinas")
    public Iterable<VacinaModel> selecionar(){
        return action.findAll();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/vacina/{id}")
    public VacinaModel editar(@RequestBody VacinaModel vacina){
        return action.save(vacina);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/vacina/{id}")
    public ResponseEntity<VacinaModel> getVacinaPorId(@PathVariable Integer id) {
        Optional<VacinaModel> vacinaOptional = action.findById(id);
        if (vacinaOptional.isPresent()) {
            VacinaModel vacina = vacinaOptional.get();
            return ResponseEntity.ok(vacina);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/vacina/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        Optional<VacinaModel> vacinaOptional = action.findById(id);
        if (vacinaOptional.isPresent()) {
            VacinaModel vacina = vacinaOptional.get();
            action.delete(vacina);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}