package com.grupob.ServiMarket.service;


import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository pRepository;

    //------------------------CREATE--------------------------
    @Transactional
    public void create (Publication publication){
        pRepository.save(publication);
    }

    //------------------------READ--------------------------

    public List<Publication> list(){
        List<Publication> publication = new ArrayList<>();
        publication=pRepository.findAll();
        return publication;
    }


    //---------------------GET PUBLICATION BY ID-----------------
    public Publication getPublicationById(Long id){
        return pRepository.findById(id).orElse(null);
    }


    //------------------------UPDATE--------------------------

    public Publication updatePublication (Publication publication){
        Publication uptadePu = pRepository.findById(publication.getId()).orElse(null);
        if(uptadePu!=null){
            uptadePu.setTitle(publication.getTitle());
            uptadePu.setRubro(publication.getRubro());
            uptadePu.setDescription(publication.getDescription());
            uptadePu.setUrlImagen(publication.getUrlImagen());
            //uptadePu.setPrecio(publication.getPrecio());
            pRepository.save(uptadePu);
            return uptadePu;
        }
        return null;
    }



    //------------------------DELETE--------------------------

    @Transactional
    public void delete (Long id){
        Optional<Publication> response = pRepository.findById(id);
        if(response!= null){
            Publication publicationToDelete = response.get();
            pRepository.delete(publicationToDelete);
        }
    }

    //---------------------SEARCH PUBLICATION-----------------

    public List<Publication> searchPublication(String query) {
        List<Publication> publications = pRepository.searchPublication(query);
        return publications;
    }

    public List<Publication> findByRubro(String rubro){
        return pRepository.findByRubro(rubro);
    }
}
