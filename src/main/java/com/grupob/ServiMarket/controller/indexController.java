package com.grupob.ServiMarket.controller;

import com.grupob.ServiMarket.entity.UserEntity;

import com.grupob.ServiMarket.enums.Role;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/")
public class indexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index (){
        return "index.html";
    }


    @GetMapping("/registrarse")
    public String registrarse(ModelMap model){
        UserEntity us = new UserEntity();
        model.put("userEntity",us);
        return "registro.html";
    }
    @PostMapping("/newuser")
    public String saveUser(@Valid UserEntity userEntity, String password, BindingResult result ){
        System.out.println(userEntity.toString());
        if(result.hasErrors()){
            return "registro.html";
        }
       //  userEntity.setRole(Role.USER);
        userService.create(userEntity, password);
        return "index.html";
    }

    @GetMapping("/ingresar")
    public String login(@RequestParam (required = false) String error, ModelMap modelo) {
        if (error != null){
            modelo.put("error","Usuario o contrasena invalidos ");
        }

        return "login.html";
    }

        @GetMapping("/inicio")
        public String inicio(){
        return "inicio.html";
        }

}
