package vacini.com.vacini.model;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import vacini.com.vacini.designerpatternsinterface.Observer;
import vacini.com.vacini.service.EmailSenderService;



@Entity

public class ResponsavelModel implements Observer{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;

    @ManyToMany(mappedBy = "responsavelDaCrianca")
    @JsonManagedReference
    public List<CriancaModel> criancasDaResponsavel = new ArrayList<>();

    @Transient
    final EmailSenderService emailSenderService  = new EmailSenderService();

    
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<CriancaModel> getCriancasDaResponsavel() {
        return criancasDaResponsavel;
    }
    public void setCriancasComResponsavel(CriancaModel criancaDaResponsavel) {
        this.criancasDaResponsavel.add(criancaDaResponsavel);
    }
    
    @Override
    public void notificarCriancaAptaVacina(CriancaModel crianca, List<VacinaModel> vacinasAptas) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Esse é o corpo do email: \n A criança").append(crianca.getNome()).append("está apta para tomar a(s) seguinte(s) vacina(s): \n");
        
        for(VacinaModel vacina : vacinasAptas){
            mensagem.append(vacina.getNome()).append("\n");
        }

        System.out.println(mensagem);
        try {
            emailSenderService.notificarResponsavelEmail(email, mensagem);
        } catch (MessagingException e) {
            System.out.println("Deu errado");
            e.printStackTrace();
        }

        throw new UnsupportedOperationException("Unimplemented method 'notificarCriancaAptaVacina'");
    }
    

}
