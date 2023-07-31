package com.grupob.ServiMarket.service;


import com.grupob.ServiMarket.entity.Image;
import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.enums.Rubro;
import com.grupob.ServiMarket.exceptions.MyException;
import com.grupob.ServiMarket.repository.PublicationRepository;
import com.grupob.ServiMarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository pRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    //------------------------CREATE--------------------------
    @Transactional
    public void create (Publication publication, Long userId, MultipartFile archivo) throws MyException {

        UserEntity user = new UserEntity();

        Optional <UserEntity> answer = userRepository.findById(userId);

        if (answer.isPresent()){
            user= answer.get();

            publication.setProvider(user);
            Image image = imageService.guardar(archivo);
            publication.setImage(image);
            pRepository.save(publication);
        }

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

    public Publication updatePublication (MultipartFile archivo,
                    String title, String description, String description2, Long id) throws Exception {
        Publication updatePu = new Publication();
        Optional<Publication> answer = pRepository.findById(id);
        if (answer.isPresent()){
            updatePu = answer.get();
            updatePu.setTitle(title);
            updatePu.setDescription(description);
            updatePu.setDescription2(description2);
            updatePu.setRubro(getPublicationById(id).getRubro());
            updatePu.setProvider(getPublicationById(id).getProvider());

            Image image = imageService.actualizar(archivo,updatePu.getImage().getId());
            updatePu.setImage(image);
            pRepository.save(updatePu);
        }
        /*Optional<Publication> answer = pRepository.findById(id);
        if (answer.isPresent()){
            updatePu.setTitle(title);
            updatePu.setDescription(description);
            updatePu.setDescription2(description2);

            Long idImage = null;
            if (updatePu.getImage()!=null){
                idImage = updatePu.getImage().getId();
            }
            Image image = imageService.actualizar(archivo,idImage);
            updatePu.setImage(image);
            pRepository.save(updatePu);

        }*/
        /*Publication updatePu = pRepository.findById(publication.getId()).orElse(null);
        if (updatePu!= null){
            updatePu.setTitle(publication.getTitle());
            updatePu.setRubro(publication.getRubro());
            updatePu.setDescription(publication.getDescription());
            updatePu.setDescription2(publication.getDescription2());
            Long idImage = null;
            if (updatePu.getImage()!=null){
                idImage = updatePu.getImage().getId();
            }
            Image image = imageService.actualizar(archivo,idImage);
            updatePu.setImage(image);
            pRepository.save(updatePu);
            return updatePu;

        }*/
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

    public List<Publication> findByRubro(Rubro rubro){
        return pRepository.findByRubro(rubro);
    }
}
