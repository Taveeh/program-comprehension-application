package web.converter;

import core.domain.Cat;
import org.springframework.stereotype.Component;
import web.dto.CatDTO;
import web.dto.CatWithToyDTO;

@Component
public class CatWithToyConverter extends BaseConverter<Long, Cat, CatWithToyDTO> {
    @Override
    public Cat convertDtoToModel(CatWithToyDTO dto) {
        var model = new Cat();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setBreed(dto.getBreed());
        model.setCatYears(dto.getCatYears());
        model.setFavoriteToy(dto.getFavoriteToy());
        return model;
    }

    @Override
    public CatWithToyDTO convertModelToDto(Cat cat) {
        var catDto = new CatWithToyDTO(cat.getName(), cat.getBreed(), cat.getCatYears(), cat.getFavoriteToy());
        catDto.setId(cat.getId());
        return catDto;
    }
}
