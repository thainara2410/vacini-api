package vacini.com.vacini.designerpatternsinterface;

import java.util.List;

import vacini.com.vacini.model.CriancaModel;
import vacini.com.vacini.model.VacinaModel;

public interface Observer {
    void notificarCriancaAptaVacina(CriancaModel crianca, List<VacinaModel> vacinasAptas );
}
