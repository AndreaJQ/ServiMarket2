package com.grupob.ServiMarket.controller;
import com.grupob.ServiMarket.entity.Image;
import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.enums.Rubro;
import com.grupob.ServiMarket.exceptions.MyException;
import com.grupob.ServiMarket.service.PublicationService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class PublicationController  {

    @Autowired
    private PublicationService pService;
    @Autowired
    private UserService userService;


    //-------------------CREATE PUBLICATION---------------------------

    @PreAuthorize("hasAnyRole('ROLE_PROVIDER')")
    @GetMapping("/publicacion")
    public String newpublication(ModelMap model, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("usersesssion");
        Publication publication = new Publication();

        model.put("publicacion", publication);
        model.put("user", user);

        return "Formulario_Servicios.html";
    }
    @PostMapping("/newPublication")
    public String savePublication(@ModelAttribute("publicacion") Publication publication,
                                  HttpSession session, ModelMap modelMap,
                                  BindingResult result,
                                  MultipartFile archivo) throws MyException {

        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        modelMap.put("user", user);
        Long userId = user.getId();


        if (result.hasErrors()) {
            modelMap.put("publicacion", publication);
            modelMap.put("user", user);

            return "Formulario_Servicios.html";
        }
        pService.create(publication, userId, archivo);
        return "inicio.html";

    }

    //---------------------------READ-----------------------LIST
    //GET MAPPING
    @GetMapping("/publist")
    public String publicationsList(Model model){

        List<Publication> publication = pService.list();
        model.addAttribute("publication",publication);

        return "inicio.html";
    }

    /*@GetMapping("/publist/{userId}")
    public String publicationsListbyUser(Model model, ModelMap modelo, HttpSession session){

        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        modelo.put("user", user);
        Long userId = user.getId();
        List<Publication> publication = pService.list();
        model.addAttribute("publication",publication);

        return "public-list-provider";
    }*/
    @GetMapping("/publicationsbyUser/{userId}")
    public String listPublic (ModelMap model, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        model.put("user", user);
        Long userId = user.getId();
        List<Publication> publication = pService.list();
        model.addAttribute("publication", publication);
        return "public-list-provider";
    }

    //----------------------READ-----------------------DETAIL
    @GetMapping("/publication/{pubId}")
    public String newsDetail(@PathVariable("pubId") Long pubId, Model model){
        Publication pub = pService.getPublicationById(pubId);
        UserEntity user = pub.getProvider();
        model.addAttribute("pub", pub);
        model.addAttribute("user", user);
        return "Vista_formulario_Servicios.html";
    }


    //--------------------------UPDATE-----------------
    @GetMapping("/editPublication/{pId}")
    public String editPub(@PathVariable("pId") Long id, ModelMap model){

        model.put("publication", pService.getPublicationById(id));

        return"Formulario_Servicios_Edit.html";
    }

    @PostMapping("/editPublication/{pId}")
    public String editPub(@PathVariable("pId") Long pId, @RequestParam MultipartFile archivo,
                          @RequestParam String title, @RequestParam String description,
                          @RequestParam String description2
                           ) throws Exception {
        pService.updatePublication(archivo,title,description,description2, pId);
        return "redirect:/";


    }

    //-------------------DELETE------------------------
    @GetMapping("/publication/{pId}/delete")
    public String deletePub(@PathVariable("pId") Long pId){
        pService.delete(pId);
        return "Deleted"; //string para ser visualizada en postman
    }


    //-------------------SEARCH QUERY------------------------
    @GetMapping("/publist/search")
    public String searchUsername(@RequestParam(value="query") String query, Model model){
        List<UserEntity> users = userService.searchUsers(query);
        model.addAttribute("users", users);
        return "publist" ;
    }

    @GetMapping("/busqueda")
    public String barraBusqueda(@RequestParam Rubro rubro, ModelMap model){
        List<Publication> servicios = pService.findByRubro(rubro);
        model.put("servicios",servicios);
        return "index.html";
    }

}
