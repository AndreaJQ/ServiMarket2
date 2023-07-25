package com.grupob.ServiMarket.service;

import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.Score;
import com.grupob.ServiMarket.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    //------------------------CREATE--------------------------
    @Transactional
    public void create (Score score){

        scoreRepository.save(score);
    }

    //------------------------READ--------------------------

    public List<Score> list(){
        List<Score> califications = new ArrayList<>();
        califications=scoreRepository.findAll();
        return califications;
    }


    //---------------------GET PUBLICATION BY ID-----------------
    public Score getScoreById(Long id){
        return scoreRepository.findById(id).orElse(null);
    }


    //------------------------UPDATE--------------------------

    public Score editCalification (Score calif){
        Score editCalif = scoreRepository.findById(calif.getId()).orElse(null);
        if(editCalif!=null){
            editCalif.setComentario(calif.getComentario());
            scoreRepository.save(editCalif);
            return editCalif;
        }
        return null;
    }



    //------------------------DELETE--------------------------

    @Transactional
    public void delete (Long id){
        Optional<Score> response = scoreRepository.findById(id);
        if(response!= null){
            Score califToDelete = response.get();
            scoreRepository.delete(califToDelete);
        }
    }


}
