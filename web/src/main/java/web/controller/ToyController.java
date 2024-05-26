package web.controller;

import core.service.ToyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.converter.ToyConverter;
import web.dto.ToyDTO;
import web.dto.ToysDTO;

@RestController
public class ToyController {
    public static final Logger logger = LoggerFactory.getLogger(ToyController.class);

    @Autowired
    private ToyService toyService;

    @Autowired
    private ToyConverter toyConverter;

    @RequestMapping(value = "/toys")
    ToysDTO getToysFromRepository() {
        return new ToysDTO(
                toyConverter.convertModelsToDTOs(
                        toyService.getToysFromRepository()
                )
        );
    }

    @RequestMapping(value = "/toys", method = RequestMethod.POST)
    void addToy(@RequestBody ToyDTO toyDto) {
        var toy = toyConverter.convertDtoToModel(toyDto);
        toyService.addToy(
                toy.getName(),
                toy.getSize()
        );
    }

    @RequestMapping(value = "/toys/{catId}&{toyId}", method = RequestMethod.PUT)
    void addToyToCat(@PathVariable Long catId, @PathVariable Long toyId) {
        toyService.addToyToCat(
                catId, toyId
        );
    }
}
