package com.grupob.ServiMarket.controller;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    //---------------------------READ-----------------------LIST
    @GetMapping("/listaUsuarios")
    public String listar(ModelMap modelo){
        List<UserEntity> user = userService.list();
        modelo.addAttribute("user", user);
return "user_list.html";
    }
    //GET MAPPING
    @GetMapping("/userslist")
    public List<UserEntity> listuser(){
        return userService.list();
    }


    //--------------------------UPDATE-----------------
    @GetMapping("/edituser/{userId}")
    public String editUser(@PathVariable("userId") Long id, ModelMap model){
        model.put("user", userService.getUserById(id));
        return"edituser.html";
    }

    @PutMapping("/edituser/{userId}")
    public String editUser(@RequestBody UserEntity userE,
                           @PathVariable("userId") Long userId){

        userE.setId(userId);
        userService.updateUser(userE);
        return "Updated"; //string para ser visualizada en postman
    }

    //-------------------DELETE------------------------
    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/user/listaUsuarios";
    }


    //-------------------SEARCH QUERY------------------------
    @GetMapping("/userslist/search")
    public String searchUsername(@RequestParam(value="query") String query, Model model){
        List<UserEntity> users = userService.searchUsers(query);
        model.addAttribute("users", users);
        return "userslist" ;
    }
}