package vacini.com.vacini.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Entity
public class VacinaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Integer idadeMinima;
    private Integer quantidadeDoses;
    
    private Integer intervaloEntreDoses;

    @ManyToMany
    @JoinTable(name = "crianca_vacina",
            joinColumns = @JoinColumn(name = "vacina_model_id"),
            inverseJoinColumns = @JoinColumn(name ="crianca_model_id"))
    @JsonBackReference
    public List<CriancaModel> criancasVacinadas = new ArrayList<>();


    public List<CriancaModel> getCriancasVacinadas() {
        return criancasVacinadas;
    }
    public void setCriancasVacinadas(CriancaModel criancasVacinadas) {
        this.criancasVacinadas.add(criancasVacinadas);
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
    
    public Integer getQuantidadeDoses() {
        return quantidadeDoses;
    }
    public void setQuantidadeDoses(Integer quantidadeDoses) {
        this.quantidadeDoses = quantidadeDoses;
    }
    public int getIdadeMinima() {
        return idadeMinima;
    }
    public void setIdadeMinima(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }
    public int getIntervaloEntreDoses() {
        return intervaloEntreDoses;
    }
    public void setIntervaloEntreDoses(int intervaloEntreDoses) {
        this.intervaloEntreDoses = intervaloEntreDoses;
    }
    
    
}
