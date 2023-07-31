package com.grupob.ServiMarket.repository;

import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.enums.Rubro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT u from Publication u WHERE u.description LIKE CONCAT('%',:query,'%')")
    List<Publication> searchPublication(String query);

    List<Publication> findByRubro(Rubro rubro);


}
