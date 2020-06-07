package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.FormValidator;
import com.cgi.dentistapp.dao.entity.DentistEntity;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.DentistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;

    @Autowired
    private DentistService dentistService;

    @Autowired
    FormValidator formValidator;

    private List<DentistEntity> allDentists;

    private Logger logger = Logger.getLogger(DentistAppController.class.getName());

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(formValidator);
    }

    @PostConstruct
    private void createDummyDentists() {
        dentistService.addDentist("Dr. Watson");
        dentistService.addDentist("Dr. Who");
        dentistService.addDentist("Dr. Zoidberg");
        dentistService.addDentist("Dr. House");
        dentistService.addDentist("Dr. Dolittle");
        dentistService.addDentist("Dr. Aivaluson");
        allDentists = getDentistsList();
    }

    @PostConstruct
    private void addDummyVisits() {
        dentistVisitService.addOrUpdateVisit(new DentistVisitEntity(allDentists.get(0).getName(),
                LocalDateTime.of(2020, 6, 13, 15, 00)));
        dentistVisitService.addOrUpdateVisit(new DentistVisitEntity(allDentists.get(3).getName(),
                LocalDateTime.of(2020, 6, 10, 12, 00)));
        dentistVisitService.addOrUpdateVisit(new DentistVisitEntity(allDentists.get(3).getName(),
                LocalDateTime.of(2020, 6, 23, 13, 30)));
    }

    @ModelAttribute("dentistsList")
    public List<DentistEntity> getDentistsList() {
        return dentistService.getAllDentists();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/form").setViewName("form");
        registry.addViewController("/formresults").setViewName("formresults");
        registry.addViewController("/all").setViewName("all");
        registry.addViewController("/update").setViewName("update");
    }

    // show index page
    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    // submit new visit
    @PostMapping("/")
    public String postRegisterForm(@Validated DentistVisitDTO dentistVisitDTO,
                                   BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("allDentists", allDentists);
            return "form";
        }

        int dentistIndex =  Integer.parseInt(dentistVisitDTO.getDentistName()) - 1;
        DentistVisitEntity dentistVisitEntity = new DentistVisitEntity(getNameFromIndex(dentistIndex),
                dentistVisitDTO.getVisitTime());
        dentistVisitEntity.setDentistName(allDentists.get(dentistIndex).getName());
        dentistVisitService.addOrUpdateVisit(dentistVisitEntity);

        return "formresults";
    }

    private String getNameFromIndex(int index) {
        return allDentists.get(index).getName();
    }

    // update existing visit
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String postUpdateForm(@Validated DentistVisitDTO dentistVisitDTO,
                                   BindingResult bindingResult, Model model) {

        model.addAttribute("visit", dentistVisitDTO);

        if (bindingResult.hasErrors()) {
            model.addAttribute("allDentists", allDentists);
            return "update";
        }

        int dentistIndex =  Integer.parseInt(dentistVisitDTO.getDentistName()) - 1;
        dentistVisitDTO.setDentistName(getNameFromIndex(dentistIndex));

        String updatedName = dentistVisitDTO.getDentistName();
        LocalDateTime updatedTime = dentistVisitDTO.getVisitTime();

        logger.log(Level.INFO, "New values:  " + updatedName + ", "
                + updatedTime + " for model " + dentistVisitDTO.getId());

        DentistVisitEntity dentistVisitEntity = dentistVisitService.findById(dentistVisitDTO.getId());
        dentistVisitEntity.setDentistName(updatedName);
        dentistVisitEntity.setVisitTime(updatedTime);
        dentistVisitService.addOrUpdateVisit(dentistVisitEntity);

        return "formresults";
    }

    // show form
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO, Model model) {
        model.addAttribute("allDentists", allDentists);
        return "form";
    }

    // show all items
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<DentistVisitEntity> visitsList(Model model) {
        List<DentistVisitEntity> allVisits = dentistVisitService.findAll();
        model.addAttribute("allVisits", allVisits);
        return allVisits;
    }

    // show search results
    @RequestMapping(value = "/search")
    public String Search(@RequestParam("searchString") String searchString, Model model) {
        if (!searchString.isEmpty()) {
            logger.log(Level.INFO, "Search params: " + searchString);
            List<DentistVisitEntity> searchResults = dentistVisitService.findBySearchString(searchString);
            model.addAttribute("searchResults", searchResults);
            return "details";
        } else {
            return "redirect:/all";
        }
    }

    // show item details
    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String showItem(@PathVariable("id") long id, Model model) {
        DentistVisitEntity dentistVisitEntity = dentistVisitService.findById(id);
        List<DentistVisitEntity> details = new ArrayList<>();
        details.add(dentistVisitEntity);
        model.addAttribute("searchResults", details);
        return "details";
    }

    // delete item
    @RequestMapping(value = "/details/{id}/delete", method = RequestMethod.POST)
    public String deleteItem(@PathVariable("id") long id) {
        dentistVisitService.deleteVisit(id);
        logger.log(Level.INFO, "Deleting item " + id);
        return "redirect:/all";
    }

    // show update form
    @RequestMapping(value = "/details/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(DentistVisitDTO dentistVisitDTO, Model model) {
        model.addAttribute("allDentists", allDentists);
        model.addAttribute("visit", dentistVisitDTO);
        return "update";
    }

}

