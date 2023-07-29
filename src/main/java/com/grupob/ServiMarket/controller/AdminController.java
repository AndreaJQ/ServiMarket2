package com.grupob.ServiMarket.controller;

import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.service.PublicationService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

private UserService userService;
private PublicationService pService;

    @Autowired
    public AdminController(UserService userService, PublicationService pService) {
        this.userService = userService;
        this.pService=pService;
    }

    @GetMapping("/dashboard")
    public String panelAdmin(){

        return "dashboard";
    }

    @GetMapping("/users")
    public String listUser (ModelMap model){
        List<UserEntity> user = userService.list();
        model.addAttribute("user", user);
        return "listUsers";
    }
    @GetMapping("/publications")
    public String listPublic (ModelMap model){
        List<Publication> publication = pService.list();
        model.addAttribute("publication", publication);
        return "public-list";
    }

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

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/deletePub/{id}")
    public String deletePublication(@PathVariable("id") Long id){
        pService.delete(id);
        return "redirect:/admin/publications";
    }




}
