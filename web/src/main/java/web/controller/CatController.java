package web.controller;


import core.domain.Cat;
import core.service.CatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.CatConverter;
import web.converter.CatWithToyConverter;
import web.dto.CatDTO;
import web.dto.CatWithToyDTO;
import web.dto.CatsDTO;
import web.dto.CatsWithToyDTO;

import java.util.List;

@RestController
public class CatController {
    public static final Logger logger = LoggerFactory.getLogger(CatController.class);

    @Autowired
    private CatService catService;

    @Autowired
    private CatConverter catConverter;

    @Autowired
    private CatWithToyConverter catWithToyConverter;

    @RequestMapping(value = "/cats")
    CatsDTO getCatsFromRepository() {
        logger.trace("getAllCats - method entered");
        List<Cat> cats = catService.getCatsFromRepository();
        CatsDTO catsDTO = new CatsDTO(catConverter.convertModelsToDTOs(cats));
        logger.trace("getAllCats: " + cats);
        return catsDTO;
    }

    @RequestMapping(value = "/cats", method = RequestMethod.POST)
    void addCat(@RequestBody CatDTO catDTO) {
        logger.trace("addCat - method entered - catDTO: " + catDTO);
        var cat = catConverter.convertDtoToModel(catDTO);
        catService.addCat(
                cat.getName(),
                cat.getBreed(),
                cat.getCatYears()
        );
        logger.trace("addCat - cat added");
    }

    @RequestMapping(value = "/cats/{id}", method = RequestMethod.PUT)
    void updateCat(@PathVariable Long id, @RequestBody CatDTO catDTO) {
        logger.trace("updateCat - method entered - catDTO: " + catDTO);
        var cat = catConverter.convertDtoToModel(catDTO);
        catService.updateCat(
                id,
                cat.getName(),
                cat.getBreed(),
                cat.getCatYears()
        );
        logger.trace("updateCat - cat updated");
    }

    @RequestMapping(value = "/cats/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteCat(@PathVariable Long id) {
        logger.trace("deleteCat - method entered - catId: " + id);
        catService.deleteCat(id);
        logger.trace("deleteCat - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/cats/toys", method = RequestMethod.GET)
    CatsWithToyDTO getCatsWithToysFromRepository() {
        logger.trace("getAllCats - method entered");
        List<Cat> cats = catService.getCatsWithToys();
        CatsWithToyDTO catsDTO = new CatsWithToyDTO(catWithToyConverter.convertModelsToDTOs(cats));
        logger.trace("getAllCats: " + cats);
        return catsDTO;
    }





}
