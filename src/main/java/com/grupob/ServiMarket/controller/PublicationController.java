package com.grupob.ServiMarket.controller;
import com.grupob.ServiMarket.entity.*;
import com.grupob.ServiMarket.enums.Rubro;
import com.grupob.ServiMarket.exceptions.MyException;
import com.grupob.ServiMarket.service.PublicationService;
import com.grupob.ServiMarket.service.ScoreService;
import com.grupob.ServiMarket.service.SolicitudService;
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
import java.util.List;

@Controller
@RequestMapping("/")
public class PublicationController  {

    @Autowired
    private PublicationService pService;
    @Autowired
    private UserService userService;
    @Autowired
    private SolicitudService solService;
    @Autowired
    private ScoreService scoreService;


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
                                  List<MultipartFile> archivos) throws MyException {

        UserEntity user = (UserEntity) session.getAttribute("usuariosession");
        modelMap.put("user", user);
        Long userId = user.getId();


        if (result.hasErrors()) {
            modelMap.put("publicacion", publication);
            modelMap.put("user", user);

            return "Formulario_Servicios.html";
        }
        pService.create(publication, userId, archivos);
        return "redirect:/publist";

    }

    //---------------------------READ-----------------------LIST
    //GET MAPPING
    @GetMapping("/publist")
    public String publicationsList(Model model){

        List<Publication> publication = pService.list();
        model.addAttribute("publication",publication);

        return "inicio.html";
    }

    @GetMapping("/publicationsbyUser")
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
    public String newsDetail(@PathVariable("pubId") Long pubId, Model model, ModelMap modelo){
        Publication pub = pService.getPublicationById(pubId);
        UserEntity user = pub.getProvider();
        model.addAttribute("pub", pub);
        model.addAttribute("user", user);
        List<Score> score = scoreService.list();
        model.addAttribute("score", score);
        Double promedioPuntaje= scoreService.calcularPromedioPuntaje(user);
        modelo.put("promedioPuntaje",promedioPuntaje);
        //List<Object[]> scores = scoreService.getProviderIdAndScoreByPublication(pub);
        //modelo.addAttribute("scores", scores);
        return "Vista_formulario_Servicios.html";
    }


    //--------------------------UPDATE-----------------
    @GetMapping("/editPublication/{pId}")
    public String editPub(@PathVariable("pId") Long id, ModelMap model){

        model.put("publication", pService.getPublicationById(id));

        return"Formulario_Servicios_Edit.html";
    }

    @PostMapping("/editPublication/{pId}")
    public String editPub(@PathVariable("pId") Long pId, @RequestParam List<MultipartFile> archivos,
                          @RequestParam String title, @RequestParam String description,
                          @RequestParam String description2, @RequestParam Rubro rubro
                           ) throws Exception {
        pService.updatePublication(archivos,title,description,description2, pId, rubro);
        return "redirect:/";


    }

    //-------------------DELETE------------------------
    @GetMapping("/publicationdel/{pubId}")
    public String deletePub(@PathVariable("pubId") Long pubId){
        pService.delete(pubId);
        return "redirect:/publicationsbyUser";
    }


    //-------------------SEARCH QUERY------------------------

    @GetMapping("/publist/rubro")
    public String barraBusqueda(@RequestParam (value = "rubro") Rubro rubro, ModelMap model){
        List<Publication> publication = pService.findByRubro(rubro);
        model.put("publication",publication);
        return "inicio.html";
    }
    @GetMapping("/publist/search")
    public String publicationsSearch(@RequestParam(value="query") String query,Model model){


        List<Publication> publication = pService.searchPublication(query);
        model.addAttribute("publication",publication);

        return "inicio.html";
    }

}
