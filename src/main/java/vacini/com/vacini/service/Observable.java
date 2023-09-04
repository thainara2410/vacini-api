package vacini.com.vacini.service;

import java.util.ArrayList;
import java.util.List;

import vacini.com.vacini.designerpatternsinterface.Observer;
import vacini.com.vacini.model.CriancaModel;
import vacini.com.vacini.model.ResponsavelModel;
import vacini.com.vacini.model.VacinaModel;

public class Observable {
    private List<Observer> observadores = new ArrayList<>();

    public void adicionarObservador(Observer observador) {
        observadores.add(observador);
    }

    public void removerObservador(Observer observador) {
        observadores.remove(observador);
    }

    public void notificarCriancaAptaVacina(CriancaModel crianca, List<VacinaModel> vacinasAptas) {
        for (Observer observador : observadores) {
            observador.notificarCriancaAptaVacina(crianca, vacinasAptas);
        }
        observadores.clear();
    }
}
