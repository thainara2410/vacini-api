package vacini.com.vacini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vacini.com.vacini.model.CriancaModel;

public interface CriancaRepository extends JpaRepository<CriancaModel, Integer> {
    
}
