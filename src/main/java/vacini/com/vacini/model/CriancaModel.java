package vacini.com.vacini.model;

import java.util.List;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.springframework.scheduling.annotation.Scheduled;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import vacini.com.vacini.service.CriancaVacinaService;
import vacini.com.vacini.service.Observable;


@Entity
public class CriancaModel extends Observable{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private LocalDate dataNasc;
    @Transient
    private int idade;

    @ManyToMany
    @JoinTable(name = "crianca_vacinaApta",
               joinColumns = @JoinColumn(name = "crianca_id"),
               inverseJoinColumns = @JoinColumn(name = "vacina_id"))
    private List<VacinaModel> vacinasAptas = new ArrayList<>();
    
    @ManyToMany(mappedBy = "criancasVacinadas")
    @JsonManagedReference
    private List<VacinaModel> vacinasTomadas = new ArrayList<>();

    
    @ManyToMany
    @JoinTable(name = "crianca_responsavel",
            joinColumns = @JoinColumn(name = "crianca_model_id"),
            inverseJoinColumns = @JoinColumn(name ="responsavel_model_id"))
    @JsonBackReference
    private List<ResponsavelModel> responsavelDaCrianca = new ArrayList<>();

    //private Responsavel responsavel;

    @Transient
    CriancaVacinaService criancaVacinaService = new CriancaVacinaService();



    public List<ResponsavelModel> getResponsavelDaCrianca() {
        return responsavelDaCrianca;
    }
    public void setResponsavelDaCrianca(ResponsavelModel responsavel) {
        this.responsavelDaCrianca.add(responsavel);
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        this.setIdade();
        return idade;
    }

    public void setIdade(){
        LocalDate diaAtual= LocalDate.now();
        Period idadeDias = Period.between(dataNasc, diaAtual);
        this.idade = idadeDias.getDays();
    }

    public List<VacinaModel> getVacinasAptas() {
        return vacinasAptas;
    }


    //@Scheduled(cron = "0 17 21 * * *")
    public void setVacinasAptas() {
        this.vacinasAptas = criancaVacinaService.alertar(this);
        //vou chamar algo aqui mas não sei o que
        for(ResponsavelModel responsavel : this.getResponsavelDaCrianca()){
            adicionarObservador(responsavel);
        }
        
        this.notificarCriancaAptaVacina(this, vacinasAptas);
    }

    public List<VacinaModel> getVacinasTomadas() {
        return vacinasTomadas;
    }
    public void setVacinasTomadas(VacinaModel vacinaTomada) {
        this.vacinasTomadas.add(vacinaTomada);
    }
    
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(String dataNasc) {        
        this.dataNasc = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
}
