package com.grupob.ServiMarket.controller;
import com.grupob.ServiMarket.entity.Publication;
import com.grupob.ServiMarket.entity.UserEntity;
import com.grupob.ServiMarket.service.PublicationService;
import com.grupob.ServiMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class PublicationController  {

    @Autowired
    private PublicationService pService;
    @Autowired
    private UserService userService;//por si lo necesitamos después


    //-------------------CREATE PUBLICATION---------------------------

    @GetMapping("/publicacion")
    public String newpublication(ModelMap model){
        Publication publication = new Publication();
        model.put("publicacion", publication);
        return "newpublic.html";
    }
    @PostMapping("/newPublication")
    public String savePublication(@Valid Publication publication, BindingResult result){

        if(result.hasErrors()){
            return "newpublic.html";
        }
        pService.create(publication);

        return "public-details.html";
    }

    //---------------------------READ-----------------------LIST
    //GET MAPPING
    @GetMapping("/publist")  //vista necesaria para el ADMIN
    public String publicationsList(Model model){

        List<Publication> publication = pService.list();
        model.addAttribute("publication",publication);

        return "index.html";
    }

    //----------------------READ-----------------------DETAIL
    @GetMapping("/publication/{pubId}")
    public String newsDetail(@PathVariable("pubId") Long pubId, Model model){
        Publication pub = pService.getPublicationById(pubId);
        model.addAttribute("pub", pub);
        return "public-details.html";
    }


    //--------------------------UPDATE-----------------
    @GetMapping("/editPublication/{pId}")
    public String editPub(@PathVariable("pId") Long id, ModelMap model){

        model.put("publication", pService.getPublicationById(id));

        return"editPublication.html";
    }

    @PutMapping("/editPublication/{pId}")
    public String editPub(@RequestBody Publication publication,
                           @PathVariable("pId") Long pId){

        publication.setId(pId);
        pService.updatePublication(publication);
        return "Updated"; //string para ser visualizada en postman


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
    public String barraBusqueda(@RequestParam String rubro, ModelMap model){
        List<Publication> servicios = pService.findByRubro(rubro);
        model.put("servicios",servicios);
        return "index.html";
    }

}
