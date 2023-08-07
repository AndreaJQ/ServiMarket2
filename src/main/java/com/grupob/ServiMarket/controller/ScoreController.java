package com.grupob.ServiMarket.controller;
import com.grupob.ServiMarket.entity.Score;
import com.grupob.ServiMarket.entity.Solicitud;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.service.ScoreService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ScoreController {


    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;//por si lo necesitamos después

    //-------------------CREATE score---------------------------
    @GetMapping("/calification/{solid}/nueva")
       public String newCalifForm(@PathVariable("solid") Long solid, HttpSession session, ModelMap model){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        Score score = new Score();
        model.put("solid", solid);
        model.put("score", score);
        model.put("user", user);
        return "calification-form";
    }




    @PostMapping("/calification/{solid}")
    public String newScore(@PathVariable("solid") Long solid, @ModelAttribute("score") Score score,
                           HttpSession session, ModelMap model){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        model.put("user",user);
        Long userId = user.getId();
        scoreService.create(score,solid,userId);

        return "redirect:/solicitudbyUser";
    }

    //---------------------------READ-----------------------LIST
    //GET MAPPING
    @GetMapping("/scorelist")  //vista para casa proveedor en su publicación
    public List<Score> calificationList(){


        return scoreService.list();
    }
    @GetMapping("/califications")
    public String listCalifications(ModelMap model){
        List<Score> score = scoreService.list();
        model.addAttribute("score", score);
        return "califications-list-provider.html";
    }


    //--------------------------UPDATE-----------------
    @GetMapping("/editScore/{sId}")
    public String editScore(@PathVariable("sId") Long sId, ModelMap model){

        model.put("score", scoreService.getScoreById(sId));

        return"calification-form-edit.html";
    }

    @PostMapping("/editScore/{sId}")
    public String editScore(@PathVariable("sId") Long sId,
                            @RequestParam("comentario") String comentario, @RequestParam("puntaje") int puntaje){


        scoreService.editCalification(comentario, puntaje,sId);
        return "redirect:/solicitudbyUser"; //string para ser visualizada en postman

    }

    //-------------------DELETE------------------------
    @GetMapping("/score/{sId}/delete")
    public String deleteScore(@PathVariable("sId") Long sId){
        scoreService.delete(sId);
        return "Deleted"; //string para ser visualizada en postman
    }










}
