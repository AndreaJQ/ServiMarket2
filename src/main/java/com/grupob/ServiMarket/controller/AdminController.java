package com.grupob.ServiMarket.controller;

import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.Score;
import com.grupob.ServiMarket.entity.Solicitud;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.service.PublicationService;
import com.grupob.ServiMarket.service.ScoreService;
import com.grupob.ServiMarket.service.SolicitudService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdminController {

private UserService userService;
private PublicationService pService;
private SolicitudService solService;
private ScoreService scoreService;

    @Autowired
    public AdminController(UserService userService, PublicationService pService,SolicitudService solService, ScoreService scoreService) {
        this.userService = userService;
        this.pService=pService;
        this.solService=solService;
        this.scoreService=scoreService;
    }

    @GetMapping("/dashboard")
    public String panelAdmin(){

        return "dashboard";
    }
//----------LIST USERS----------------
    @GetMapping("/users")
    public String listUser (ModelMap model){
        List<UserEntity> user = userService.list();
        model.addAttribute("user", user);
        return "listUsers";
    }
    //----------LIST PUBLICATIONS----------------
    @GetMapping("/publications")
    public String listPublic (ModelMap model){
        List<Publication> publication = pService.list();
        model.addAttribute("publication", publication);
        return "public-list";
    }
    //----------CHANGE ROLE USER----------------
    @GetMapping("/editRole/{id}")
    public String changeRol(@PathVariable Long id){
        userService.changeRole(id);
        return "redirect:/admin/users";
    }
    @PostMapping("/editRole/{id}")
    public String changeRole(@PathVariable Long id){
        userService.changeRole(id);
        return "redirect:/admin/users";
    }
    //----------DELETE USERS----------------
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin/users";
    }
    //----------DELETE PUBLICATION----------------
    @GetMapping("/deletePub/{id}")
    public String deletePublication(@PathVariable("id") Long id){
        pService.delete(id);
        return "redirect:/admin/publications";
    }
    //----------LIST SOLICITUDES----------------
    @GetMapping("/solicitudes")
    public String listsolicitudes (ModelMap model){
        List<Solicitud> solicitud = solService.list();
        model.addAttribute("solicitud", solicitud);
        return "solicitudes-list";
    }
    @GetMapping("/editStatus/{id}")
    public String changeStat(@PathVariable Long id){
        solService.changeStatus(id);
        return "redirect:/admin/solicitudes";
    }
    //----------CHANGE STATUS SOLICITUD----------------
    @PostMapping("/editStatus/{id}")
    public String changeStatus(@PathVariable Long id){
        solService.changeStatus(id);
        return "redirect:/admin/solicitudes";
    }
    //----------DELETE SOLICITUD----------------
    @GetMapping("/deleteSol/{solid}")
    public String deleteSolicitud(@PathVariable("solid") Long id){
        solService.delete(id);
        return "redirect:/admin/solicitudes";
    }
    //----------CHANGE COMPLETO SOLICITUD----------------
    @GetMapping("/editCompleto/{id}")
    public String changeCompleto(@PathVariable Long id){
        solService.changeCompleto(id);
        return "redirect:/admin/solicitudes";
    }

    //----------LIST CALIFICATIONS----------------
    @GetMapping("/califications")
    public String listCalifications(ModelMap model){
        List<Score> score = scoreService.list();
        model.addAttribute("score", score);
        return "califications-list";
    }
    //----------DELETE SCORE----------------
    @GetMapping("/deleteScore/{scid}")
    public String deleteScore(@PathVariable("scid") Long id){
        scoreService.delete(id);
        return "redirect:/admin/califications";
    }
}
