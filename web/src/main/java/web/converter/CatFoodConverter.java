package web.converter;

import core.domain.Cat;
import core.domain.CatFood;
import core.domain.CatFoodPrimaryKey;
import core.domain.Food;
import core.domain.transmission.SimpleCat;
import core.domain.transmission.SimpleFood;
import org.springframework.stereotype.Component;
import web.dto.CatFoodDTO;

@Component
public class CatFoodConverter extends BaseConverter<CatFoodPrimaryKey, CatFood, CatFoodDTO> {
    @Override
    public CatFood convertDtoToModel(CatFoodDTO dto) {
        var model = new CatFood();
        model.setId(dto.getId());
        model.setCat(new Cat(dto.getCat().getId(), dto.getCat().getName(), dto.getCat().getBreed(), dto.getCat().getCatYears()));
        model.setFood(new Food(dto.getFood().getId(), dto.getFood().getName(), dto.getFood().getProducer(), dto.getFood().getExpirationDate()));
        return model;
    }

    @Override
    public CatFoodDTO convertModelToDto(CatFood catFood) {
        var catFoodDto = new CatFoodDTO();
        catFoodDto.setId(catFood.getId());
        catFoodDto.setCat(new SimpleCat(catFood.getCat().getId(), catFood.getCat().getName(), catFood.getCat().getBreed(), catFood.getCat().getCatYears()));
        catFoodDto.setFood(new SimpleFood(catFood.getFood().getId(), catFood.getFood().getName(), catFood.getFood().getProducer(), catFood.getFood().getExpirationDate()));
        return catFoodDto;
    }
}
