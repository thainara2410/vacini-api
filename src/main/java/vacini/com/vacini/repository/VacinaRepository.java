package vacini.com.vacini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vacini.com.vacini.model.VacinaModel;

@Repository
public  interface VacinaRepository extends JpaRepository<VacinaModel, Integer> {
    
}
