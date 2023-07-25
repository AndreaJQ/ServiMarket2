package com.grupob.ServiMarket.controller;
import com.grupob.ServiMarket.entity.Score;
import com.grupob.ServiMarket.service.ScoreService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ScoreController {


    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;//por si lo necesitamos después

    //-------------------CREATE score---------------------------
    @PostMapping("/newScore")
    public String newScore(@RequestBody Score calification){

        scoreService.create(calification);

        return "POSTED"; //string para ser visualizada en postman
    }

    //---------------------------READ-----------------------LIST
    //GET MAPPING
    @GetMapping("/scorelist")  //vista para casa proveedor en su publicación
    public List<Score> calificationList(){


        return scoreService.list();
    }


    //--------------------------UPDATE-----------------
    @GetMapping("/editScore/{sId}")
    public String editScore(@PathVariable("sId") Long sId, ModelMap model){

        model.put("score", scoreService.getScoreById(sId));

        return"editScore.html";
    }

    @PutMapping("/editScore/{sId}")
    public String editScore(@RequestBody Score calif,
                          @PathVariable("sId") Long sId){

        calif.setId(sId);
        scoreService.editCalification(calif);
        return "Updated"; //string para ser visualizada en postman

    }

    //-------------------DELETE------------------------
    @GetMapping("/score/{sId}/delete")
    public String deleteScore(@PathVariable("sId") Long sId){
        scoreService.delete(sId);
        return "Deleted"; //string para ser visualizada en postman
    }










}
