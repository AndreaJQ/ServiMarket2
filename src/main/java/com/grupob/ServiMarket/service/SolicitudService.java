package com.grupob.ServiMarket.service;

import com.grupob.ServiMarket.entity.Solicitud;
import com.grupob.ServiMarket.repository.PublicationRepository;
import com.grupob.ServiMarket.repository.SolicitudRepository;
import com.grupob.ServiMarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository repository;

    //------------------------CREATE--------------------------
    @Transactional
    public void create (Solicitud solicitud){
        solicitudRepository.save(solicitud);
    }

    //------------------------READ--------------------------

    public List<Solicitud> list(){
        List<Solicitud> solicitud = new ArrayList<>();
        solicitud = solicitudRepository.findAll();
        return solicitud;
    }


    //---------------------GET PUBLICATION BY ID-----------------
    public Solicitud getSolicitudById(Long id){
        return solicitudRepository.findById(id).orElse(null);
    }
    //------------------------UPDATE--------------------------
    public Solicitud updateSolicitud (Solicitud solicitud){
        Solicitud updateSolicitud = solicitudRepository.findById(solicitud.getId()).orElse(null);
        if(updateSolicitud !=null){
            updateSolicitud.setComentario(solicitud.getComentario());
            updateSolicitud.setProvider(solicitud.getProvider());
            updateSolicitud.setUserClient(solicitud.getUserClient());

            solicitudRepository.save(updateSolicitud);
            return updateSolicitud;
        }
        return null;
    }

    //------------------------DELETE--------------------------

    @Transactional
    public void delete (Long id){
        Optional<Solicitud> response = solicitudRepository.findById(id);
        if(response!= null){
            Solicitud solicitudToDelete = response.get();
            solicitudRepository.delete(solicitudToDelete);
        }
    }
}
