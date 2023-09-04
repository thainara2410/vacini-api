package vacini.com.vacini.controller;

import java.util.Optional;
import java.util.Set;

import javax.management.relation.RelationNotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vacini.com.vacini.model.CriancaModel;
import vacini.com.vacini.model.VacinaModel;
import vacini.com.vacini.repository.CriancaRepository;
import vacini.com.vacini.repository.VacinaRepository;
import vacini.com.vacini.service.CriancaVacinaService;

@RestController
@RequestMapping("/api/vacinicriancas")
public class CriancaController {

    @Autowired
    private CriancaRepository action;
    private VacinaRepository actionVacina;
    private CriancaVacinaService criancaVacinaService;

    public CriancaController(VacinaRepository actionVacina) {
        this.actionVacina = actionVacina;
        this.criancaVacinaService = new CriancaVacinaService();
    }
    @CrossOrigin(origins = "*")
    @PostMapping("/criancas")
    public CriancaModel cadastrar(@RequestBody CriancaModel crianca){
        return action.save(crianca);
    }
    
    //testado
    @CrossOrigin(origins = "*")
    @PostMapping("/criancas/vacina/{id}")
    public CriancaModel cadastrarCriancaComVacina(@RequestBody CriancaModel crianca, @PathVariable Integer id) throws RelationNotFoundException{
        Optional<VacinaModel> optionalVacina = actionVacina.findById(id);

            if (optionalVacina.isPresent()) {
                VacinaModel vacina = optionalVacina.get();

                // Adiciona a vacina à lista de vacinas tomadas pela criança
                crianca.setVacinasTomadas(vacina);

                // Adiciona a criança à lista de crianças vacinadas pela vacina
                vacina.setCriancasVacinadas(crianca);

                // Salva a criança no banco de dados
                return action.save(crianca);
            } else {
                // Caso a vacina com o ID fornecido não seja encontrada, você pode lidar com isso como preferir.
                throw new RelationNotFoundException("Vacina não encontrada com o ID fornecido: " + id);
            }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/criancas")
    public Iterable<CriancaModel> selecionar(){
        return action.findAll();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/crianca/{id}")
    public CriancaModel editar(@RequestBody CriancaModel crianca){
        return action.save(crianca);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/criancas/vacina/{id}")
    public CriancaModel atualizarCriancaComVacina(@RequestBody Set<Integer> idsVacinas, @PathVariable Integer id) throws RelationNotFoundException {
        Optional<CriancaModel> optionalCrianca = action.findById(id);

        if (optionalCrianca.isPresent()) {
            CriancaModel crianca = optionalCrianca.get();

            // Limpa a lista de vacinas tomadas antes de adicionar as novas vacinas
            crianca.getVacinasTomadas().clear();

            for (Integer idVacina : idsVacinas) {
                Optional<VacinaModel> optionalVacina = actionVacina.findById(idVacina);
                if (optionalVacina.isPresent()) {
                    VacinaModel vacina = optionalVacina.get();
                    crianca.setVacinasTomadas(vacina); // Método que adiciona a vacina à lista de vacinas tomadas
                    vacina.setCriancasVacinadas(crianca); // Método que adiciona a criança à lista de crianças vacinadas
                } else {
                    // Caso a vacina com o ID fornecido não seja encontrada, você pode lidar com isso como preferir.
                    throw new RelationNotFoundException("Vacina não encontrada com o ID fornecido: " + idVacina);
                }
            }

            // Salva a criança no banco de dados após adicionar todas as vacinas
            return action.save(crianca);
        } else {
            throw new RelationNotFoundException("Criança não encontrada com o ID fornecido: " + id);
        }
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/crianca/{id}")
    public ResponseEntity<CriancaModel> getContatoPorId(@PathVariable Integer id) {
        Optional<CriancaModel> criancaOptional = action.findById(id);
        if (criancaOptional.isPresent()) {
            CriancaModel crianca = criancaOptional.get();
            return ResponseEntity.ok(crianca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/crianca/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        Optional<CriancaModel> criancaOptional = action.findById(id);
        if (criancaOptional.isPresent()) {
            CriancaModel crianca = criancaOptional.get();
            action.delete(crianca);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/criancas/vacinasAptas")
    public ResponseEntity<String> vacinasTomar(){
        List<CriancaModel> criancas = action.findAll();
        List<VacinaModel> vacinasAptas = new ArrayList<>();
        StringBuilder mensagem = new StringBuilder();

        for (CriancaModel crianca : criancas) {
            vacinasAptas = criancaVacinaService.alertar(crianca);
            for(VacinaModel vacina : vacinasAptas){
                mensagem.append(crianca.getNome()).append(" deve tomar a vacina: ").append(vacina.getNome()).append("\n");
            }
        }
        if (mensagem.length() > 0) {
            return ResponseEntity.ok(mensagem.toString());
        }
        return null;
 
    }


    
}
