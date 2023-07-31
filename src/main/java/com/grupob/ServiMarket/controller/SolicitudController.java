package com.grupob.ServiMarket.controller;

import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.Solicitud;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.exceptions.MyException;
import com.grupob.ServiMarket.service.PublicationService;
import com.grupob.ServiMarket.service.SolicitudService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SolicitudController {
    @Autowired
    private PublicationService pService;
    @Autowired
    private UserService userService;
    @Autowired
    private SolicitudService solService;


    //----------------------LIST-----------------------
   //en admin controller
    @GetMapping("/solicitudbyUser/{userId}")
    public String listSolic (ModelMap model, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        model.put("user", user);
        Long userId = user.getId();
        List<Solicitud> solicitud = solService.list();
        model.addAttribute("solicitud", solicitud);
        return "solicitudes-list-client";
    }
    @GetMapping("/solicitudbyProvider/{userId}")
    public String listSolicProv (ModelMap model, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        model.put("user", user);
        Long userId = user.getId();
        List<Solicitud> solicitud = solService.list();
        model.addAttribute("solicitud", solicitud);
        List<Publication> publication = pService.list();
        model.addAttribute("publication", publication);
        return "solicitudes-list-provider";
    }


    //-------------------CREATE SOLICITUD--------------------
    @GetMapping("/solicitud/{pId}/nueva")
    public String newSolicitudForm(@PathVariable("pId") Long pId,HttpSession session, ModelMap model){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        Solicitud solicitud = new Solicitud();
        model.put("pId", pId);
        model.put("solicitud", solicitud);
        model.put("user", user);
        return "solicitud-create";
    }
    @PostMapping("/solicitud/{pId}")
    public String newSolicitud (@PathVariable("pId") Long pId, @ModelAttribute("solicitud") Solicitud solicitud,
                                HttpSession session, ModelMap model){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        model.put("user",user);
        Long userId = user.getId();
        solService.createSol(solicitud,userId,pId);
        return "redirect:/publist";
    }


}
