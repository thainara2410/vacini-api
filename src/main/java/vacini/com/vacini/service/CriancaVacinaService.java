package vacini.com.vacini.service;

import vacini.com.vacini.model.CriancaModel;
import vacini.com.vacini.model.VacinaModel;
import vacini.com.vacini.repository.CriancaRepository;
import vacini.com.vacini.repository.VacinaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CriancaVacinaService {
    @Autowired
    private VacinaRepository actionVacina;
    private CriancaRepository actionCrianca;
    

    public CriancaVacinaService() {
        
        //this.actionCrianca = actionCrianca;
    }

    public List<VacinaModel> alertar(CriancaModel crianca){

        List<VacinaModel> vacinasAptas = new ArrayList<>();

        // Obter todas as vacinas do banco de dados
        List<VacinaModel> todasAsVacinas = actionVacina.findAll();

        int idade = crianca.getIdade();
        for (VacinaModel vacina : todasAsVacinas) {
            if(idade>= vacina.getIdadeMinima()){
                List<VacinaModel>  vacinasTomadasPelaCrianca= crianca.getVacinasTomadas();
                Integer dosesTomadas = 0;
                for(VacinaModel vacinaTomada: vacinasTomadasPelaCrianca){
                    if(vacinaTomada == vacina){
                        dosesTomadas ++;
                    }
                }
                if(dosesTomadas<1){
                    System.out.println(crianca.getNome() +"deve tomar a primeira dose dessa vacina:"+ vacina.getNome());
                    vacinasAptas.add(vacina);
                    //return crianca.getNome() +"deve tomar a primeira dose dessa vacina:"+ vacina.getNome();
                }else if(dosesTomadas>=1){
                    if((vacina.getQuantidadeDoses() - dosesTomadas)>0){
                        int diasParaProxVacina = dosesTomadas*vacina.getIntervaloEntreDoses();
                        LocalDate diaAtual = LocalDate.now();
                        if(!crianca.getDataNasc().plusDays(diasParaProxVacina).isBefore(diaAtual)){
                            System.out.println(crianca.getNome()+"deve tomar mais uma dose da vacina:"+ vacina.getNome());
                            vacinasAptas.add(vacina);
                            //return crianca.getNome()+"deve tomar mais uma dose da vacina:"+ vacina.getNome();
                        }
                    }
                }

            }
        }
        return vacinasAptas;
    }

    @Scheduled(fixedRate = 6000)
    public void agendarAtualizacao(){
        List<CriancaModel> criancas = actionCrianca.findAll();

        if(criancas != null){
            for(CriancaModel crianca: criancas){
                crianca.setVacinasAptas();
            }
        }
        System.out.println("passei aqui no agendamento");
    }
}
