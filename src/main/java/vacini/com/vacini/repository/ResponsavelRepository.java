package vacini.com.vacini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vacini.com.vacini.model.ResponsavelModel;

public  interface ResponsavelRepository extends JpaRepository<ResponsavelModel, Integer> {
    
}
