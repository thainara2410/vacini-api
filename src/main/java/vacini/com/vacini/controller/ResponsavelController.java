package vacini.com.vacini.controller;

import java.util.Optional;

import javax.management.relation.RelationNotFoundException;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vacini.com.vacini.model.CriancaModel;
import vacini.com.vacini.model.ResponsavelModel;
import vacini.com.vacini.repository.CriancaRepository;
import vacini.com.vacini.repository.ResponsavelRepository;

@RestController
@RequestMapping("/api/vacinicriancas")
public class ResponsavelController {

    @Autowired
    private ResponsavelRepository action;
    @Autowired
    private CriancaRepository actionCrianca;

    @CrossOrigin(origins = "*")
    @PostMapping("/responsavel/crianca/{id}")
    public ResponsavelModel cadastrar(@RequestBody ResponsavelModel responsavel, @PathVariable Integer id) throws RelationNotFoundException{
        Optional<CriancaModel> crianca = actionCrianca.findById(id);
        System.out.println("/n entrei no endpoint e busquei a crianca /n");
        
        if(crianca.isPresent())
        {
            crianca.get().setResponsavelDaCrianca(responsavel);
            //actionCrianca.save(crianca.get());
        
            responsavel.setCriancasComResponsavel(crianca.get());
            System.out.println("/n Eu passei aquiu\n");
            return action.save(responsavel);
        }
        else
        {
            // Caso a vacina com o ID fornecido não seja encontrada, você pode lidar com isso como preferir.
            throw new RelationNotFoundException("Crianca não encontrada com o ID fornecido: " + id);
        }
        
        
    }

    @CrossOrigin(origins= "*")
    @GetMapping("/responsaveis")
    public Iterable<ResponsavelModel> selecionar(){
        return action.findAll();
    }

    @CrossOrigin(origins="*")
    @PutMapping("/responsavel/{id}")
    public ResponsavelModel editar(@RequestBody ResponsavelModel responsavel){
        return action.save(responsavel);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/responsavel/{id}")
    public ResponseEntity<ResponsavelModel> getVacinaPorId(@PathVariable Integer id) {
        Optional<ResponsavelModel> responsavelOptional = action.findById(id);
        if (responsavelOptional.isPresent()) {
            ResponsavelModel responsavel = responsavelOptional.get();
            return ResponseEntity.ok(responsavel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/responsavel/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        Optional<ResponsavelModel> responsavelOptional = action.findById(id);
        if (responsavelOptional.isPresent()) {
            ResponsavelModel responsavel = responsavelOptional.get();
            action.delete(responsavel);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
