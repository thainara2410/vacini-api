package vacini.com.vacini.service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender emailSender;
 
    public void notificarResponsavelEmail(String destinatario, StringBuilder mensagem) throws MessagingException {
        System.out.println("\n Eu tentei enviar o email!! \n");
        MimeMessage message = emailSender.createMimeMessage();
 
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(destinatario);
        helper.setSubject("Novas vacinas!");
        
        helper.setText(mensagem.toString());
 
        emailSender.send(message);
        System.out.println("foi o email!!!");
    }
}