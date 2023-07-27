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

import java.util.List;

@Controller
public class AdminController {

private UserService userService;
private PublicationService pService;

    @Autowired
    public AdminController(UserService userService, PublicationService pService) {
        this.userService = userService;
        this.pService=pService;
    }

    @GetMapping("/admin/dashboard")
    public String panelAdmin(){

        return "dashboard";
    }

    @GetMapping("/admin/users")
    public String listUser (ModelMap model){
        List<UserEntity> user = userService.list();
        model.addAttribute("user", user);
        return "users-list";
    }
    @GetMapping("/admin/publications")
    public String listPublic (ModelMap model){
        List<Publication> publication = pService.list();
        model.addAttribute("publication", publication);
        return "public-list";
    }

    @GetMapping("/admin/editRole/{id}")
    public String changeRol(@PathVariable Long id){
        userService.changeRole(id);
        return "redirect:/admin/users";
    }
    @PostMapping("/admin/editRole/{id}")
    public String changeRole(@PathVariable Long id){
        userService.changeRole(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/deletePub/{id}")
    public String deletePublication(@PathVariable("id") Long id){
        pService.delete(id);
        return "redirect:/admin/publications";
    }




}
